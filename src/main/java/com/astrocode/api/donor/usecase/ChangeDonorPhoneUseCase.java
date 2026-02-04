package com.astrocode.api.donor.usecase;


import com.astrocode.api.donor.domain.Donor;
import com.astrocode.api.donor.domain.interfacerepository.DonorRepository;
import com.astrocode.api.donor.usecase.command.ChangeDonorPhoneCommand;
import com.astrocode.api.donor.usecase.exception.DonorUseCaseException;
import org.springframework.stereotype.Service;

@Service
public class ChangeDonorPhoneUseCase {

    private final DonorRepository donorRepository;

    public ChangeDonorPhoneUseCase(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public Donor execute(ChangeDonorPhoneCommand command){
        if (command == null){
            throw DonorUseCaseException.commandNull("CHANGE_PHONE");
        }

        var donor = donorRepository.findById(command.id())
                .orElseThrow(() -> DonorUseCaseException.notFoundById(command.id()));

        var updatedDonor = donor.changePhone(command.newPhone());

        return donorRepository.save(updatedDonor);
    }
}
