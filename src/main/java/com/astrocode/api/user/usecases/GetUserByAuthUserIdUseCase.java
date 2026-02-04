package com.astrocode.api.user.usecases;


import com.astrocode.api.user.domain.User;
import com.astrocode.api.user.domain.interfacerepository.UserRepository;
import com.astrocode.api.user.usecases.exception.UserUseCasesException;
import org.springframework.stereotype.Service;

@Service
public class GetUserByAuthUserIdUseCase {

    private final UserRepository userRepository;

    public GetUserByAuthUserIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String authUserId){
        if (authUserId.isEmpty()){
            throw UserUseCasesException.commandNull("AUTH_USER_ID");
        }

        return userRepository.findByAuthUserId(authUserId)
                .orElseThrow(() -> UserUseCasesException.notFoundByAuthId(authUserId));
    }

}
