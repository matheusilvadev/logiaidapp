package com.astrocode.api.donor.usecase;


import com.astrocode.api.donor.domain.Donor;
import com.astrocode.api.donor.domain.interfacerepository.DonorRepository;
import com.astrocode.api.donor.usecase.command.ChangeDonorAddressCommand;
import com.astrocode.api.donor.usecase.exception.DonorUseCaseException;
import org.springframework.stereotype.Service;

@Service
public class ChangeDonorAddressUseCase {

    private final DonorRepository donorRepository;

    public ChangeDonorAddressUseCase(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public Donor execute(ChangeDonorAddressCommand command){
        if (command == null){
            throw DonorUseCaseException.commandNull("CHANGE_ADDRESS");
        }

        var donor = donorRepository.findById(command.id())
                .orElseThrow(() -> DonorUseCaseException.notFoundById(command.id()));

        var updatedDonor = donor.changeAddress(command.newAddress());

        return donorRepository.save(updatedDonor);
    }
}
