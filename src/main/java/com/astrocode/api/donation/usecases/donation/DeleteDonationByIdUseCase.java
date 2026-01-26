package com.astrocode.api.donation.usecases.donation;

import com.astrocode.api.donation.domain.enums.DonationStatus;
import com.astrocode.api.donation.domain.interfacerepository.DonationRepository;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteDonationByIdUseCase {

    private final DonationRepository donationRepository;

    public DeleteDonationByIdUseCase(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public void execute(UUID donationId){
        final var donation = donationRepository.findById(donationId)
                .orElseThrow(() -> DonationUseCasesException.notFound("DELETE_DONATION_BYID_USECASE", donationId));

        if (donation.getStatus() != DonationStatus.CANCELED){
            throw DonationUseCasesException.notPossibleToDelete("DELETE_DONATION_BYID", "CANCELED");
        }

        donationRepository.deleteById(donationId);
    }
}
