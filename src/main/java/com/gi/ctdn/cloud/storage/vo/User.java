package com.gi.ctdn.cloud.storage.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by vincent on 16-12-19.
 */
@Data
public class User implements Serializable {
    private String id;
    private String email;
    private Integer roleId;
    private String realName;
    private Integer type;
}
