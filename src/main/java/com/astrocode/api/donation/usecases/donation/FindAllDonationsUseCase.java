package com.astrocode.api.donation.usecases.donation;

import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.domain.interfacerepository.DonationRepository;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FindAllDonationsUseCase {

    private final DonationRepository donationRepository;

    public FindAllDonationsUseCase(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public List<Donation> execute(){
        if (donationRepository.findAll().isEmpty()){
            throw DonationUseCasesException.emptyList("FINDALL_DONATIONS_USECASE");
        }

        return donationRepository.findAll();
    }

}
