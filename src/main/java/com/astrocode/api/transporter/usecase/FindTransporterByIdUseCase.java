package com.astrocode.api.transporter.usecase;


import com.astrocode.api.transporter.domain.Transporter;
import com.astrocode.api.transporter.domain.interfacerepository.TransporterRepository;
import com.astrocode.api.transporter.usecase.exception.TransporterUseCasesException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindTransporterByIdUseCase {

    private final TransporterRepository transporterRepository;

    public FindTransporterByIdUseCase(TransporterRepository transporterRepository) {
        this.transporterRepository = transporterRepository;
    }

    public Transporter execute(UUID id){
        return transporterRepository.findById(id)
                .orElseThrow(() -> TransporterUseCasesException.notFoundById(id));
    }
}
