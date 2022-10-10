package com.wejuai.weixin.callback.web;


import com.endofmaster.weixin.log.WxMsgLogRepository;
import com.endofmaster.weixin.message.received.WxMsgHandler;
import com.endofmaster.weixin.message.received.WxMsgService;
import com.endofmaster.weixin.message.received.WxReply;
import com.endofmaster.weixin.support.WxUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YQ.Huang
 */
@Api
@RestController
@RequestMapping("/callback/weixin")
public class WeixinCallbackController {

    private static final Logger logger = LoggerFactory.getLogger(WeixinCallbackController.class);

    private final WxMsgService wxMsgService;

    public WeixinCallbackController(WxMsgHandler<?>[] msgHandlers, WxMsgLogRepository wxMsgLogRepository) {
        wxMsgService = new WxMsgService();
        wxMsgService.registerHandlers(msgHandlers);
        wxMsgService.registerMsgLog(wxMsgLogRepository);
    }

    //目前只有公众号
    @PostMapping
    public String receiveMsg(@RequestBody String xml) {
        String replyContent = "";
        if (!xml.contains("TEMPLATESENDJOBFINISH")) {
            logger.debug("Received a msg from Weixin:\n{}", xml);
            WxReply reply = wxMsgService.process(xml);
            if (reply != null)
                replyContent = reply.getContent();
            logger.debug("Reply:\n{}", replyContent);
        }
        return replyContent;
    }

    @GetMapping
    public String validateToken(String signature, String timestamp, String nonce, String echostr) {
        logger.debug("Received a token validation request from Weixin");
        WxUtils.validateToken("wejuai", signature, timestamp, nonce);
        logger.debug("Token validation passed!");
        return echostr;
    }

}
