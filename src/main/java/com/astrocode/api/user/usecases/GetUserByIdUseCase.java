package com.astrocode.api.user.usecases;


import com.astrocode.api.user.domain.User;
import com.astrocode.api.user.domain.interfacerepository.UserRepository;
import com.astrocode.api.user.usecases.exception.UserUseCasesException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserByIdUseCase {

    private final UserRepository userRepository;

    public GetUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID id){
        return userRepository.findById(id)
                .orElseThrow(() -> UserUseCasesException.notFoundById(id));
    }
}
