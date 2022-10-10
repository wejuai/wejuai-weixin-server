package com.wejuai.weixin.callback.config;

import com.endofmaster.weixin.basic.WxBasicApi;
import com.endofmaster.weixin.qr.code.WxQrCodeApi;
import com.endofmaster.weixin.support.WxHttpClient;
import com.wejuai.weixin.callback.repository.mongo.WeiXinTokenRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @author ZM.Wang
 */
@Configuration
@EnableConfigurationProperties(WeixinConfig.Properties.class)
public class WeixinConfig {

    public final Properties weixin;

    public WeixinConfig(Properties weixin) {
        this.weixin = weixin;
    }

    @Bean
    WxHttpClient wxHttpClient() {
        return new WxHttpClient();
    }

    /** 代表开放平台 */
    @Bean
    WxBasicApi wxOpenBasicApi(WxHttpClient wxHttpClient) {
        return new WxBasicApi(weixin.getOpenId(), weixin.getOpenSecret(), wxHttpClient);
    }

    /** 代表公众平台 */
    @Bean
    WxBasicApi wxOffiBasicApi(WxHttpClient wxHttpClient) {
        return new WxBasicApi(weixin.getOffiId(), weixin.getOffiSecret(), wxHttpClient);
    }

    /** 代表小程序 */
    @Bean
    WxBasicApi wxAppBasicApi(WxHttpClient wxHttpClient) {
        return new WxBasicApi(weixin.getAppId(), weixin.getAppSecret(), wxHttpClient);
    }

    @Bean
    RefreshWeixinToken refreshWeixinOffiToken(WeiXinTokenRepository weiXinTokenRepository, WxBasicApi wxOffiBasicApi) {
        return new RefreshWeixinToken(weiXinTokenRepository, wxOffiBasicApi);
    }

    @Bean
    RefreshWeixinToken refreshWeixinAppToken(WeiXinTokenRepository weiXinTokenRepository, WxBasicApi wxAppBasicApi) {
        return new RefreshWeixinToken(weiXinTokenRepository, wxAppBasicApi);
    }

    @Bean
    WxQrCodeApi wxQrCodeApi() {
        return new WxQrCodeApi();
    }

    @Validated
    @ConfigurationProperties(prefix = "weixin")
    static class Properties {

        @NotBlank
        private String offiOriginId;

        @NotBlank
        private String appOriginId;

        @NotBlank
        private String openId;

        @NotBlank
        private String openSecret;

        @NotBlank
        private String appId;

        @NotBlank
        private String appSecret;

        @NotBlank
        private String offiId;

        @NotBlank
        private String offiSecret;

        @NotBlank
        private String appAuditMsg;

        @NotBlank
        private String appReplyMsg;

        public String getOffiOriginId() {
            return offiOriginId;
        }

        public Properties setOffiOriginId(String offiOriginId) {
            this.offiOriginId = offiOriginId;
            return this;
        }

        public String getAppOriginId() {
            return appOriginId;
        }

        public Properties setAppOriginId(String appOriginId) {
            this.appOriginId = appOriginId;
            return this;
        }

        public String getOpenId() {
            return openId;
        }

        public Properties setOpenId(String openId) {
            this.openId = openId;
            return this;
        }

        public String getOpenSecret() {
            return openSecret;
        }

        public Properties setOpenSecret(String openSecret) {
            this.openSecret = openSecret;
            return this;
        }

        public String getAppId() {
            return appId;
        }

        public Properties setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public Properties setAppSecret(String appSecret) {
            this.appSecret = appSecret;
            return this;
        }

        public String getOffiId() {
            return offiId;
        }

        public Properties setOffiId(String offiId) {
            this.offiId = offiId;
            return this;
        }

        public String getOffiSecret() {
            return offiSecret;
        }

        public Properties setOffiSecret(String offiSecret) {
            this.offiSecret = offiSecret;
            return this;
        }

        public String getAppAuditMsg() {
            return appAuditMsg;
        }

        public Properties setAppAuditMsg(String appAuditMsg) {
            this.appAuditMsg = appAuditMsg;
            return this;
        }

        public String getAppReplyMsg() {
            return appReplyMsg;
        }

        public Properties setAppReplyMsg(String appReplyMsg) {
            this.appReplyMsg = appReplyMsg;
            return this;
        }
    }
}
