package com.infobridge.bridge.empty.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 内部静态类是不能实例化的
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloDTO {
    private String name;
}