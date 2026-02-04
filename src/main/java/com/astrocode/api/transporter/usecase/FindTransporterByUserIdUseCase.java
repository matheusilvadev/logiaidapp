package com.astrocode.api.transporter.usecase;


import com.astrocode.api.transporter.domain.Transporter;
import com.astrocode.api.transporter.domain.interfacerepository.TransporterRepository;
import com.astrocode.api.transporter.usecase.exception.TransporterUseCasesException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindTransporterByUserIdUseCase {

    private final TransporterRepository transporterRepository;

    public FindTransporterByUserIdUseCase(TransporterRepository transporterRepository) {
        this.transporterRepository = transporterRepository;
    }

    public Transporter execute(UUID userId){
        return transporterRepository.findByUserId(userId)
                .orElseThrow(() -> TransporterUseCasesException.notFoundByUserId(userId));
    }

}
