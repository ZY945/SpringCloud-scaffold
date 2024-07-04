package com.scaffold.commons.utils.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TimeEnum {
    HOUR(10),
    MINUTE(12),
    SECOND(13);
    private int value;
}
