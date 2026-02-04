package com.astrocode.api.donor.presentation;


import com.astrocode.api.user.authservice.AuthenticatedUserService;
import com.astrocode.api.donor.presentation.request.CreateDonorRequest;
import com.astrocode.api.donor.usecase.CreateDonorUseCase;
import com.astrocode.api.donor.usecase.command.CreateDonorCommand;
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
@RequestMapping("/donors")
public class DonorOnboardingController {

    private final AuthenticatedUserService authenticatedUserService;
    private final CreateDonorUseCase createDonorUseCase;

    public DonorOnboardingController(AuthenticatedUserService authenticatedUserService, CreateDonorUseCase createDonorUseCase) {
        this.authenticatedUserService = authenticatedUserService;
        this.createDonorUseCase = createDonorUseCase;
    }

    @PostMapping("/onboard")
    @PreAuthorize(("hasRole('DONOR"))
    public ResponseEntity<Void> onboard(@AuthenticationPrincipal Jwt jwt,
                                        @RequestBody CreateDonorRequest body){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);

        CreateDonorCommand command = new CreateDonorCommand(
                user.getId(),
                body.displayName(),
                body.document(),
                body.phone(),
                body.address()
        );

        createDonorUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
