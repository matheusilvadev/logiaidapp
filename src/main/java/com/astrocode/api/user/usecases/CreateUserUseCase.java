package com.astrocode.api.user.usecases;


import com.astrocode.api.user.domain.User;
import com.astrocode.api.user.domain.interfacerepository.UserRepository;
import com.astrocode.api.user.usecases.command.CreateUserCommand;
import com.astrocode.api.user.usecases.exception.UserUseCasesException;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(CreateUserCommand command){
        if (userRepository.existsByAuthUserId(command.authUserId())){
            throw UserUseCasesException.userAlreadyExists(command.authUserId());
        }

        User user = User.create(
                command.authUserId(),
                command.email(),
                command.role()
        );

        return userRepository.save(user);
    }

}
