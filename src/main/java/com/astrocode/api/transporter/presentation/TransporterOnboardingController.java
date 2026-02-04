package com.astrocode.api.transporter.presentation;


import com.astrocode.api.user.authservice.AuthenticatedUserService;
import com.astrocode.api.transporter.presentation.request.CreateTransporterRequest;
import com.astrocode.api.transporter.usecase.CreateTransporterUseCase;
import com.astrocode.api.transporter.usecase.command.CreateTransporterCommand;
import com.astrocode.api.user.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transporters")
public class TransporterOnboardingController {

    private final AuthenticatedUserService authenticatedUserService;
    private final CreateTransporterUseCase createTransporterUseCase;

    public TransporterOnboardingController(AuthenticatedUserService authenticatedUserService, CreateTransporterUseCase createTransporterUseCase) {
        this.authenticatedUserService = authenticatedUserService;
        this.createTransporterUseCase = createTransporterUseCase;
    }

    @PostMapping("/onboard")
    @PreAuthorize(("hasRole('TRANSPORTER"))
    public ResponseEntity<Void> onboard(@AuthenticationPrincipal Jwt jwt,
                                        @RequestBody CreateTransporterRequest body){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);

        CreateTransporterCommand command = new CreateTransporterCommand(
                user.getId(),
                body.displayName(),
                body.document(),
                body.phone(),
                body.address()
        );

        createTransporterUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
