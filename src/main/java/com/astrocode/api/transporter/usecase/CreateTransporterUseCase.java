package com.astrocode.api.transporter.usecase;


import com.astrocode.api.transporter.domain.Transporter;
import com.astrocode.api.transporter.domain.interfacerepository.TransporterRepository;
import com.astrocode.api.transporter.usecase.command.CreateTransporterCommand;
import com.astrocode.api.transporter.usecase.exception.TransporterUseCasesException;
import org.springframework.stereotype.Service;

@Service
public class CreateTransporterUseCase {

    private final TransporterRepository transporterRepository;

    public CreateTransporterUseCase(TransporterRepository transporterRepository) {
        this.transporterRepository = transporterRepository;
    }

    public Transporter execute(CreateTransporterCommand command){
        if (command == null){
            throw TransporterUseCasesException.commandNull("CREATE_TRANSPORTER");
        }

        Transporter transporter = Transporter.create(
                command.userId(),
                command.displayName(),
                command.document(),
                command.phone(),
                command.address()
        );

        return transporterRepository.save(transporter);
    }
}
