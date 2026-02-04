package com.astrocode.api.transporter.usecase;


import com.astrocode.api.transporter.domain.interfacerepository.TransporterRepository;
import com.astrocode.api.transporter.usecase.exception.TransporterUseCasesException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteTransporterUseCase {

    private final TransporterRepository transporterRepository;

    public DeleteTransporterUseCase(TransporterRepository transporterRepository) {
        this.transporterRepository = transporterRepository;
    }

    public void execute(UUID id){
        if (transporterRepository.findById(id).isEmpty()){
            throw TransporterUseCasesException.notFoundById(id);
        }

        transporterRepository.deleteById(id);
    }

}
