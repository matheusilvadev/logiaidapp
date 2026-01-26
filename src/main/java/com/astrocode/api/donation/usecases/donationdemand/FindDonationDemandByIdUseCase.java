package com.astrocode.api.donation.usecases.donationdemand;

import com.astrocode.api.donation.domain.DonationDemand;
import com.astrocode.api.donation.domain.interfacerepository.DonationDemandRepository;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class FindDonationDemandByIdUseCase {

    private final DonationDemandRepository donationDemandRepository;

    public FindDonationDemandByIdUseCase(DonationDemandRepository donationDemandRepository) {
        this.donationDemandRepository = donationDemandRepository;
    }

    public DonationDemand execute(UUID demandId){
        return donationDemandRepository.findById(demandId)
                .orElseThrow(() -> DonationUseCasesException.notFound("FIND_DEMAND_BYID", demandId));
    }

}
