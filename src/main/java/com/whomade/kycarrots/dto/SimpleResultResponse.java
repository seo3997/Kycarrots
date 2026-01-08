package com.whomade.kycarrots.dto;

public record SimpleResultResponse(
        boolean result,
        String message
) {
    public static SimpleResultResponse ok(String message) {
        return new SimpleResultResponse(true, message);
    }

    public static SimpleResultResponse fail(String message) {
        return new SimpleResultResponse(false, message);
    }
}
