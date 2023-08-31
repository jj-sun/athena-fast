package com.athena.modules.sys.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

/**
 * 系统验证码
 *
 * @author Mr.sun
 */
@Data
@Entity
@Table(name = "sys_captcha")
public class SysCaptchaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorConfig")
    @GenericGenerator(name="idGeneratorConfig", strategy = "com.athena.config.IdGeneratorConfig")
    @Column(length = 32)
    private String uuid;
    /**
     * 验证码
     */
    @Column(length = 6)
    private String code;
    /**
     * 过期时间
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date expireTime;

}
