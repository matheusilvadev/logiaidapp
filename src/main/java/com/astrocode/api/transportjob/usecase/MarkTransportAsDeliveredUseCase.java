package com.astrocode.api.transportjob.usecase;


import com.astrocode.api.transportjob.domain.TransportJob;
import com.astrocode.api.transportjob.domain.enums.TransportStatus;
import com.astrocode.api.transportjob.domain.interfacerepository.TransportJobRepository;
import com.astrocode.api.transportjob.usecase.command.ChangeTransportStatusCommand;
import com.astrocode.api.transportjob.usecase.exception.TransportJobUseCaseException;
import org.springframework.stereotype.Service;

@Service
public class MarkTransportAsDeliveredUseCase {

    private final TransportJobRepository transportJobRepository;

    public MarkTransportAsDeliveredUseCase(TransportJobRepository transportJobRepository) {
        this.transportJobRepository = transportJobRepository;
    }

    public TransportJob execute(ChangeTransportStatusCommand command){
        if (command == null){
            throw TransportJobUseCaseException.commandNull("MARK_AS_DELIVERED");
        }

        var transportJob = transportJobRepository.getById(command.transportJobId())
                .orElseThrow(() -> TransportJobUseCaseException.notFoundById(command.transportJobId()));

        var updatedTransportJob = transportJob.markAsDelivered();

        return transportJobRepository.save(updatedTransportJob);
    }
}
