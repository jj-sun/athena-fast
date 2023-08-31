package com.athena.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

@Slf4j
public class IdGeneratorConfig implements IdentifierGenerator {

    /**
     * 终端ID
     */
    public static long WORK_ID = 1;
    /**
     * 数据中心ID
     */
    public static long DATACENTER_ID = 1;

    private final Snowflake snowflake = IdUtil.getSnowflake(WORK_ID, DATACENTER_ID);

    @PostConstruct
    public void init() {
        //WORK_ID = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        log.info("当前机器的workId:{}", WORK_ID);
    }

    public synchronized String snowflakeId() {
        return snowflake.nextIdStr();
    }

    public synchronized String snowflakeId(long workId, long datacenterId) {
        Snowflake snowflake = IdUtil.getSnowflake(workId, datacenterId);
        return snowflake.nextIdStr();
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return snowflakeId(WORK_ID, DATACENTER_ID);
    }
}
