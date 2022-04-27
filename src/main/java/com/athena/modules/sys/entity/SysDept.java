package com.athena.modules.sys.entity;

import com.athena.common.base.po.BasePo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author Mr.sun
 * @date 2022/1/24 8:58
 * @description
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_dept")
public class SysDept extends BasePo implements Serializable {
    @Serial
    private static final long serialVersionUID = -1456049284379334422L;

    /**
     * 上级部门
     */
    @Column(length = 32)
    private String parentId;

    /**
     * 部门名称
     */
    @Column(length = 100)
    private String deptName;

    /**
     * 部门code
     */
    @Column(length = 64)
    private String deptCode;

    /**
     * 描述
     */
    @Column(length = 500)
    private String description;

    /**
     * 排序
     */
    private Integer sortOrder;

    @Transient
    private List<SysDept> children;
}
