package com.astrocode.api.transporter.usecase;


import com.astrocode.api.transporter.domain.Transporter;
import com.astrocode.api.transporter.domain.interfacerepository.TransporterRepository;
import com.astrocode.api.transporter.usecase.command.ChangeTransporterPhoneCommand;
import com.astrocode.api.transporter.usecase.exception.TransporterUseCasesException;
import org.springframework.stereotype.Service;

@Service
public class ChangeTransporterPhoneUseCase {

    private final TransporterRepository transporterRepository;

    public ChangeTransporterPhoneUseCase(TransporterRepository transporterRepository) {
        this.transporterRepository = transporterRepository;
    }

    public Transporter execute(ChangeTransporterPhoneCommand command){
        if (command == null){
            throw TransporterUseCasesException.commandNull("CHANGE_PHONE");
        }

        var transporter = transporterRepository.findById(command.id())
                .orElseThrow(() -> TransporterUseCasesException.notFoundById(command.id()));

        var updatedTransporter = transporter.changePhone(command.newPhone());

        return transporterRepository.save(updatedTransporter);
    }
}
