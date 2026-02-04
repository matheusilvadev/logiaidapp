package com.astrocode.api.api.controller;


import com.astrocode.api.api.controller.dtos.ChangeAddressRequest;
import com.astrocode.api.api.controller.dtos.ChangeDisplayNameRequest;
import com.astrocode.api.api.controller.dtos.ChangePhoneRequest;
import com.astrocode.api.beneficiary.domain.Beneficiary;
import com.astrocode.api.beneficiary.usecases.*;
import com.astrocode.api.beneficiary.usecases.command.ChangeBeneficiaryAddressCommand;
import com.astrocode.api.beneficiary.usecases.command.ChangeBeneficiaryPhoneCommand;
import com.astrocode.api.beneficiary.usecases.command.ChangeDisplayNameCommand;

import com.astrocode.api.shared.vo.Location;
import com.astrocode.api.shared.vo.PhoneBR;
import com.astrocode.api.user.authservice.AuthenticatedUserService;
import com.astrocode.api.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/beneficiaries")
public class BeneficiaryController {

    private final ChangeBeneficiaryAddressUseCase changeBeneficiaryAddressUseCase;
    private final ChangeBeneficiaryPhoneUseCase changeBeneficiaryPhoneUseCase;
    private final ChangeDisplayNameUseCase changeDisplayNameUseCase;
    private final DeleteBeneficiaryByIdUseCase deleteBeneficiaryByIdUseCase;
    private final FindBeneficiaryByIdUseCase findBeneficiaryByIdUseCase;
    private final FindBeneficiaryByUserIdUseCase findBeneficiaryByUserIdUseCase;
    private final AuthenticatedUserService authenticatedUserService;


    public BeneficiaryController(ChangeBeneficiaryAddressUseCase changeBeneficiaryAddressUseCase,
                                 ChangeBeneficiaryPhoneUseCase changeBeneficiaryPhoneUseCase,
                                 ChangeDisplayNameUseCase changeDisplayNameUseCase,
                                 DeleteBeneficiaryByIdUseCase deleteBeneficiaryByIdUseCase,
                                 FindBeneficiaryByIdUseCase findBeneficiaryByIdUseCase,
                                 FindBeneficiaryByUserIdUseCase findBeneficiaryByUserIdUseCase,
                                 AuthenticatedUserService authenticatedUserService) {

        this.changeBeneficiaryAddressUseCase = changeBeneficiaryAddressUseCase;
        this.changeBeneficiaryPhoneUseCase = changeBeneficiaryPhoneUseCase;
        this.changeDisplayNameUseCase = changeDisplayNameUseCase;
        this.deleteBeneficiaryByIdUseCase = deleteBeneficiaryByIdUseCase;
        this.findBeneficiaryByIdUseCase = findBeneficiaryByIdUseCase;
        this.findBeneficiaryByUserIdUseCase = findBeneficiaryByUserIdUseCase;
        this.authenticatedUserService = authenticatedUserService;
    }

    @PatchMapping("/me/changeaddress")
    @PreAuthorize("hasRole('BENEFICIARY')")
    public ResponseEntity<Beneficiary> changeAddress(@AuthenticationPrincipal Jwt jwt,
                                                     @RequestBody ChangeAddressRequest body){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        Beneficiary beneficiary = findBeneficiaryByUserIdUseCase.execute(user.getId());

        Location newAddress = body.newAddress().toDomain();

        ChangeBeneficiaryAddressCommand command = new ChangeBeneficiaryAddressCommand(
                beneficiary.getId(),
                newAddress
        );

        Beneficiary updated = changeBeneficiaryAddressUseCase.execute(command);

        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/me/changephone")
    @PreAuthorize("hasRole('BENEFICIARY')")
    public ResponseEntity<Beneficiary> changePhone(@AuthenticationPrincipal Jwt jwt,
                                                   @RequestBody ChangePhoneRequest body){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        Beneficiary beneficiary = findBeneficiaryByUserIdUseCase.execute(user.getId());

        PhoneBR newPhone = body.phoneBR().toDomain();

        ChangeBeneficiaryPhoneCommand command = new ChangeBeneficiaryPhoneCommand(
                beneficiary.getId(),
                newPhone
        );

        Beneficiary updated = changeBeneficiaryPhoneUseCase.execute(command);

        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/me/changedisplayname")
    @PreAuthorize("hasRole('BENEFICIARY')")
    public ResponseEntity<Beneficiary> changeDisplayName(@AuthenticationPrincipal Jwt jwt,
                                                         @RequestBody ChangeDisplayNameRequest body){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        Beneficiary beneficiary = findBeneficiaryByUserIdUseCase.execute(user.getId());

        ChangeDisplayNameCommand command = new ChangeDisplayNameCommand(
                beneficiary.getId(),
                body.newDisplayName()
        );

        Beneficiary updated = changeDisplayNameUseCase.execute(command);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/me")
    @PreAuthorize("hasRole('BENEFICIARY')")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Jwt jwt){
        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        Beneficiary beneficiary = findBeneficiaryByUserIdUseCase.execute(user.getId());

        deleteBeneficiaryByIdUseCase.execute(beneficiary.getId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{beneficiaryId}")
    @PreAuthorize("hasAnyRole('ADMIN','DONOR','BENEFICIARY','TRANSPORTER')")
    public ResponseEntity<Beneficiary> findById(@PathVariable UUID beneficiaryId){
        Beneficiary beneficiary = findBeneficiaryByIdUseCase.execute(beneficiaryId);
        return ResponseEntity.ok(beneficiary);
    }

    @GetMapping("/by-user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Beneficiary> findByUserId(@PathVariable("userId") UUID userId){
        Beneficiary beneficiary = findBeneficiaryByUserIdUseCase.execute(userId);
        return ResponseEntity.ok(beneficiary);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('BENEFICIARY')")
    public ResponseEntity<Beneficiary> findMe(@AuthenticationPrincipal Jwt jwt){
        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        Beneficiary beneficiary = findBeneficiaryByUserIdUseCase.execute(user.getId());
        return ResponseEntity.ok(beneficiary);
    }

}
