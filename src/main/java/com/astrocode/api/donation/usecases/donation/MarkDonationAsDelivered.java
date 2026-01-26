package com.astrocode.api.donation.usecases.donation;

import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.domain.interfacerepository.DonationRepository;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class MarkDonationAsDelivered {

    private final DonationRepository donationRepository;

    public MarkDonationAsDelivered(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public Donation execute(UUID donationId){
        final var donation = donationRepository.findById(donationId)
                .orElseThrow(() -> DonationUseCasesException.notFound("MARK_DONATION_AS_DELIVERED", donationId));

        final var deliveredDonation = donation.markDelivered();

        return donationRepository.save(deliveredDonation);
    }

}
