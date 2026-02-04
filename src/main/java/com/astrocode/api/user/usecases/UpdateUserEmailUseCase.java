package com.astrocode.api.user.usecases;


import com.astrocode.api.user.domain.User;
import com.astrocode.api.user.domain.interfacerepository.UserRepository;
import com.astrocode.api.user.usecases.command.UpdateUserEmailCommand;
import com.astrocode.api.user.usecases.exception.UserUseCasesException;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserEmailUseCase {

    private final UserRepository userRepository;

    public UpdateUserEmailUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UpdateUserEmailCommand command){
        if (command == null){
            throw UserUseCasesException.commandNull("UPDATE_EMAIL_USE_CASE");
        }

        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> UserUseCasesException.notFoundById(command.userId()));

        user.changeEmail(command.newEmail());

        return userRepository.save(user);
    }
}
