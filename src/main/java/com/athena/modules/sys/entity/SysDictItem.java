package com.athena.modules.sys.entity;

import com.athena.common.base.po.BasePo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Table(name = "sys_dict_item")
public class SysDictItem extends BasePo implements Serializable {
    @Serial
    private static final long serialVersionUID = 913097975486211620L;

    @Column(length = 32)
    private String dictId;

    @Column(length = 100)
    private String itemText;

    @Column(length = 100)
    private String itemValue;

    private String description;

}
