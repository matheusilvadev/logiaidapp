package com.astrocode.api.shared.vo;

import java.util.List;
import java.util.Objects;

public enum Roles {
    ADMIN,
    BENEFICIARY,
    DONOR,
    TRANSPORTER;

    public static Roles fromKeycloakRoles(List<String> roleNames) {
        return roleNames.stream()
                .map(name -> {
                    String normalized = name.replace("ROLE_", "");
                    try {
                        return Roles.valueOf(normalized);
                    } catch (IllegalArgumentException ex) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Nenhuma role do domínio encontrada no token: " + roleNames)
                );
    }
}
