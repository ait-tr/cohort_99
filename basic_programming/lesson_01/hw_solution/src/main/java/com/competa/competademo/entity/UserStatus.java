package com.competa.competademo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    NOT_CONFIRMED,
    CONFIRMED,
    DELETED,
    BANNED
}
