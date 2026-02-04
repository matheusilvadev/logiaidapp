package com.astrocode.api.transportjob.usecase;


import com.astrocode.api.transportjob.domain.TransportJob;
import com.astrocode.api.transportjob.domain.enums.TransportStatus;
import com.astrocode.api.transportjob.domain.interfacerepository.TransportJobRepository;
import com.astrocode.api.transportjob.usecase.command.ChangeTransportStatusCommand;
import com.astrocode.api.transportjob.usecase.exception.TransportJobUseCaseException;
import org.springframework.stereotype.Service;

@Service
public class MarkTransportAsInTransitUseCase {

    private final TransportJobRepository transportJobRepository;

    public MarkTransportAsInTransitUseCase(TransportJobRepository transportJobRepository) {
        this.transportJobRepository = transportJobRepository;
    }

    public TransportJob execute(ChangeTransportStatusCommand command){
        if (command == null){
            throw TransportJobUseCaseException.commandNull("MARK_AS_IN_TRANSIT");
        }

        var transportJob = transportJobRepository.getById(command.transportJobId())
                .orElseThrow(() -> TransportJobUseCaseException.notFoundById(command.transportJobId()));

        var updatedTransportJob = transportJob.markAsInTransit();

        return transportJobRepository.save(updatedTransportJob);
    }
}
