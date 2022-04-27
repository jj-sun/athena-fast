package com.athena.modules.sys.entity;

import com.athena.common.base.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author Mr.sun
 * @date 2022/1/19 19:53
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
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