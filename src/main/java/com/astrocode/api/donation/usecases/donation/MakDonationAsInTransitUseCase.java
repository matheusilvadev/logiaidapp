package com.astrocode.api.donation.usecases.donation;

import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.domain.interfacerepository.DonationRepository;
import com.astrocode.api.donation.usecases.donation.command.MarkDonationAsInTransitCommand;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;


@Service
public class MakDonationAsInTransitUseCase {

    private final DonationRepository donationRepository;

    public MakDonationAsInTransitUseCase(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public Donation execute(MarkDonationAsInTransitCommand command){
        if (command == null){
            throw DonationUseCasesException.commandNull("MARK_DONATION_AS_IN_TRANSIT_COMMAND");
        }

        final var donation = donationRepository.findById(command.donationId())
                .orElseThrow(() -> DonationUseCasesException.notFound("MARK_DONATION_AS_IN_TRANSIT_USECASE", command.donationId()));

        final var inTransitDonation = donation.markInTransit(command.transporterId());

        return donationRepository.save(inTransitDonation);
    }

}
