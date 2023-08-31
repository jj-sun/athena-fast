package com.athena.common.base.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Mr.sun
 * @date 2021/12/14 12:45
 * @description
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BasePo implements Serializable {

    @Serial
    private static final long serialVersionUID = -8172686200993082466L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorConfig")
    @GenericGenerator(name="idGeneratorConfig", strategy = "com.athena.config.IdGeneratorConfig")
    @Column(length = 32)
    private String id;

    /**
     * 创建人
     */
    @CreatedBy
    @Column(length = 32)
    private String creator;

    /**
     * 创建时间
     */
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ctime;

    /**
     * 修改人
     */
    @LastModifiedBy
    @Column(length = 32)
    private String editor;

    /**
     * 修改时间
     */
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date mtime;

    /**
     * 状态 0正常1删除或禁用
     */
    @Column(columnDefinition = "tinyint default 0")
    private Integer delFlag;
}
