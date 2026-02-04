package com.astrocode.api.donor.usecase;


import com.astrocode.api.donor.domain.Donor;
import com.astrocode.api.donor.domain.interfacerepository.DonorRepository;
import com.astrocode.api.donor.usecase.command.ChangeDonorDisplayNameCommand;
import com.astrocode.api.donor.usecase.exception.DonorUseCaseException;
import org.springframework.stereotype.Service;

@Service
public class ChangeDonorDisplayNameUseCase {

    private final DonorRepository donorRepository;

    public ChangeDonorDisplayNameUseCase(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public Donor execute(ChangeDonorDisplayNameCommand command){
        if (command == null){
            throw DonorUseCaseException.commandNull("CHANGE_DISPLAY_NAME");
        }

        var donor = donorRepository.findById(command.id())
                .orElseThrow(() -> DonorUseCaseException.notFoundById(command.id()));

        var updatedDonor = donor.changeDisplayName(command.newDisplayName());

        return donorRepository.save(updatedDonor);
    }
}
