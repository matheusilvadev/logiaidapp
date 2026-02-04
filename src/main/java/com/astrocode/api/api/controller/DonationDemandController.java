package com.astrocode.api.api.controller;


import com.astrocode.api.api.controller.dtos.CreateDemandRequest;
import com.astrocode.api.beneficiary.domain.Beneficiary;
import com.astrocode.api.beneficiary.usecases.FindBeneficiaryByUserIdUseCase;

import com.astrocode.api.donation.domain.DonationDemand;
import com.astrocode.api.donation.usecases.donationdemand.*;

import com.astrocode.api.donation.usecases.donationdemand.command.CreateDonationDemandCommand;

import com.astrocode.api.donation.usecases.donationdemand.command.MarkAsAcceptDemandCommand;
import com.astrocode.api.donation.usecases.donationdemand.command.MarkAsCanceledDemandCommand;
import com.astrocode.api.donation.usecases.donationdemand.command.MarksAsFulfilledDemandCommand;
import com.astrocode.api.user.authservice.AuthenticatedUserService;
import com.astrocode.api.user.domain.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/demands")
public class DonationDemandController {

    private final CreateDonationDemandUseCase createDonationDemandUseCase;
    private final FindAllDonationDemandByBeneficiaryId findAllDonationDemandByBeneficiaryId;
    private final FindAllDonationDemandUseCase findAllDonationDemandUseCase;
    private final FindDonationDemandByIdUseCase findDonationDemandByIdUseCase;
    private final MarkAsAcceptDemandUseCase markAsAcceptDemandUseCase;
    private final MarkAsCanceledDemandUseCase markAsCanceledDemandUseCase;
    private final MarkAsFulfilledDemandUseCase markAsFulfilledDemandUseCase;
    private final AuthenticatedUserService authenticatedUserService;
    private final FindBeneficiaryByUserIdUseCase findBeneficiaryByUserIdUseCase;


    public DonationDemandController(CreateDonationDemandUseCase createDonationDemandUseCase,
                                    FindAllDonationDemandByBeneficiaryId findAllDonationDemandByBeneficiaryId,
                                    FindAllDonationDemandUseCase findAllDonationDemandUseCase,
                                    FindDonationDemandByIdUseCase findDonationDemandByIdUseCase,
                                    MarkAsAcceptDemandUseCase markAsAcceptDemandUseCase,
                                    MarkAsCanceledDemandUseCase markAsCanceledDemandUseCase,
                                    MarkAsFulfilledDemandUseCase markAsFulfilledDemandUseCase, AuthenticatedUserService authenticatedUserService, FindBeneficiaryByUserIdUseCase findBeneficiaryByUserIdUseCase) {

        this.createDonationDemandUseCase = createDonationDemandUseCase;
        this.findAllDonationDemandByBeneficiaryId = findAllDonationDemandByBeneficiaryId;
        this.findAllDonationDemandUseCase = findAllDonationDemandUseCase;
        this.findDonationDemandByIdUseCase = findDonationDemandByIdUseCase;
        this.markAsAcceptDemandUseCase = markAsAcceptDemandUseCase;
        this.markAsCanceledDemandUseCase = markAsCanceledDemandUseCase;
        this.markAsFulfilledDemandUseCase = markAsFulfilledDemandUseCase;
        this.authenticatedUserService = authenticatedUserService;
        this.findBeneficiaryByUserIdUseCase = findBeneficiaryByUserIdUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('BENEFICIARY')")
    public ResponseEntity<DonationDemand> create(@AuthenticationPrincipal Jwt jwt,
                                           @RequestBody CreateDemandRequest body){
        User user = authenticatedUserService.getOrCreateFromJwt(jwt);

        Beneficiary beneficiary = findBeneficiaryByUserIdUseCase.execute(user.getId());
        UUID beneficiaryId = beneficiary.getId();

        CreateDonationDemandCommand command = new CreateDonationDemandCommand(
                beneficiaryId,
                body.deliveryLocation(),
                body.items(),
                body.notes()
        );

        DonationDemand created = createDonationDemandUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/beneficiary/{beneficiaryId}/")
    @PreAuthorize(("hasRole('BENEFICIARY')"))
    public ResponseEntity<List<DonationDemand>> findAllByCurrentBeneficiary(@AuthenticationPrincipal Jwt jwt){
        User user = authenticatedUserService.getOrCreateFromJwt(jwt);

        Beneficiary beneficiary = findBeneficiaryByUserIdUseCase.execute(user.getId());

        List<DonationDemand> demands = findAllDonationDemandByBeneficiaryId.execute(beneficiary.getId());

        return ResponseEntity.ok(demands);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DonationDemand>> findAllDemands(){
        List<DonationDemand> demands = findAllDonationDemandUseCase.execute();
        return ResponseEntity.ok(demands);
    }

    @GetMapping("/{beneficiaryId}")
    @PreAuthorize("hasRole('ADMIN','DONOR','BENEFICIARY','TRANSPORTER')")
    public ResponseEntity<DonationDemand> findById(@PathVariable UUID id){
        DonationDemand donationDemand = findDonationDemandByIdUseCase.execute(id);
        return ResponseEntity.ok(donationDemand);
    }

    @PatchMapping("/{beneficiaryId}/accepted")
    @PreAuthorize("hasRole('DONOR')")
    public ResponseEntity<DonationDemand> markAsAccepted(@PathVariable UUID id,
                                                         @AuthenticationPrincipal Jwt jwt){
        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        UUID donorId = user.getId();

        var command = new MarkAsAcceptDemandCommand(id, donorId);
        DonationDemand updated = markAsAcceptDemandUseCase.execute(command);

        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{beneficiaryId}/canceled")
    @PreAuthorize("hasRole('BENEFICIARY')")
    public ResponseEntity<DonationDemand> markAsCanceled(@PathVariable UUID id,
                                                         @AuthenticationPrincipal Jwt jwt){
        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        UUID beneficiaryId = user.getId();

        var command = new MarkAsCanceledDemandCommand(id, beneficiaryId);
        DonationDemand updated = markAsCanceledDemandUseCase.execute(command);

        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{beneficiaryId}/canceled")
    @PreAuthorize("hasRole('BENEFICIARY')")
    public ResponseEntity<DonationDemand> markAsFulfilled(@PathVariable UUID id,
                                                          @AuthenticationPrincipal Jwt jwt){
        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        UUID donorId = user.getId();

        var command = new MarksAsFulfilledDemandCommand(id, donorId);
        DonationDemand updated = markAsFulfilledDemandUseCase.execute(command);

        return ResponseEntity.ok(updated);
    }



}
