package com.astrocode.api.donation.usecases.donation;

import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.domain.interfacerepository.DonationRepository;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class FindDonationByIdUseCase {

    private final DonationRepository donationRepository;

    public FindDonationByIdUseCase(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public Donation execute(UUID donationId){
        return donationRepository.findById(donationId)
                .orElseThrow(() -> DonationUseCasesException.notFound("FIND_DONATION_BYID", donationId));
    }

}
