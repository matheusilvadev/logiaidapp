package com.astrocode.api.api.controller;


import com.astrocode.api.api.controller.dtos.ChangeAddressRequest;
import com.astrocode.api.api.controller.dtos.ChangeDisplayNameRequest;
import com.astrocode.api.api.controller.dtos.ChangePhoneRequest;
import com.astrocode.api.beneficiary.domain.Beneficiary;
import com.astrocode.api.beneficiary.usecases.command.ChangeBeneficiaryPhoneCommand;
import com.astrocode.api.shared.vo.Location;
import com.astrocode.api.shared.vo.PhoneBR;
import com.astrocode.api.transporter.domain.Transporter;
import com.astrocode.api.transporter.usecase.*;
import com.astrocode.api.transporter.usecase.command.ChangeTransporterAddressCommand;
import com.astrocode.api.transporter.usecase.command.ChangeTransporterDisplayNameCommand;
import com.astrocode.api.transporter.usecase.command.ChangeTransporterPhoneCommand;
import com.astrocode.api.user.authservice.AuthenticatedUserService;
import com.astrocode.api.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transporters")
public class TransporterController {

    private final ChangeTransporterAddressUseCase changeTransporterAddressUseCase;
    private final ChangeTransporterDisplayNameUseCase changeTransporterDisplayNameUseCase;
    private final ChangeTransporterPhoneUseCase changeTransporterPhoneUseCase;
    private final DeleteTransporterUseCase deleteTransporterUseCase;
    private final FindTransporterByIdUseCase findTransporterByIdUseCase;
    private final FindTransporterByUserIdUseCase findTransporterByUserIdUseCase;
    private final AuthenticatedUserService authenticatedUserService;

    public TransporterController(ChangeTransporterAddressUseCase changeTransporterAddressUseCase, ChangeTransporterDisplayNameUseCase changeTransporterDisplayNameUseCase, ChangeTransporterPhoneUseCase changeTransporterPhoneUseCase, DeleteTransporterUseCase deleteTransporterUseCase, FindTransporterByIdUseCase findTransporterByIdUseCase, FindTransporterByUserIdUseCase findTransporterByUserIdUseCase, AuthenticatedUserService authenticatedUserService) {
        this.changeTransporterAddressUseCase = changeTransporterAddressUseCase;
        this.changeTransporterDisplayNameUseCase = changeTransporterDisplayNameUseCase;
        this.changeTransporterPhoneUseCase = changeTransporterPhoneUseCase;
        this.deleteTransporterUseCase = deleteTransporterUseCase;
        this.findTransporterByIdUseCase = findTransporterByIdUseCase;
        this.findTransporterByUserIdUseCase = findTransporterByUserIdUseCase;
        this.authenticatedUserService = authenticatedUserService;
    }

    @PatchMapping("/me/changeaddress")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<Transporter> changeAddress(@AuthenticationPrincipal Jwt jwt,
                                                     @RequestBody ChangeAddressRequest body){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        Transporter transporter = findTransporterByUserIdUseCase.execute(user.getId());

        Location newAddress = body.newAddress().toDomain();

        ChangeTransporterAddressCommand command = new ChangeTransporterAddressCommand(
                transporter.getId(),
                newAddress
        );

        Transporter updated = changeTransporterAddressUseCase.execute(command);

        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/me/changedisplayname")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<Transporter> changeDisplayName(@AuthenticationPrincipal Jwt jwt,
                                                         @RequestBody ChangeDisplayNameRequest body){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        Transporter transporter = findTransporterByUserIdUseCase.execute(user.getId());

        ChangeTransporterDisplayNameCommand command = new ChangeTransporterDisplayNameCommand(
                transporter.getId(),
                body.newDisplayName()
        );

        Transporter updated = changeTransporterDisplayNameUseCase.execute(command);

        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/me/changephone")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<Transporter> changePhone(@AuthenticationPrincipal Jwt jwt,
                                                   @RequestBody ChangePhoneRequest body){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        Transporter transporter = findTransporterByUserIdUseCase.execute(user.getId());

        PhoneBR newPhone = body.phoneBR().toDomain();

        ChangeTransporterPhoneCommand command = new ChangeTransporterPhoneCommand(
                transporter.getId(),
                newPhone
        );

        Transporter updated = changeTransporterPhoneUseCase.execute(command);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/me")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Jwt jwt){
        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        Transporter transporter = findTransporterByUserIdUseCase.execute(user.getId());

        deleteTransporterUseCase.execute(transporter.getId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{transporterId}")
    @PreAuthorize("hasAnyRole('ADMIN','DONOR','BENEFICIARY','TRANSPORTER')")
    public ResponseEntity<Transporter> findById(@PathVariable UUID transporterId){
        Transporter transporter = findTransporterByIdUseCase.execute(transporterId);
        return ResponseEntity.ok(transporter);
    }

    @GetMapping("/by-user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Transporter> findByUserId(@PathVariable("userId") UUID userId){
        Transporter transporter = findTransporterByIdUseCase.execute(userId);
        return ResponseEntity.ok(transporter);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<Transporter> findMe(@AuthenticationPrincipal Jwt jwt){
        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        Transporter transporter = findTransporterByIdUseCase.execute(user.getId());
        return ResponseEntity.ok(transporter);
    }
}
