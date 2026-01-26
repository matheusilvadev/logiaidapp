package com.astrocode.api.donation.usecases.donationdemand;

import com.astrocode.api.donation.domain.DonationDemand;
import com.astrocode.api.donation.domain.interfacerepository.DonationDemandRepository;
import com.astrocode.api.donation.usecases.donationdemand.command.MarkAsCanceledDemandCommand;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;


@Service
public class MarkAsCanceledDemandUseCase {

    private final DonationDemandRepository donationDemandRepository;

    public MarkAsCanceledDemandUseCase(DonationDemandRepository donationDemandRepository) {
        this.donationDemandRepository = donationDemandRepository;
    }

    public DonationDemand execute(MarkAsCanceledDemandCommand command){
        if (command == null){
            throw DonationUseCasesException.commandNull("MARK_AS_CANCELED_COMMAND");
        }

        final var donationDemand = donationDemandRepository.findById(command.demandId())
                .orElseThrow(() -> DonationUseCasesException.notFound("MARK_AS_CANCELED", command.demandId()));

        final var canceledDemand = donationDemand.cancel();

        return donationDemandRepository.save(canceledDemand);

    }

}
