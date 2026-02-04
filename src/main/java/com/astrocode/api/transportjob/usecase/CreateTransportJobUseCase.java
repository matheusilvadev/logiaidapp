package com.astrocode.api.transportjob.usecase;


import com.astrocode.api.transportjob.domain.TransportJob;
import com.astrocode.api.transportjob.domain.interfacerepository.TransportJobRepository;
import com.astrocode.api.transportjob.usecase.command.CreateTransportJobCommand;
import com.astrocode.api.transportjob.usecase.exception.TransportJobUseCaseException;
import org.springframework.stereotype.Service;

@Service
public class CreateTransportJobUseCase {

    private final TransportJobRepository transportJobRepository;

    public CreateTransportJobUseCase(TransportJobRepository transportJobRepository) {
        this.transportJobRepository = transportJobRepository;
    }

    public TransportJob execute(CreateTransportJobCommand command){
        if (command == null){
            throw TransportJobUseCaseException.commandNull("CREATE_TRANSPORT_JOB");
        }

        TransportJob transportJob = TransportJob.assign(
                command.donationId(),
                command.transporterId()
        );

        return transportJobRepository.save(transportJob);
    }
}
