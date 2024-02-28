package com.competa.competademo.service;

import com.competa.competademo.entity.ConfirmationCode;
import com.competa.competademo.entity.User;

/**
 * @author Siedakov Serhii
 * created on 22.01.2024
 */

public interface ConfirmationCodeService {
    String createCode(final User user);
    ConfirmationCode confirm (final String code);
}
