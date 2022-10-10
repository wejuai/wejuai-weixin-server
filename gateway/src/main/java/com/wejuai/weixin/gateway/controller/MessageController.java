package com.wejuai.weixin.gateway.controller;

import com.endofmaster.rest.exception.ServerException;
import com.endofmaster.weixin.message.send.WxMessageApi;
import com.endofmaster.weixin.message.send.WxTemplateMsg;
import com.wejuai.entity.mongo.WeiXinToken;
import com.wejuai.weixin.gateway.config.WeixinConfig;
import com.wejuai.weixin.gateway.repository.WeiXinTokenRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZM.Wang
 * 发送消息
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    private final WxMessageApi wxMessageApi;
    private final WeiXinTokenRepository weiXinTokenRepository;
    private final WeixinConfig.Properties wxConfig;

    public MessageController(WxMessageApi wxMessageApi, WeiXinTokenRepository weiXinTokenRepository, WeixinConfig.Properties wxConfig) {
        this.wxMessageApi = wxMessageApi;
        this.weiXinTokenRepository = weiXinTokenRepository;
        this.wxConfig = wxConfig;
    }

    @PostMapping("/subscribe")
    public void sendWxSubscribeMessage(@RequestBody WxTemplateMsg message) {
        WeiXinToken weiXinToken = weiXinTokenRepository.findById(wxConfig.getAppId())
                .orElseThrow(() -> new ServerException("找不到小程序accessToken"));
        wxMessageApi.sendWxSubscribeMessage(message, weiXinToken.getToken());
    }
}
