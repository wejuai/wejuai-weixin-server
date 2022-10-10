package com.wejuai.weixin.callback.repository.mongo;


import com.endofmaster.weixin.log.WxMsgLog;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ZM.Wang
 */
public interface WxMsgXmllLogRepository extends MongoRepository<WxMsgLog, String> {
}
