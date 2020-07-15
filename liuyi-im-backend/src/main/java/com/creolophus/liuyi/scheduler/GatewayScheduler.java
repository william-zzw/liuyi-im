package com.creolophus.liuyi.scheduler;

import com.creolophus.liuyi.common.logger.Entry;
import com.creolophus.liuyi.service.GatewayService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2020/7/3 下午2:36
 */
@Service
public class GatewayScheduler {

    @Resource
    private GatewayService gatewayService;

    @Entry
    @Scheduled(fixedRate = 60 * 1000)
    public void gatewayCheck(){
        gatewayService.findGetewayListAndCheck();
    }
}