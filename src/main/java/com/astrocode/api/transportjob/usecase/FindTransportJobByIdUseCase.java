package com.astrocode.api.transportjob.usecase;

import com.astrocode.api.transportjob.domain.TransportJob;
import com.astrocode.api.transportjob.domain.interfacerepository.TransportJobRepository;
import com.astrocode.api.transportjob.usecase.exception.TransportJobUseCaseException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindTransportJobByIdUseCase {

    private final TransportJobRepository transportJobRepository;

    public FindTransportJobByIdUseCase(TransportJobRepository transportJobRepository) {
        this.transportJobRepository = transportJobRepository;
    }

    public TransportJob execute(UUID id){
        return transportJobRepository.getById(id)
                .orElseThrow(() -> TransportJobUseCaseException.notFoundById(id));
    }
}
