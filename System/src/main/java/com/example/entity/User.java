package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 3072 kB
 * </p>
 *
 * @author maso
 * @since 2024-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="InnoDB free: 3072 kB")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String no;

    private String name;

    private String password;

    private Integer age;

    private Integer sex;

    private String phone;

    private Integer roleId;

    @TableField("isValid")
    private String isValid;


}
