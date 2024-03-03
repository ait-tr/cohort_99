package com.competa.competademo.consts;

/**
 * @author Konstantin Glazunov
 * created on 12-Jan-24 1
 */
public abstract class ValidateConstants {
    public static final String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
}
