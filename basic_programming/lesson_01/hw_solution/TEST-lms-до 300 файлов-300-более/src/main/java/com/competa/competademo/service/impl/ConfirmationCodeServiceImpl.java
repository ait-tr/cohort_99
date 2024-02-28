package com.competa.competademo.service.impl;

import com.competa.competademo.entity.ConfirmationCode;
import com.competa.competademo.entity.User;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.ConfirmationCodesRepository;
import com.competa.competademo.service.ConfirmationCodeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Siedakov Serhii
 * created on 22.01.2024
 */

@Service("ConfirmationCodeService")
@RequiredArgsConstructor
public class ConfirmationCodeServiceImpl implements ConfirmationCodeService {

    private final ConfirmationCodesRepository confirmationCodesRepository;

    @Value("${expire.date}")
    private Long expireDate;

    @Override
    public String createCode(User user) {

        String codeValue = UUID.randomUUID().toString();

        ConfirmationCode code = ConfirmationCode.builder()
                .code(codeValue)
                .user(user)
                .isUsed(false)
                .expireTime(LocalDateTime.now().plusDays(expireDate))
                .build();

        confirmationCodesRepository.save(code);

        return codeValue;
    }

    @Override
    @Transactional
    public ConfirmationCode confirm(String code) {

        ConfirmationCode confirmationCode = confirmationCodesRepository.findByCodeAndExpireTimeAfter(code,LocalDateTime.now())
                .orElseThrow(() -> new RestApiException(HttpStatus.NOT_FOUND,"Code time is expired or not found"));
        if (confirmationCode.getIsUsed()){
            throw new RestApiException(HttpStatus.CONFLICT,"This code was used, please make new code");
        }
        return confirmationCode;
    }
}
