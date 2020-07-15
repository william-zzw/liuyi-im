package com.creolophus.liuyi.netty;

import com.creolophus.liuyi.domain.UserChannel;
import com.creolophus.liuyi.netty.core.ChannelEventListener;
import com.creolophus.liuyi.service.UserChannelHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2020/6/30 下午2:59
 */
@Service
public class DefaultChannelEventListener implements ChannelEventListener {

    @Resource
    private UserChannelHolder userChannelHolder;


    @Override
    public void onChannelActive(ChannelHandlerContext ctx) {

    }

    @Override
    public void onChannelInactive(ChannelHandlerContext ctx) {

    }

    @Override
    public void onChannelRegistered(ChannelHandlerContext ctx) {

    }

    @Override
    public void onChannelUnregistered(ChannelHandlerContext ctx) {
        unregisterUserClient(ctx.channel());
    }

    @Async
    public void unregisterUserClient(Channel channel){
        UserChannel uc = userChannelHolder.getUserClient(UserChannel.getChannelId(channel));
        if(uc!=null){
            userChannelHolder.unregisterUserClient(uc);
        }
    }

    @Override
    public void onExceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

    }

    @Override
    public void onFlush(ChannelHandlerContext ctx) {

    }

    @Override
    public void onUserEventTriggered(ChannelHandlerContext ctx, Object evt) {

    }
}