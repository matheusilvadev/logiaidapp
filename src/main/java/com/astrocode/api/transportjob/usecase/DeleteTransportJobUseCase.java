package com.astrocode.api.transportjob.usecase;

import com.astrocode.api.transportjob.domain.interfacerepository.TransportJobRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteTransportJobUseCase {

    private final TransportJobRepository transportJobRepository;

    public DeleteTransportJobUseCase(TransportJobRepository transportJobRepository) {
        this.transportJobRepository = transportJobRepository;
    }

    public void execute(UUID id){
        transportJobRepository.deleteById(id);
    }
}
