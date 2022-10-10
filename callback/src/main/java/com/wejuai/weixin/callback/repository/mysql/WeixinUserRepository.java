package com.wejuai.weixin.callback.repository.mysql;

import com.wejuai.entity.mysql.WeixinUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeixinUserRepository extends JpaRepository<WeixinUser, String> {

    boolean existsByOffiOpenId(String offiOpenId);

    WeixinUser findByUnionId(String unionId);
}
