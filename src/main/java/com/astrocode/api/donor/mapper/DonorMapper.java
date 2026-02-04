package com.astrocode.api.donor.mapper;

import com.astrocode.api.donor.domain.Donor;
import com.astrocode.api.donor.infrastructure.DonorJPAEntity;

public class DonorMapper {

    private DonorMapper() {
    }

    public static DonorJPAEntity toJPA(Donor donor){
        if (donor == null) return null;

        return new DonorJPAEntity(
                donor.getId(),
                donor.getUserId(),
                donor.getDisplayName(),
                donor.getDocument(),
                donor.getPhone(),
                donor.getAddress(),
                donor.getCreatedAt(),
                donor.getUpdatedAt(),
                donor.getActivatedAt()
        );
    }

    public static Donor toModel(DonorJPAEntity jpaEntity){
        if (jpaEntity == null) return null;

        return Donor.restore(
                jpaEntity.getId(),
                jpaEntity.getUserId(),
                jpaEntity.getDisplayName(),
                jpaEntity.getDocument(),
                jpaEntity.getPhone(),
                jpaEntity.getAddress(),
                jpaEntity.getCreatedAt(),
                jpaEntity.getUpdatedAt(),
                jpaEntity.getActivatedAt()
        );
    }

}
