package com.astrocode.api.donation.usecases.donationdemand;

import com.astrocode.api.donation.domain.DonationDemand;
import com.astrocode.api.donation.domain.interfacerepository.DonationDemandRepository;
import com.astrocode.api.donation.usecases.donationdemand.command.CreateDonationDemandCommand;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.springframework.stereotype.Service;


@Service
public class CreateDonationDemandUseCase {

    private final DonationDemandRepository donationDemandRepository;

    public CreateDonationDemandUseCase(DonationDemandRepository donationDemandRepository) {
        this.donationDemandRepository = donationDemandRepository;
    }

    public DonationDemand execute(CreateDonationDemandCommand command){

        if (command == null){
            throw DonationUseCasesException.commandNull("CREATE_DONATION_DEMAND_COMMAND");
        }

        final DonationDemand demand = DonationDemand.create(
                command.beneficiaryId(),
                command.deliveryLocation(),
                command.items(),
                command.notes()
        );

        return donationDemandRepository.save(demand);
    }

}
