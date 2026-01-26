package com.astrocode.api.donation.mapper;

import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.infrastructure.jpaentities.DonationJPAEntity;

public class DonationMapper {

    private DonationMapper(){}

    public static DonationJPAEntity toJPA(final Donation donation){

        if (donation == null) return null;

        return new DonationJPAEntity(
                donation.getId(),
                donation.getDemandId(),
                donation.getDonorId(),
                donation.getTransporterId(),
                donation.getPickupLocation(),
                donation.getStatus(),
                donation.getCreatedAt(),
                donation.getUpdatedAt(),
                donation.getDeliveredAt(),
                donation.getCanceledAt()
        );
    }

    public static Donation toModel(DonationJPAEntity jpaEntity){
        if (jpaEntity == null) return null;

        return Donation.restore(
                jpaEntity.getId(),
                jpaEntity.getDemandId(),
                jpaEntity.getDonorId(),
                jpaEntity.getTransporterId(),
                jpaEntity.getPickupLocation(),
                jpaEntity.getStatus(),
                jpaEntity.getCreatedAt(),
                jpaEntity.getUpdatedAt(),
                jpaEntity.getDeliveredAt(),
                jpaEntity.getCanceledAt()
        );
    }
}
