package com.astrocode.api.donation.usecases.donationdemand;

import com.astrocode.api.donation.domain.DonationDemand;
import com.astrocode.api.donation.domain.interfacerepository.DonationDemandRepository;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FindAllDonationDemandUseCase {

    private final DonationDemandRepository donationDemandRepository;

    public FindAllDonationDemandUseCase(DonationDemandRepository donationDemandRepository) {
        this.donationDemandRepository = donationDemandRepository;
    }

    public List<DonationDemand> execute(){
        if (donationDemandRepository.findAll().isEmpty()){
            throw DonationUseCasesException.emptyList("FIND_ALL_DEMANDS");
        }

        return donationDemandRepository.findAll();
    }

}
