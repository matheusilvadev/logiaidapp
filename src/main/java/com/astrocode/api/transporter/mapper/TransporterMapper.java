package com.astrocode.api.transporter.mapper;

import com.astrocode.api.donor.domain.Donor;
import com.astrocode.api.donor.infrastructure.DonorJPAEntity;
import com.astrocode.api.transporter.domain.Transporter;
import com.astrocode.api.transporter.infrastructure.TransporterJPAEntity;

public class TransporterMapper {

    private TransporterMapper() {
    }

    public static TransporterJPAEntity toJPA(Transporter transporter){
        if (transporter == null) return null;

        return new TransporterJPAEntity(
                transporter.getId(),
                transporter.getUserId(),
                transporter.getDisplayName(),
                transporter.getDocument(),
                transporter.getPhone(),
                transporter.getAddress(),
                transporter.getCreatedAt(),
                transporter.getUpdatedAt(),
                transporter.getActivatedAt()
        );
    }

    public static Transporter toModel(TransporterJPAEntity jpaEntity){
        if (jpaEntity == null) return null;

        return Transporter.restore(
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
