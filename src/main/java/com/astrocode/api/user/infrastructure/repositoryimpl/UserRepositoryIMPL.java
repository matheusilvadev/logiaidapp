package com.astrocode.api.user.infrastructure.repositoryimpl;

import com.astrocode.api.shared.vo.Email;
import com.astrocode.api.user.domain.User;
import com.astrocode.api.user.domain.interfacerepository.UserRepository;
import com.astrocode.api.user.infrastructure.UserJPARepository;
import com.astrocode.api.user.mapper.UserMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Repository
public class UserRepositoryIMPL implements UserRepository {

    private final UserJPARepository jpaRepository;

    public UserRepositoryIMPL(UserJPARepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public User save(User user) {
        final var entity = UserMapper.toJPA(user);
        final var savedEntity = jpaRepository.save(entity);
        return UserMapper.toModel(savedEntity);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(UserMapper::toModel);
    }

    @Override
    public Optional<User> findByAuthUserId(String authUserId) {
        return jpaRepository.findByAuthUserId(authUserId)
                .map(UserMapper::toModel);
    }

    @Override
    public Optional<User> findByEmail(Email emailAddress) {
        return jpaRepository.findByEmail(emailAddress)
                .map(UserMapper::toModel);
    }

    @Override
    public boolean existsByAuthUserId(String authUserId) {
        return jpaRepository.existsByAuthUserId(authUserId);
    }

    @Override
    public boolean existsByEmail(Email emailAddress) {
        return jpaRepository.existsByEmail(emailAddress);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
