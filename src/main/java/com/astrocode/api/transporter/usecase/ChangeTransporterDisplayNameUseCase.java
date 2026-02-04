package com.astrocode.api.transporter.usecase;


import com.astrocode.api.transporter.domain.Transporter;
import com.astrocode.api.transporter.domain.interfacerepository.TransporterRepository;
import com.astrocode.api.transporter.usecase.command.ChangeTransporterDisplayNameCommand;
import com.astrocode.api.transporter.usecase.exception.TransporterUseCasesException;
import org.springframework.stereotype.Service;

@Service
public class ChangeTransporterDisplayNameUseCase {

    private final TransporterRepository transporterRepository;

    public ChangeTransporterDisplayNameUseCase(TransporterRepository transporterRepository) {
        this.transporterRepository = transporterRepository;
    }

    public Transporter execute(ChangeTransporterDisplayNameCommand command){
        if (command == null){
            throw TransporterUseCasesException.commandNull("CHANGE_DISPLAY_NAME");
        }

        var transporter = transporterRepository.findById(command.id())
                .orElseThrow(() -> TransporterUseCasesException.notFoundById(command.id()));

        var updatedTransporter = transporter.changeDisplayName(command.newDisplayName());

        return transporterRepository.save(updatedTransporter);
    }
}
