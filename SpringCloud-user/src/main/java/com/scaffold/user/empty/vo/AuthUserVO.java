package com.scaffold.user.empty.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author dongfeng
 * 2024-07-06 17:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> roles;
}
