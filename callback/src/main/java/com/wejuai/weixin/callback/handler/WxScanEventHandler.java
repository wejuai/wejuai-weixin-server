package com.wejuai.weixin.callback.handler;

import com.endofmaster.weixin.message.received.WxMsgHandler;
import com.endofmaster.weixin.message.received.WxReply;
import com.endofmaster.weixin.message.received.WxTextMsgReply;
import com.endofmaster.weixin.message.received.msg.WxScanEvent;
import com.wejuai.weixin.callback.service.WxUserService;
import org.springframework.stereotype.Component;

/**
 * @author ZM.Wang
 * 已关注的扫码事件
 */
@Component
public class WxScanEventHandler implements WxMsgHandler<WxScanEvent> {

    private final WxUserService wxUserService;

    public WxScanEventHandler(WxUserService wxUserService) {
        this.wxUserService = wxUserService;
    }

    @Override
    public WxReply process(WxScanEvent event) {
        wxUserService.saveWeixinUser(event.fromUserName);
        return new WxTextMsgReply(event, "扫码成功");
    }

    @Override
    public Class<WxScanEvent> getMsgClass() {
        return WxScanEvent.class;
    }
}
