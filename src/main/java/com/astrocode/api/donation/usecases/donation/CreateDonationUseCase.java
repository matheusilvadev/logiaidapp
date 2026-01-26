package com.astrocode.api.donation.usecases.donation;

import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.domain.interfacerepository.DonationRepository;
import com.astrocode.api.donation.usecases.donation.command.CreateDonationCommand;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import com.astrocode.api.shared.vo.Location;
import org.springframework.stereotype.Service;


@Service
public class CreateDonationUseCase {

    private final DonationRepository donationRepository;

    public CreateDonationUseCase(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public Donation execute(CreateDonationCommand command){
        if (command == null){
            throw DonationUseCasesException.commandNull("CREATE_DONATION_COMMAND");
        }

        Location pickupLocation = Location.of(
                command.location().street(),
                command.location().number(),
                command.location().district(),
                command.location().city(),
                command.location().state()
        );

        final Donation donation = Donation.create(
                command.demandId(),
                command.donorId(),
                pickupLocation
        );

        return donationRepository.save(donation);
    }

}
