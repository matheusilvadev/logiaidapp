package com.astrocode.api.donor.usecase;


import com.astrocode.api.donor.domain.Donor;
import com.astrocode.api.donor.domain.interfacerepository.DonorRepository;
import com.astrocode.api.donor.usecase.command.CreateDonorCommand;
import com.astrocode.api.donor.usecase.exception.DonorUseCaseException;
import org.springframework.stereotype.Service;

@Service
public class CreateDonorUseCase {

    private final DonorRepository donorRepository;

    public CreateDonorUseCase(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public Donor execute(CreateDonorCommand command){
        if (command == null){
            throw DonorUseCaseException.commandNull("CREATE_DONOR");
        }

        Donor donor = Donor.create(
                command.userId(),
                command.displayName(),
                command.document(),
                command.phone(),
                command.address()
        );

        return donorRepository.save(donor);
    }

}
