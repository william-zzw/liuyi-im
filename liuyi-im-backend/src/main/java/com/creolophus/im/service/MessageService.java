package com.creolophus.im.service;

import com.creolophus.im.common.base.BaseService;
import com.creolophus.im.common.entity.Message;
import com.creolophus.im.dao.MessageDao;
import com.creolophus.im.protocol.MessageType;
import com.creolophus.im.storage.MessageStorage;
import com.creolophus.im.thread.StopableThread;
import com.creolophus.liuyi.common.thread.Stopable;
import org.beetl.sql.core.query.LambdaQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author magicnana
 * @date 2019/10/21 下午5:12
 */
@Service
public class MessageService extends BaseService implements Stopable {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    private static List<StopableThread> threads = new ArrayList<>();

    @Resource
    private IdSimpleService idSimpleService;

    @Resource
    private GroupService groupService;

    @Resource
    private MessageStorage messageStorage;

    @Resource
    private MessageDao messageDao;

    @Resource
    private MessageHandlerService messageHandlerService;

    private Long asyncMessage(Message message) {
        return messageStorage.asyncMessage(message);
    }

    public List<Message> findMessageList(
            Long receiverId, Long messageId, Long groupId, Long senderId, Integer messageType, Integer messageKind, Integer pageNo, Integer pageSize) {
        LambdaQuery<Message> query = messageDao.createLambdaQuery();
        if(receiverId != null) {
            query.andEq(Message::getReceiverId, receiverId);
        }

        if(messageId != null) {
            query.andGreat(Message::getMessageId, messageId);
        }

        if(groupId != null) {
            query.andEq(Message::getGroupId, groupId);
        }

        if(senderId != null) {
            query.andEq(Message::getSenderId, senderId);
        }

        if(messageType != null) {
            query.andEq(Message::getMessageType, messageType);
        }

        if(messageKind != null) {
            query.andEq(Message::getMessageKind, messageKind);
        }

        return query.asc(Message::getMessageId).page(pageNo, pageSize).getList();
    }

    public List<Message> findUnreadMessageList(Long receiverId, Long messageId) {
        return messageDao.selectMessageList(receiverId, messageId);
    }


    @PostConstruct
    public void initThread() {
        Runnable asyncRunnable = () -> messageHandlerService.asyncMessage();
        int processors = Runtime.getRuntime().availableProcessors();

        {
            for (int i = 0; i < processors; i++) {
                StopableThread t = new StopableThread(asyncRunnable);
                threads.add(t);
                t.start();
            }
        }
        logger.info("所有线程已启动 {}", threads.size());
    }

    public Long sendMessage(
            Long senderId, Long targetId, Integer messageType, Integer messageKind, String messageBody, Date sendTime, String appKey) {
        if(messageType == MessageType.SINGLE.value()) {
            Message message = new Message();
            message.setMessageId(idSimpleService.nextMessageId(targetId));
            message.setCreateTime(new Date());
            message.setMessageBody(messageBody);
            message.setMessageType(messageType);
            message.setMessageKind(messageKind);
            message.setSenderId(senderId);
            message.setSendTime(sendTime);
            message.setReceiverId(targetId);
            message.setAppKey(appKey);
            asyncMessage(message);
            return message.getMessageId();
        } else {
            List<Long> list = groupService.findMembersUserIdByGroupId(targetId);
            Long messageId = 0L;
            for (Long member : list) {
                Message message = new Message();
                message.setMessageId(idSimpleService.nextMessageId(member));
                message.setCreateTime(new Date());
                message.setGroupId(targetId);
                message.setMessageBody(messageBody);
                message.setMessageType(messageType);
                message.setMessageKind(messageKind);
                message.setSenderId(senderId);
                message.setSendTime(sendTime);
                message.setReceiverId(member);
                message.setAppKey(appKey);
                asyncMessage(message);

                if(member.equals(senderId)) {
                    messageId = message.getMessageId();
                }
            }
            return messageId;
        }
    }

    @Override
    public void shutdown() {
        threads.forEach(t -> {
            t.shutdown();
        });
    }
}
