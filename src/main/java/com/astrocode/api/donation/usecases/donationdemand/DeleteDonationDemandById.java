package com.astrocode.api.donation.usecases.donationdemand;

import com.astrocode.api.donation.domain.enums.DemandStatus;
import com.astrocode.api.donation.domain.interfacerepository.DonationDemandRepository;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class DeleteDonationDemandById {

    private final DonationDemandRepository donationDemandRepository;

    public DeleteDonationDemandById(DonationDemandRepository donationDemandRepository) {
        this.donationDemandRepository = donationDemandRepository;
    }

    public void execute(UUID demandId){
        final var donationDemand = donationDemandRepository.findById(demandId)
                        .orElseThrow(() -> DonationUseCasesException.notFound("DELETE_DEMAND_BYID", demandId));

        if (donationDemand.getDemandStatus() != DemandStatus.CANCELED){
            throw DonationUseCasesException.notPossibleToDelete("DELETE_DEMAND_BYID", "CANCELED");
        }

        donationDemandRepository.deleteById(demandId);
    }

}
