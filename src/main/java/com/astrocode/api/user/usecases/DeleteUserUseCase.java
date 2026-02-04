package com.astrocode.api.user.usecases;


import com.astrocode.api.user.domain.User;
import com.astrocode.api.user.domain.interfacerepository.UserRepository;
import com.astrocode.api.user.usecases.exception.UserUseCasesException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteUserUseCase {

    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> UserUseCasesException.notFoundById(id));

        userRepository.deleteById(id);
    }

}
