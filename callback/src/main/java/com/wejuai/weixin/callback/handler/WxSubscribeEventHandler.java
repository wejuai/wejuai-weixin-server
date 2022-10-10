package com.wejuai.weixin.callback.handler;


import com.endofmaster.weixin.message.received.WxMsgHandler;
import com.endofmaster.weixin.message.received.WxReply;
import com.endofmaster.weixin.message.received.WxTextMsgReply;
import com.endofmaster.weixin.message.received.msg.WxSubscribeEvent;
import com.wejuai.weixin.callback.service.WxUserService;
import org.springframework.stereotype.Component;

/**
 * @author ZM.Wang
 * 关注事件
 */
@Component
public class WxSubscribeEventHandler implements WxMsgHandler<WxSubscribeEvent> {

    private final WxUserService wxUserService;

    public WxSubscribeEventHandler(WxUserService wxUserService) {
        this.wxUserService = wxUserService;
    }

    @Override
    public WxReply process(WxSubscribeEvent event) {
        wxUserService.saveWeixinUser(event.fromUserName);
        return new WxTextMsgReply(event, "欢迎关注为聚爱公众号");
    }

    @Override
    public Class<WxSubscribeEvent> getMsgClass() {
        return WxSubscribeEvent.class;
    }
}
