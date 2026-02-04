package com.astrocode.api.api.controller;


import com.astrocode.api.api.controller.dtos.CreateDonationRequest;
import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.usecases.donation.*;
import com.astrocode.api.donation.usecases.donation.command.CancelDonationCommand;
import com.astrocode.api.donation.usecases.donation.command.CreateDonationCommand;
import com.astrocode.api.donation.usecases.donation.command.MarkDonationAsDeliveredCommand;
import com.astrocode.api.donation.usecases.donation.command.MarkDonationAsInTransitCommand;
import com.astrocode.api.donor.domain.Donor;
import com.astrocode.api.donor.usecase.FindDonorByUserIdUseCase;
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
@RequestMapping("/donations")
public class DonationController {

    private final CreateDonationUseCase createDonationUseCase;
    private final CancelDonationUseCase cancelDonationUseCase;
    private final FindAllDonationsByDonorIdUseCase findAllDonationsByDonorIdUseCase;
    private final FindAllDonationsUseCase findAllDonationsUseCase;
    private final FindDonationByIdUseCase findDonationByIdUseCase;
    private final MakDonationAsInTransitUseCase makDonationAsInTransitUseCase;
    private final MarkDonationAsDeliveredUseCase markDonationAsDeliveredUseCase;
    private final AuthenticatedUserService authenticatedUserService;
    private final FindDonorByUserIdUseCase findDonorByUserIdUseCase;


    public DonationController(CreateDonationUseCase createDonationUseCase,
                              CancelDonationUseCase cancelDonationUseCase,
                              FindAllDonationsByDonorIdUseCase findAllDonationsByDonorIdUseCase,
                              FindAllDonationsUseCase findAllDonationsUseCase,
                              FindDonationByIdUseCase findDonationByIdUseCase,
                              MakDonationAsInTransitUseCase makDonationAsInTransitUseCase,
                              MarkDonationAsDeliveredUseCase markDonationAsDeliveredUseCase,
                              AuthenticatedUserService authenticatedUserService,
                              FindDonorByUserIdUseCase findDonorByUserIdUseCase) {

        this.createDonationUseCase = createDonationUseCase;
        this.cancelDonationUseCase = cancelDonationUseCase;
        this.findAllDonationsByDonorIdUseCase = findAllDonationsByDonorIdUseCase;
        this.findAllDonationsUseCase = findAllDonationsUseCase;
        this.findDonationByIdUseCase = findDonationByIdUseCase;
        this.makDonationAsInTransitUseCase = makDonationAsInTransitUseCase;
        this.markDonationAsDeliveredUseCase = markDonationAsDeliveredUseCase;
        this.authenticatedUserService = authenticatedUserService;
        this.findDonorByUserIdUseCase = findDonorByUserIdUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('DONOR')")
    public ResponseEntity<Donation> create(@AuthenticationPrincipal Jwt jwt,
                                           @RequestBody CreateDonationRequest body) {
        User user = authenticatedUserService.getOrCreateFromJwt(jwt);


        Donor donor = findDonorByUserIdUseCase.execute(user.getId());
        UUID donorId = donor.getId();

        CreateDonationCommand.LocationCommand locationCommand =
                new CreateDonationCommand.LocationCommand(
                        body.street(),
                        body.number(),
                        body.district(),
                        body.city(),
                        body.state()
                );


        CreateDonationCommand command = new CreateDonationCommand(
                body.demandId(),
                donorId,
                locationCommand
        );

        Donation created = createDonationUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @PatchMapping("/{beneficiaryId}/cancel")
    @PreAuthorize("hasRole('DONOR')")
    public ResponseEntity<Void> cancelDonation(@PathVariable UUID id,
                                               @AuthenticationPrincipal Jwt jwt) {

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        Donor donor = findDonorByUserIdUseCase.execute(user.getId());

        CancelDonationCommand command = new CancelDonationCommand(
                id,
                donor.getId()
        );

        cancelDonationUseCase.execute(command);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/donor/{donorId}")
    @PreAuthorize("hasRole('DONOR')")
    public ResponseEntity<List<Donation>> findAllByCurrentDonor(@AuthenticationPrincipal Jwt jwt) {
        User user = authenticatedUserService.getOrCreateFromJwt(jwt);

        Donor donor = findDonorByUserIdUseCase.execute(user.getId());

        List<Donation> donations = findAllDonationsByDonorIdUseCase.execute(donor.getId());

        return ResponseEntity.ok(donations);
    }

    @GetMapping("/donor/{donorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Donation>> findAllByDonorForAdmin(@PathVariable UUID donorId) {
        List<Donation> donations = findAllDonationsByDonorIdUseCase.execute(donorId);
        return ResponseEntity.ok(donations);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Donation>> findAllDonations() {
        List<Donation> donations = findAllDonationsUseCase.execute();
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/{beneficiaryId}")
    @PreAuthorize("hasAnyRole('ADMIN','DONOR','BENEFICIARY','TRANSPORTER')")
    public ResponseEntity<Donation> findById(@PathVariable UUID id) {
        Donation donation = findDonationByIdUseCase.execute(id);
        return ResponseEntity.ok(donation);
    }


    @PatchMapping("/{beneficiaryId}/in-transit")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<Donation> markAsInTransit(@PathVariable UUID id,
                                                    @AuthenticationPrincipal Jwt jwt){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        UUID transporterId = user.getId();

        var command = new MarkDonationAsInTransitCommand(
                id,
                transporterId
        );

        Donation updated = makDonationAsInTransitUseCase.execute(command);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{beneficiaryId}/delivered")
    @PreAuthorize("hasRole('BENEFICIARY')")
    public ResponseEntity<Donation> markAsDelivered(@PathVariable UUID id,
                                                    @AuthenticationPrincipal Jwt jwt) {
        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        UUID transporterId = user.getId();

        var command = new MarkDonationAsDeliveredCommand(id, transporterId);
        Donation updated = markDonationAsDeliveredUseCase.execute(command);

        return ResponseEntity.ok(updated);
    }

}
