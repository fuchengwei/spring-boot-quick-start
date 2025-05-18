package com.github.springbootquickstart.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseVO {
    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
