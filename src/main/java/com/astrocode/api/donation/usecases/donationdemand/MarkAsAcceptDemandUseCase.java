package com.astrocode.api.donation.usecases.donationdemand;

import com.astrocode.api.donation.domain.DonationDemand;
import com.astrocode.api.donation.domain.interfacerepository.DonationDemandRepository;
import com.astrocode.api.donation.usecases.donationdemand.command.MarkAsAcceptDemandCommand;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;


@Service
public class MarkAsAcceptDemandUseCase {

    private final DonationDemandRepository donationDemandRepository;

    public MarkAsAcceptDemandUseCase(DonationDemandRepository donationDemandRepository) {
        this.donationDemandRepository = donationDemandRepository;
    }

    public DonationDemand execute(MarkAsAcceptDemandCommand command){

        if (command == null){
            throw DonationUseCasesException.commandNull("MARK_AS_ACCEPT_COMMAND");
        }

        final var donationDemand = donationDemandRepository.findById(command.demandId())
                .orElseThrow(() -> DonationUseCasesException.notFound("MARK_AS_ACCEPT", command.demandId()));

        final var acceptedDemand = donationDemand.accept(command.donorId());

        return donationDemandRepository.save(acceptedDemand);
    }

}
