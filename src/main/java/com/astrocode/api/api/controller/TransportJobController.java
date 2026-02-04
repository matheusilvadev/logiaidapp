package com.astrocode.api.api.controller;


import com.astrocode.api.api.controller.dtos.CreateTransportJobRequest;
import com.astrocode.api.transporter.domain.Transporter;
import com.astrocode.api.transporter.usecase.FindTransporterByUserIdUseCase;
import com.astrocode.api.transportjob.domain.TransportJob;
import com.astrocode.api.transportjob.usecase.*;
import com.astrocode.api.transportjob.usecase.command.ChangeTransportStatusCommand;
import com.astrocode.api.transportjob.usecase.command.CreateTransportJobCommand;
import com.astrocode.api.user.authservice.AuthenticatedUserService;
import com.astrocode.api.user.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transportjob")
public class TransportJobController {

    private final CreateTransportJobUseCase createTransportJobUseCase;
    private final FindTransportJobByIdUseCase findTransportJobByIdUseCase;
    private final MarkTransportAsDeliveredUseCase markTransportAsDeliveredUseCase;
    private final MarkTransportAsInTransitUseCase markTransportAsInTransitUseCase;
    private final MarkTransportAsPickedUpUseCase markTransportAsPickedUpUseCase;
    private final AuthenticatedUserService authenticatedUserService;

    private final FindTransporterByUserIdUseCase findTransporterByUserIdUseCase;

    public TransportJobController(CreateTransportJobUseCase createTransportJobUseCase,
                                  FindTransportJobByIdUseCase findTransportJobByIdUseCase,
                                  MarkTransportAsDeliveredUseCase markTransportAsDeliveredUseCase,
                                  MarkTransportAsInTransitUseCase markTransportAsInTransitUseCase,
                                  MarkTransportAsPickedUpUseCase markTransportAsPickedUpUseCase,
                                  AuthenticatedUserService authenticatedUserService, FindTransporterByUserIdUseCase findTransporterByUserIdUseCase) {

        this.createTransportJobUseCase = createTransportJobUseCase;
        this.findTransportJobByIdUseCase = findTransportJobByIdUseCase;
        this.markTransportAsDeliveredUseCase = markTransportAsDeliveredUseCase;
        this.markTransportAsInTransitUseCase = markTransportAsInTransitUseCase;
        this.markTransportAsPickedUpUseCase = markTransportAsPickedUpUseCase;
        this.authenticatedUserService = authenticatedUserService;

        this.findTransporterByUserIdUseCase = findTransporterByUserIdUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<TransportJob> create(@AuthenticationPrincipal Jwt jwt,
                                               @RequestBody CreateTransportJobRequest body){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);

        Transporter transporter = findTransporterByUserIdUseCase.execute(user.getId());
        UUID transporterId = transporter.getId();

        CreateTransportJobCommand command = new CreateTransportJobCommand(
                body.donationId(),
                transporterId
        );

        TransportJob created = createTransportJobUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }

    @GetMapping("/{beneficiaryId}")
    @PreAuthorize("hasAnyRole('ADMIN','DONOR','BENEFICIARY','TRANSPORTER')")
    public ResponseEntity<TransportJob> findById(@PathVariable UUID id){
        TransportJob transportJob = findTransportJobByIdUseCase.execute(id);
        return ResponseEntity.ok(transportJob);
    }

    @PatchMapping("/{beneficiaryId}/delivered")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<TransportJob> markAsDelivered(@PathVariable UUID id,
                                                        @AuthenticationPrincipal Jwt jwt){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        UUID transporterId = user.getId();

        var command = new ChangeTransportStatusCommand(
                id,
                transporterId
        );

        TransportJob updated = markTransportAsDeliveredUseCase.execute(command);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{beneficiaryId}/intransit")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<TransportJob> markAsInTransit(@PathVariable UUID id,
                                                        @AuthenticationPrincipal Jwt jwt){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        UUID transporterId = user.getId();

        var command = new ChangeTransportStatusCommand(
                id,
                transporterId
        );

        TransportJob updated = markTransportAsInTransitUseCase.execute(command);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{beneficiaryId}/intransit")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<TransportJob> markAsPickedUp(@PathVariable UUID id,
                                                        @AuthenticationPrincipal Jwt jwt){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        UUID transporterId = user.getId();

        var command = new ChangeTransportStatusCommand(
                id,
                transporterId
        );

        TransportJob updated = markTransportAsPickedUpUseCase.execute(command);
        return ResponseEntity.ok(updated);
    }

}
