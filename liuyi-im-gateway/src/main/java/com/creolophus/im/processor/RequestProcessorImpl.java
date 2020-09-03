package com.creolophus.im.processor;

import com.creolophus.im.netty.core.RequestProcessor;
import com.creolophus.im.protocol.coder.MessageCoder;
import com.creolophus.im.protocol.domain.Auth;
import com.creolophus.im.protocol.domain.Command;
import com.creolophus.im.protocol.domain.CommandType;
import com.creolophus.im.protocol.type.LoginMsg;
import com.creolophus.im.protocol.type.PushMessageAck;
import com.creolophus.im.protocol.type.SendMessageMsg;
import com.creolophus.im.service.AuthService;
import com.creolophus.im.service.MessageService;
import com.creolophus.im.service.UserClientService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/9/12 上午11:27
 */
@Component
public class RequestProcessorImpl implements RequestProcessor {

    @Resource
    private MessageCoder messageCoder;

    @Resource
    private MessageService messageService;

    @Resource
    private UserClientService userClientService;

    @Resource
    private AuthService authService;

    @Override
    public Object processRequest(Command request) {
        switch (CommandType.valueOf(request.getHeader().getType())) {
            case LOGIN:
                return userClientService.login(messageCoder.decode(request.getBody(), LoginMsg.class));
            case SEND_MESSAGE:
                return messageService.sendMessage(messageCoder.decode(request.getBody(), SendMessageMsg.class));
            case PUSH_MESSAGE:
                userClientService.pushMessageAck(messageCoder.decode(request.getBody(), PushMessageAck.class));
            default:
                break;
        }
        return null;
    }

    @Override
    public void verify(Command request) {
        Auth auth = authService.verify(request.getToken());
        request.setAuth(auth);
    }
}
