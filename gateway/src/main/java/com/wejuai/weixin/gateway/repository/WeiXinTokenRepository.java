package com.wejuai.weixin.gateway.repository;

import com.wejuai.entity.mongo.WeiXinToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeiXinTokenRepository extends MongoRepository<WeiXinToken, String> {

}
