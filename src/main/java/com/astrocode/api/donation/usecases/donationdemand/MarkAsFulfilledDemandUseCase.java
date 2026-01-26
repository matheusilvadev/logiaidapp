package com.astrocode.api.donation.usecases.donationdemand;

import com.astrocode.api.donation.domain.DonationDemand;
import com.astrocode.api.donation.domain.interfacerepository.DonationDemandRepository;
import com.astrocode.api.donation.usecases.donationdemand.command.MarksAsFulfilledDemandCommand;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;


@Service
public class MarkAsFulfilledDemandUseCase {

    private final DonationDemandRepository donationDemandRepository;

    public MarkAsFulfilledDemandUseCase(DonationDemandRepository donationDemandRepository) {
        this.donationDemandRepository = donationDemandRepository;
    }

    public DonationDemand execute(MarksAsFulfilledDemandCommand command){
        if (command == null){
            throw DonationUseCasesException.commandNull("MARK_AS_FULFILLED_COMMAND");
        }

        final var donationDemand = donationDemandRepository.findById(command.demandId())
                .orElseThrow(() -> DonationUseCasesException.notFound("MARK_AS_FULFILLED", command.demandId()));

        final var fulfilledDemand = donationDemand.markFulfilled();

        return donationDemandRepository.save(fulfilledDemand);
    }

}
