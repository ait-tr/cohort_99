package com.competa.competademo.exceptions;

import java.util.List;

public record ValidationError(List<ValidationFiledError> filedErrors) {
}
