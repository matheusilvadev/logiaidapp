package com.astrocode.api.beneficiary.mapper;

import com.astrocode.api.beneficiary.domain.Beneficiary;
import com.astrocode.api.beneficiary.infrastructure.BeneficiaryJPAEntity;

public class BeneficiaryMapper {

    private BeneficiaryMapper(){}

    public static BeneficiaryJPAEntity toJPA(Beneficiary beneficiary){
        if (beneficiary == null) return null;

        return new BeneficiaryJPAEntity(
                beneficiary.getId(),
                beneficiary.getUserId(),
                beneficiary.getDisplayName(),
                beneficiary.getDocument(),
                beneficiary.getPhone(),
                beneficiary.getAddress(),
                beneficiary.getCreatedAt(),
                beneficiary.getUpdatedAt(),
                beneficiary.getActivatedAt()
        );
    }

    public static Beneficiary toModel(BeneficiaryJPAEntity jpaEntity){
        if (jpaEntity == null) return null;

        return Beneficiary.restore(
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
