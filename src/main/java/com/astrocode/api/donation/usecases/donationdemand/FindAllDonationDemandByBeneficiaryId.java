package com.astrocode.api.donation.usecases.donationdemand;

import com.astrocode.api.donation.domain.DonationDemand;
import com.astrocode.api.donation.domain.interfacerepository.DonationDemandRepository;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class FindAllDonationDemandByBeneficiaryId {

    private final DonationDemandRepository donationDemandRepository;

    public FindAllDonationDemandByBeneficiaryId(DonationDemandRepository donationDemandRepository) {
        this.donationDemandRepository = donationDemandRepository;
    }

    public List<DonationDemand> execute(UUID beneficiaryId){
        if (donationDemandRepository.findAll().isEmpty()){
            throw DonationUseCasesException.emptyList("FIND_ALL_DEMANDS_BYBENEFICIARYID");
        }

        return donationDemandRepository.findByBeneficiaryId(beneficiaryId);
    }
}
