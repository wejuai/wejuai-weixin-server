package com.wejuai.weixin.callback.handler;

import com.endofmaster.weixin.message.received.WxMsgHandler;
import com.endofmaster.weixin.message.received.WxReply;
import com.endofmaster.weixin.message.received.WxTextMsgReply;
import com.endofmaster.weixin.message.received.msg.WxScanSubscribeEvent;
import com.wejuai.weixin.callback.service.WxUserService;
import org.springframework.stereotype.Component;

/**
 * @author ZM.Wang
 * 扫码关注事件
 */
@Component
public class WxScanSubscribeEventHandler implements WxMsgHandler<WxScanSubscribeEvent> {

    private final WxUserService wxUserService;

    public WxScanSubscribeEventHandler(WxUserService wxUserService) {
        this.wxUserService = wxUserService;
    }

    @Override
    public WxReply process(WxScanSubscribeEvent event) {
        wxUserService.saveWeixinUser(event.fromUserName);
        return new WxTextMsgReply(event, "欢迎关注为聚爱公众号");
    }

    @Override
    public Class<WxScanSubscribeEvent> getMsgClass() {
        return WxScanSubscribeEvent.class;
    }
}
