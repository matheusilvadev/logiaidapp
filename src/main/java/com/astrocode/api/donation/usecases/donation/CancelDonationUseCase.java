package com.astrocode.api.donation.usecases.donation;

import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.domain.interfacerepository.DonationRepository;
import com.astrocode.api.donation.usecases.donation.command.CancelDonationCommand;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class CancelDonationUseCase {

    private final DonationRepository donationRepository;

    public CancelDonationUseCase(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public Donation execute(CancelDonationCommand command){

        final var donation = donationRepository.findById(command.donationId())
                .orElseThrow(() -> DonationUseCasesException.notFound("CANCEL_DONATION_USECASE", command.donationId()));

        if (!donation.getDonorId().equals(command.donorId())) {
            throw DonationUseCasesException.notOwner(
                    "CANCEL_DONATION_USECASE",
                    command.donationId(),
                    command.donorId()
            );
        }

        final var canceledDonation = donation.cancel();

        return donationRepository.save(canceledDonation);
    }
}
