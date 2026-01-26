package com.astrocode.api.shared.exception;

public class VOException extends RuntimeException {

    private final String valueObject;
    private final String field;
    private final String code;

    public VOException(String message, String valueObject, String field, String code) {
        super(message);
        this.valueObject = valueObject;
        this.field = field;
        this.code = code;
    }

    public String getValueObject() {
        return valueObject;
    }

    public String getField() {
        return field;
    }

    public String getCode() {
        return code;
    }

    public static VOException required(String vo, String field) {
        String msg = "%s.%s cannot be null or blank".formatted(vo, field);
        return new VOException("vo.required", vo, field, msg);
    }

    public static VOException invalidValue(String vo, String field, String details) {
        String msg = "%s.%s has an invalid format: %s".formatted(vo, field, details);
        return new VOException("vo.invalid_format", vo, field, msg);
    }

    public static VOException invalidFormat(String vo, String field, String details) {
        String msg = "%s.%s has an invalid format: %s".formatted(vo, field, details);
        return new VOException("vo.invalid_format", vo, field, msg);
    }

    public static VOException length(String vo, String field, int expected) {
        String msg = "%s.%s must have %d characters".formatted(vo, field, expected);
        return new VOException("vo.invalid_length", vo, field, msg);
    }

    public static VOException invalidEmail(){
        return new VOException(
                "vo.email_invalid",
                "Email",
                "address",
                "Invalid email format"
        );
    }
}
