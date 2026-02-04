package com.astrocode.api.shared.vo;

import com.astrocode.api.shared.exception.VOException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {

    private static final Pattern PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email of(String raw) {
        if (raw == null) throw VOException.required("EMAIL", "ADDRESS");
        String v = raw.trim();

        if (v.isBlank()) throw VOException.required("EMAIL", "ADDRESS");
        if (v.length() > 180) throw VOException.invalidFormat("EMAIL", "ADDRESS", "Less than 180");
        if (!PATTERN.matcher(v).matches()) throw VOException.invalidEmail();

        return new Email(v.toLowerCase());
    }

    public String value() { return value; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email other)) return false;
        return value.equals(other.value);
    }

    @Override public int hashCode() { return Objects.hash(value); }

    @Override public String toString() { return value; }

}
