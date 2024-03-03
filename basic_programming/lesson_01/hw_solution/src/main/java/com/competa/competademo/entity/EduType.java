package com.competa.competademo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EduType {
    PRIMARY(0, "Primary (4 years)"),
    BASIC(1, "Basic (9 years)"),
    SECONDARY(2, "Secondary (11 years))"),
    SEC_PROF(3, "Profession (10-11 years)"),
    BACHELOR(4, "Bachelor (4 years)"),
    MASTER(5, "Master"),
    HIGH(6, "High education(University)"),
    SEC_HIGH(7, "Two and more high education"),
    PHD(8, "PhD");

    private final int id;
    private final String description;

}
