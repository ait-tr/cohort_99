package com.competa.competademo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompetaType {
    EDU(1, "Education"),
    JOB(2, "Job"),
    HARDSKILL(3, "Hard skill"),
    SOFTSKILL(4, "Soft skill");

    private final int id;
    private final String title;

}
