package com.astrocode.api.donation.usecases.donation;

import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.domain.interfacerepository.DonationRepository;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class FindAllDonationsByDonorIdUseCase {

    private final DonationRepository donationRepository;

    public FindAllDonationsByDonorIdUseCase(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public List<Donation> execute(UUID donorId){
        if (donationRepository.findAll().isEmpty()){
            throw DonationUseCasesException.emptyList("FIND_DONATIONS_BYDONORID");
        }

        return donationRepository.findByDonorId(donorId);
    }

}
