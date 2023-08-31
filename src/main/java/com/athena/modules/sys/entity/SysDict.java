package com.athena.modules.sys.entity;

import com.athena.common.base.po.BasePo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Formula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author Mr.sun
 * @date 2022/1/19 19:53
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@Entity
@Table(name = "sys_dict")
public class SysDict extends BasePo implements Serializable {
    @Serial
    private static final long serialVersionUID = -1541013999225745949L;

    /**
     * 字典名称
     */
    @Column(length = 100)
    private String dictName;

    /**
     * 字典code
     */
    @Column(length = 100)
    private String dictCode;

    /**
     * 字典类型0为string,1为number
     */
    @Column(columnDefinition = "tinyint")
    private Integer type;
    /**
     * 描述
     */
    private String description;

}
