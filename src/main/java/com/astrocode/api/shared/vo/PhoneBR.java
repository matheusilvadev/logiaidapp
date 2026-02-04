package com.astrocode.api.shared.vo;

import com.astrocode.api.shared.exception.VOException;

import java.util.Objects;

public class PhoneBR {

    private final String digits;

    private PhoneBR(String digits) {
        this.digits = digits;
    }

    public static PhoneBR of(String raw) {

        if (raw == null) throw VOException.required("PHONE_BR", "DIGITS");

        String d = raw.replaceAll("\\D", "");

        if (!(d.length() == 10 || d.length() == 11)) {
            throw VOException.length("PHONE_BR", "DIGITS", 11);
        }

        if (d.charAt(0) == '0' || d.charAt(1) == '0') {
            throw VOException.invalidFormat("PHONE_BR", "DIGITS", "DDD cannot be initiated with 0");
        }

        if (d.length() == 11 && d.charAt(2) != '9') {
            throw VOException.invalidFormat("PHONE_BR", "DIGITS", "If it has 11 digits, the 3rd digit (after the area code) is usually '9' (mobile phone number).");
        }

        return new PhoneBR(d);
    }

    public String digits() { return digits; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneBR other)) return false;
        return digits.equals(other.digits);
    }

    @Override public int hashCode() { return Objects.hash(digits); }

    @Override public String toString() { return digits; }

}
