package com.astrocode.api.donation.usecases.donation;

import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.domain.interfacerepository.DonationRepository;
import com.astrocode.api.donation.usecases.donation.command.MarkDonationAsDeliveredCommand;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class MarkDonationAsDeliveredUseCase {

    private final DonationRepository donationRepository;

    public MarkDonationAsDeliveredUseCase(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public Donation execute(MarkDonationAsDeliveredCommand command){
        final var donation = donationRepository.findById(command.id())
                .orElseThrow(() -> DonationUseCasesException.notFound("MARK_DONATION_AS_DELIVERED", command.id()));

        final var deliveredDonation = donation.markDelivered();

        return donationRepository.save(deliveredDonation);
    }

}
