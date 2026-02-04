package com.astrocode.api.user.usecases;


import com.astrocode.api.user.domain.User;
import com.astrocode.api.user.domain.interfacerepository.UserRepository;
import com.astrocode.api.user.usecases.command.ActivateUserCommand;
import com.astrocode.api.user.usecases.exception.UserUseCasesException;
import org.springframework.stereotype.Service;

@Service
public class ActivateUserUseCase {

    private final UserRepository userRepository;

    public ActivateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(ActivateUserCommand command){
        if (command == null){
            throw UserUseCasesException.commandNull("ACTIVATE_USER_USE_CASE");
        }

        User user = userRepository.findByAuthUserId(command.authUserId())
                .orElseThrow(() -> UserUseCasesException.notFoundByAuthId(command.authUserId()));

        user.activate();

        return userRepository.save(user);
    }

}
