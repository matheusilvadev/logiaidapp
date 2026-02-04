package com.astrocode.api.transporter.usecase;


import com.astrocode.api.transporter.domain.Transporter;
import com.astrocode.api.transporter.domain.interfacerepository.TransporterRepository;
import com.astrocode.api.transporter.usecase.command.ChangeTransporterAddressCommand;
import com.astrocode.api.transporter.usecase.exception.TransporterUseCasesException;
import org.springframework.stereotype.Service;

@Service
public class ChangeTransporterAddressUseCase {

    private final TransporterRepository transporterRepository;

    public ChangeTransporterAddressUseCase(TransporterRepository transporterRepository) {
        this.transporterRepository = transporterRepository;
    }

    public Transporter execute(ChangeTransporterAddressCommand command){
        if (command == null){
            throw TransporterUseCasesException.commandNull("CHANGE_ADDRESS");
        }

        var transporter = transporterRepository.findById(command.id())
                .orElseThrow(() -> TransporterUseCasesException.notFoundById(command.id()));

        var updatedTransporter = transporter.changeAddress(command.newAddress());

        return transporterRepository.save(updatedTransporter);
    }

}
