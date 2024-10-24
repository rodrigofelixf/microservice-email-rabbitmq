package io.ms.user.services;


import io.ms.user.dtos.UserRecordDto;
import io.ms.user.models.UserModel;
import io.ms.user.producers.UserProducer;
import io.ms.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }


    @Transactional
    public UserModel save(UserModel userModel) {
        UserModel savedUser = userRepository.save(userModel);
        userProducer.publishMessageEmail(savedUser);
        return userModel;
    }
}
