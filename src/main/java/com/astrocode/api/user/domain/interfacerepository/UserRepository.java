package com.astrocode.api.user.domain.interfacerepository;

import com.astrocode.api.shared.vo.Email;
import com.astrocode.api.user.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByAuthUserId(String authUserId);
    Optional<User> findByEmail(Email emailAddress);
    boolean existsByAuthUserId(String authUserId);
    boolean existsByEmail(Email emailAddress);
    void deleteById(UUID id);
}
