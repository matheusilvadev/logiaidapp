package com.astrocode.api.donation.mapper;

import com.astrocode.api.donation.domain.DonationDemand;
import com.astrocode.api.donation.infrastructure.jpaentities.DonationDemandJPAEntity;

public class DonationDemandMapper {

    private DonationDemandMapper(){}

    public static DonationDemandJPAEntity toJPA(final DonationDemand donationDemand){

        if (donationDemand == null) return null;

        return new DonationDemandJPAEntity(
                donationDemand.getId(),
                donationDemand.getBeneficiaryId(),
                donationDemand.getDonorId(),
                donationDemand.getDemandStatus(),
                donationDemand.getDeliveryLocation(),
                donationDemand.getNotes(),
                donationDemand.getItems(),
                donationDemand.getCreatedAt(),
                donationDemand.getUpdatedAt(),
                donationDemand.getAcceptedAt(),
                donationDemand.getFulfilledAt(),
                donationDemand.getCanceledAt()
        );

    }

    public static DonationDemand toModel(DonationDemandJPAEntity jpaEntity){
        return DonationDemand.restore(
                jpaEntity.getId(),
                jpaEntity.getBeneficiaryId(),
                jpaEntity.getDonorId(),
                jpaEntity.getDemandStatus(),
                jpaEntity.getDeliveryLocation(),
                jpaEntity.getNotes(),
                jpaEntity.getItems(),
                jpaEntity.getCreatedAt(),
                jpaEntity.getUpdatedAt(),
                jpaEntity.getAcceptedAt(),
                jpaEntity.getFulfilledAt(),
                jpaEntity.getCanceledAt()
        );
    }

}
