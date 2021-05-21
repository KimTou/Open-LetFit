package com.letfit.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author cjt
 * @date 2021/4/24 23:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements Serializable {

    private static final long serialVersionUID = 6777582135901380426L;

    private Integer adminId;

    @NotNull
    private String adminName;

    @NotNull
    private String password;

    private String role;

}
