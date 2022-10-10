package com.wejuai.weixin.callback.service;


import com.endofmaster.rest.exception.NotFoundException;
import com.endofmaster.weixin.basic.WxAuthUserInfo;
import com.endofmaster.weixin.basic.WxBasicApi;
import com.wejuai.entity.mongo.WeiXinToken;
import com.wejuai.entity.mysql.Sex;
import com.wejuai.entity.mysql.WeixinUser;
import com.wejuai.weixin.callback.repository.mongo.WeiXinTokenRepository;
import com.wejuai.weixin.callback.repository.mysql.WeixinUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.wejuai.weixin.core.config.WxConstant.APP_ID;

/**
 * @author ZM.Wang
 */
@Service
public class WxUserService {

    private static final Logger logger = LoggerFactory.getLogger(WxUserService.class);

    private final WeiXinTokenRepository weiXinTokenRepository;
    private final WeixinUserRepository weixinUserRepository;

    private final WxBasicApi wxOffiBasicApi;

    public WxUserService(WeiXinTokenRepository weiXinTokenRepository, WeixinUserRepository weixinUserRepository, WxBasicApi wxOffiBasicApi) {
        this.weiXinTokenRepository = weiXinTokenRepository;
        this.weixinUserRepository = weixinUserRepository;
        this.wxOffiBasicApi = wxOffiBasicApi;
    }

    public void saveWeixinUser(String openId) {
        boolean hasWxUser = weixinUserRepository.existsByOffiOpenId(openId);
        if (!hasWxUser) {
            Optional<WeiXinToken> tokenOptional = weiXinTokenRepository.findById(APP_ID);
            if (tokenOptional.isEmpty()) {
                throw new NotFoundException("未找到微信accessToken");
            }
            String accessToken = tokenOptional.get().getToken();
            WxAuthUserInfo wxAuthUserInfo = wxOffiBasicApi.getUserInfo(openId, accessToken);
            WeixinUser weixinUser = weixinUserRepository.findByUnionId(wxAuthUserInfo.getUnionId());
            if (weixinUser == null) {
                weixinUser = new WeixinUser();
            }
            weixinUser.update(wxAuthUserInfo.getProvince(), wxAuthUserInfo.getCity(),
                    wxAuthUserInfo.getCountry(), Sex.find(wxAuthUserInfo.getSex()), wxAuthUserInfo.getNickName(),
                    wxAuthUserInfo.getHeadImgUrl(), wxAuthUserInfo.getUnionId()).setOffiOpenId(wxAuthUserInfo.getOpenId());
            weixinUserRepository.save(weixinUser);
            logger.debug("更新用户微信公众号对应openId成功");
        }
    }
}
