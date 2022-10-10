package com.wejuai.weixin.callback.repository.mongo;


import com.endofmaster.weixin.log.WxMsgLog;
import com.endofmaster.weixin.log.WxMsgLogRepository;
import org.springframework.stereotype.Component;

/**
 * @author ZM.Wang
 */
@Component
public class WxMsgLogRepositoryImpl implements WxMsgLogRepository {

    private final WxMsgXmllLogRepository wxMsgXmllLogRepository;

    public WxMsgLogRepositoryImpl(WxMsgXmllLogRepository wxMsgXmllLogRepository) {
        this.wxMsgXmllLogRepository = wxMsgXmllLogRepository;
    }

    @Override
    public void save(WxMsgLog wxMsgLog) {
        wxMsgXmllLogRepository.save(wxMsgLog);
    }
}
