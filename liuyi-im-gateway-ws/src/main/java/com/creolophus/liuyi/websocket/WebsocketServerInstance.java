package com.creolophus.liuyi.websocket;

import com.creolophus.liuyi.netty.core.AbstractWebSocketServer;
import com.creolophus.liuyi.netty.protocol.Command;
import com.creolophus.liuyi.netty.sleuth.SleuthNettyAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @author magicnana
 * @date 2020/7/8 下午8:17
 */
@ServerEndpoint("/liuyi/gateway/ws")
@Component
public class WebsocketServerInstance extends AbstractWebSocketServer {

    private Logger logger = LoggerFactory.getLogger(WebsocketServerInstance.class);

    private Session session;

    @Override
    public Session getSession() {
        return session;
    }

    @OnClose
    public void onClose(Session session) {
        SleuthNettyAdapter.getInstance().begin(tracerUtil, "onClose");
        logger.debug("{}", session.getId());
//        connect(session);         //客服不需要关闭闲置连接
        if(sessionEventListener != null) sessionEventListener.onClose(session);
        SleuthNettyAdapter.getInstance().cleanContext();
        contextProcessor.clearContext();
    }

    @OnError
    public void onError(Session session, Throwable error) {
        SleuthNettyAdapter.getInstance().begin(tracerUtil, "onError");
        logger.error("{}", session.getId(), error);
        if(sessionEventListener != null) sessionEventListener.onError(session, error);
        SleuthNettyAdapter.getInstance().cleanContext();
        contextProcessor.clearContext();

    }

    @OnMessage
    public void onMessage(Session session, String message) {
        SleuthNettyAdapter.getInstance().begin(tracerUtil, "onMessage");
        logger.debug("{} {}", session.getId(), message);
        Command request = decode(message);
        contextProcessor.initContext(session, request);
        verify(session, request);
        contextProcessor.validateUserId(request);
        if(sessionEventListener != null) sessionEventListener.onMessage(session, message);
        Command response = process(session, request);
        flush(session, message, response);
        if(sessionEventListener != null) sessionEventListener.onFlush(session, response);
        SleuthNettyAdapter.getInstance().cleanContext();
        contextProcessor.clearContext();
    }

    @OnOpen
    public void onOpen(Session session) {
        SleuthNettyAdapter.getInstance().begin(tracerUtil, "onOpen");
        logger.debug("{}", session.getId());
        this.session = session;
        if(sessionEventListener != null) sessionEventListener.onOpen(session);
        SleuthNettyAdapter.getInstance().cleanContext();
        contextProcessor.clearContext();
    }
}