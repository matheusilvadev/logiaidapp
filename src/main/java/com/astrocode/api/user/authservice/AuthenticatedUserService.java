package com.astrocode.api.user.authservice;


import com.astrocode.api.shared.vo.Email;
import com.astrocode.api.shared.vo.Roles;
import com.astrocode.api.user.domain.User;
import com.astrocode.api.user.usecases.ActivateUserUseCase;
import com.astrocode.api.user.usecases.CreateUserUseCase;
import com.astrocode.api.user.usecases.GetUserByAuthUserIdUseCase;
import com.astrocode.api.user.usecases.command.ActivateUserCommand;
import com.astrocode.api.user.usecases.command.CreateUserCommand;
import com.astrocode.api.user.usecases.exception.UserUseCasesException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AuthenticatedUserService {

    private final GetUserByAuthUserIdUseCase getUserByAuthUserIdUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final ActivateUserUseCase activateUserUseCase;

    public AuthenticatedUserService(GetUserByAuthUserIdUseCase getUserByAuthUserIdUseCase,
                                    CreateUserUseCase createUserUseCase,
                                    ActivateUserUseCase activateUserUseCase) {
        this.getUserByAuthUserIdUseCase = getUserByAuthUserIdUseCase;
        this.createUserUseCase = createUserUseCase;
        this.activateUserUseCase = activateUserUseCase;
    }

    public User getOrCreateFromJwt(Jwt jwt) {
        String authUserId = jwt.getClaim("sub");
        String email = jwt.getClaim("email");
        Boolean emailVerified = jwt.getClaim("email_verified");
        if (emailVerified == null) {
            emailVerified = Boolean.FALSE;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess == null) {
            throw new IllegalStateException("Token JWT não contém realm_access");
        }

        @SuppressWarnings("unchecked")
        List<String> roleNames = (List<String>) realmAccess.get("roles");
        if (roleNames == null || roleNames.isEmpty()) {
            throw new IllegalStateException("Token JWT não contém realm_access.roles");
        }

        Roles role = Roles.fromKeycloakRoles(roleNames);

        User user;
        try {
            user = getUserByAuthUserIdUseCase.execute(authUserId);
        } catch (UserUseCasesException e) {
            CreateUserCommand createCmd = new CreateUserCommand(
                    authUserId,
                    Email.of(email),
                    role
            );
            user = createUserUseCase.execute(createCmd);
        }

        if (emailVerified && !user.isActive()) {
            ActivateUserCommand activateCmd = new ActivateUserCommand(authUserId);
            activateUserUseCase.execute(activateCmd);
        }

        return user;
    }
}
