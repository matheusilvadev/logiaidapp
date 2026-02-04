package com.astrocode.api.api.controller;


import com.astrocode.api.api.controller.dtos.ChangeAddressRequest;
import com.astrocode.api.api.controller.dtos.ChangeDisplayNameRequest;

import com.astrocode.api.api.controller.dtos.ChangePhoneRequest;
import com.astrocode.api.donor.domain.Donor;
import com.astrocode.api.donor.usecase.*;
import com.astrocode.api.donor.usecase.command.ChangeDonorAddressCommand;
import com.astrocode.api.donor.usecase.command.ChangeDonorDisplayNameCommand;
import com.astrocode.api.donor.usecase.command.ChangeDonorPhoneCommand;
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
@RequestMapping("/donors")
public class DonorController {

    private final ChangeDonorAddressUseCase changeDonorAddressUseCase;
    private final ChangeDonorDisplayNameUseCase changeDonorDisplayNameUseCase;
    private final ChangeDonorPhoneUseCase changeDonorPhoneUseCase;
    private final DeleteDonorByIdUseCase deleteDonorByIdUseCase;
    private final FindDonorByIdUseCase findDonorByIdUseCase;
    private final FindDonorByUserIdUseCase findDonorByUserIdUseCase;
    private final AuthenticatedUserService authenticatedUserService;

    public DonorController(ChangeDonorAddressUseCase changeDonorAddressUseCase, ChangeDonorDisplayNameUseCase changeDonorDisplayNameUseCase, ChangeDonorPhoneUseCase changeDonorPhoneUseCase, DeleteDonorByIdUseCase deleteDonorByIdUseCase, FindDonorByIdUseCase findDonorByIdUseCase, FindDonorByUserIdUseCase findDonorByUserIdUseCase, AuthenticatedUserService authenticatedUserService) {
        this.changeDonorAddressUseCase = changeDonorAddressUseCase;
        this.changeDonorDisplayNameUseCase = changeDonorDisplayNameUseCase;
        this.changeDonorPhoneUseCase = changeDonorPhoneUseCase;
        this.deleteDonorByIdUseCase = deleteDonorByIdUseCase;
        this.findDonorByIdUseCase = findDonorByIdUseCase;
        this.findDonorByUserIdUseCase = findDonorByUserIdUseCase;
        this.authenticatedUserService = authenticatedUserService;
    }

    @PatchMapping("/me/changeaddress")
    @PreAuthorize("hasRole('DONOR')")
    public ResponseEntity<Donor> changeAddress(@AuthenticationPrincipal Jwt jwt,
                                               @RequestBody ChangeAddressRequest body){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        Donor donor = findDonorByUserIdUseCase.execute(user.getId());

        Location newAddress = body.newAddress().toDomain();

        ChangeDonorAddressCommand command = new ChangeDonorAddressCommand(
                donor.getId(),
                newAddress
        );

        Donor updated = changeDonorAddressUseCase.execute(command);

        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/me/changedisplayname")
    @PreAuthorize("hasRole('DONOR')")
    public ResponseEntity<Donor> changeDisplayName(@AuthenticationPrincipal Jwt jwt,
                                                   @RequestBody ChangeDisplayNameRequest body){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        Donor donor = findDonorByUserIdUseCase.execute(user.getId());

        ChangeDonorDisplayNameCommand command = new ChangeDonorDisplayNameCommand(
                donor.getId(),
                body.newDisplayName()
        );

        Donor updated = changeDonorDisplayNameUseCase.execute(command);

        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/me/changephone")
    @PreAuthorize("hasRole('DONOR')")
    public ResponseEntity<Donor> changePhone(@AuthenticationPrincipal Jwt jwt,
                                             @RequestBody ChangePhoneRequest body){

        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        Donor donor = findDonorByUserIdUseCase.execute(user.getId());

        PhoneBR newPhone = body.phoneBR().toDomain();

        ChangeDonorPhoneCommand command = new ChangeDonorPhoneCommand(
                donor.getId(),
                newPhone
        );

        Donor updated = changeDonorPhoneUseCase.execute(command);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/me")
    @PreAuthorize("hasRole('DONOR')")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Jwt jwt){
        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        Donor donor = findDonorByUserIdUseCase.execute(user.getId());

        deleteDonorByIdUseCase.execute(donor.getId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{donorId}")
    @PreAuthorize("hasAnyRole('ADMIN','DONOR','BENEFICIARY','TRANSPORTER')")
    public ResponseEntity<Donor> findById(@PathVariable UUID donorId){
        Donor donor = findDonorByIdUseCase.execute(donorId);
        return ResponseEntity.ok(donor);
    }

    @GetMapping("/by-user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Donor> findByUserId(@PathVariable("userId") UUID userId){
        Donor donor = findDonorByUserIdUseCase.execute(userId);
        return ResponseEntity.ok(donor);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('DONOR')")
    public ResponseEntity<Donor> findMe(@AuthenticationPrincipal Jwt jwt){
        User user = authenticatedUserService.getOrCreateFromJwt(jwt);
        Donor donor = findDonorByUserIdUseCase.execute(user.getId());
        return ResponseEntity.ok(donor);
    }

}
