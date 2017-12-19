//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gi.ctdn.cloud.storage.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class User {
    private Long id;
    private static final long serialVersionUID = 1L;
    private int userTzjlSum;
    @NotBlank(
            message = "真实姓名不能为空"
    )
    private String realName;
    @NotBlank(
            message = "登陆名称不能为空"
    )
    private String nickName;
    @NotBlank(
            message = "邮箱不能为空"
    )
    private String email;
    private String mobile;
    private String telephone;
    private String status;
    private String type;
    @NotBlank(
            message = "工号不能为空"
    )
    private String employNo;
    @NotNull(
            message = "部门id不能为空"
    )
    private Long departmentId;
    private String departmentName;
    private String role;
    private String password;
    private String originPassword;
    private Boolean gender;
    private Date birth;
    private String birthStr;
    private String address;
    private Boolean isAdmin;
    private boolean isCurrentUser;
    private String companyId;
    private String aclient;
    private long idstr;
    @NotNull(
            message = "角色不能为空"
    )
    private Long roleId;
    private List<Long> roleIds;
    private List<Long> ids;
    private String nameMbLike;
    private List<Long> departmentIds;

}
