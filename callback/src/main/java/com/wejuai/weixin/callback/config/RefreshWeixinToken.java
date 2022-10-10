package com.wejuai.weixin.callback.config;

import com.endofmaster.weixin.basic.WxAccessToken;
import com.endofmaster.weixin.basic.WxBasicApi;
import com.wejuai.entity.mongo.WeiXinToken;
import com.wejuai.weixin.callback.repository.mongo.WeiXinTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author ZM.Wang
 */
public class RefreshWeixinToken {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WeiXinTokenRepository weiXinTokenRepository;
    private final WxBasicApi wxBasicApi;
    private final ScheduledExecutorService task;

    public RefreshWeixinToken(WeiXinTokenRepository weiXinTokenRepository, WxBasicApi wxBasicApi) {
        this.weiXinTokenRepository = weiXinTokenRepository;
        this.wxBasicApi = wxBasicApi;
        this.task = Executors.newSingleThreadScheduledExecutor();
        start();
    }

    private void start() {
        this.task.schedule(this::refresh, 2, TimeUnit.MINUTES);
    }

    private void refresh() {
        logger.info("刷新公众号Token开始");
        String appId = wxBasicApi.getAppId();
        WxAccessToken accessToken = wxBasicApi.getAccessToken();
        Optional<WeiXinToken> weiXinTokenOptional = weiXinTokenRepository.findById(appId);
        WeiXinToken weiXinToken = weiXinTokenOptional.orElse(new WeiXinToken(appId));
        if (weiXinToken.getExpiredAt() == 0 || weiXinToken.getExpiredAt() >= System.currentTimeMillis()) {
            logger.debug("获取到的过期时间：" + accessToken.getExpiresIn());
            weiXinToken.update(accessToken.getAccessToken(), (System.currentTimeMillis() / 1000 + accessToken.getExpiresIn() - (60 * 30)) * 1000);
            weiXinTokenRepository.save(weiXinToken);
            logger.info("刷新公众号Token结束");
        } else {
            logger.info("公众号Token尚未过期");
        }
    }

}
