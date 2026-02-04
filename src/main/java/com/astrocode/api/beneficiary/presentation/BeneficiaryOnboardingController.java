package com.astrocode.api.beneficiary.presentation;


import com.astrocode.api.user.authservice.AuthenticatedUserService;
import com.astrocode.api.beneficiary.presentation.request.CreateBeneficiaryRequest;
import com.astrocode.api.beneficiary.usecases.CreateBeneficiaryUseCase;
import com.astrocode.api.beneficiary.usecases.command.CreateBeneficiaryCommand;
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
@RequestMapping("/beneficiaries")
public class BeneficiaryOnboardingController {

    private final AuthenticatedUserService authenticatedUserService;
    private final CreateBeneficiaryUseCase createBeneficiaryUseCase;

    public BeneficiaryOnboardingController(AuthenticatedUserService authenticatedUserService,
                                           CreateBeneficiaryUseCase createBeneficiaryUseCase) {
        this.authenticatedUserService = authenticatedUserService;
        this.createBeneficiaryUseCase = createBeneficiaryUseCase;
    }

    @PostMapping("/onboard")
    @PreAuthorize("hasRole('BENEFICIARY')")
    public ResponseEntity<Void> onboard( @AuthenticationPrincipal Jwt jwt,
                                         @RequestBody CreateBeneficiaryRequest body){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);

        CreateBeneficiaryCommand command = new CreateBeneficiaryCommand(
                user.getId(),
                body.displayName(),
                body.document(),
                body.phone(),
                body.address()
        );

        createBeneficiaryUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
