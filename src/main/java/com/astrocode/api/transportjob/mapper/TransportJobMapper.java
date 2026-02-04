package com.astrocode.api.transportjob.mapper;

import com.astrocode.api.transportjob.domain.TransportJob;
import com.astrocode.api.transportjob.infrastructure.TransportJobJpaEntity;

public class TransportJobMapper {

    private TransportJobMapper() {
    }

    public static TransportJobJpaEntity toJpa(TransportJob transportJob){
        if(transportJob == null) return null;

        return new TransportJobJpaEntity(
                transportJob.getId(),
                transportJob.getDonationId(),
                transportJob.getTransporterId(),
                transportJob.getStatus(),
                transportJob.getAssignedAt(),
                transportJob.getPickedUpAt(),
                transportJob.getDeliveredAt(),
                transportJob.getUpdatedAt()
        );
    }

    public static TransportJob toModel(TransportJobJpaEntity jpaEntity){
        if (jpaEntity == null) return null;

        return TransportJob.restore(
                jpaEntity.getId(),
                jpaEntity.getDonationId(),
                jpaEntity.getTransporterId(),
                jpaEntity.getStatus(),
                jpaEntity.getAssignedAt(),
                jpaEntity.getPickedUpAt(),
                jpaEntity.getDeliveredAt(),
                jpaEntity.getUpdatedAt()
        );
    }
}
