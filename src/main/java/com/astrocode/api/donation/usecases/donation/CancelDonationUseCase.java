package com.astrocode.api.donation.usecases.donation;

import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.domain.interfacerepository.DonationRepository;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class CancelDonationUseCase {

    private final DonationRepository donationRepository;

    public CancelDonationUseCase(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public Donation execute(UUID donationId){
        final var donation = donationRepository.findById(donationId)
                .orElseThrow(() -> DonationUseCasesException.notFound("CANCEL_DONATION_USECASE", donationId));

        final var canceledDonation = donation.cancel();

        return donationRepository.save(canceledDonation);
    }
}
