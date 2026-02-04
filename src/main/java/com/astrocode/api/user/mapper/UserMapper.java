package com.astrocode.api.user.mapper;

import com.astrocode.api.user.domain.User;
import com.astrocode.api.user.infrastructure.UserJPAEntity;

public class UserMapper {

    private UserMapper(){}

    public static UserJPAEntity toJPA(final User user){
        if (user == null) return null;

        return new UserJPAEntity(
                user.getId(),
                user.getAuthUserId(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public static User toModel(final UserJPAEntity jpaEntity){
        if (jpaEntity == null) return null;

        return User.restore(
                jpaEntity.getId(),
                jpaEntity.getAuthUserId(),
                jpaEntity.getEmail(),
                jpaEntity.getRole(),
                jpaEntity.isActive(),
                jpaEntity.getCreatedAt(),
                jpaEntity.getUpdatedAt()
        );
    }

}
