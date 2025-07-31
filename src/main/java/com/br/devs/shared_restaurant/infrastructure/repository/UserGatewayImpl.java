package com.br.devs.shared_restaurant.infrastructure.repository;

import com.br.devs.shared_restaurant.application.mapper.GenericMapper;
import com.br.devs.shared_restaurant.core.entities.User;
import com.br.devs.shared_restaurant.core.interfaces.IUserGateway;
import com.br.devs.shared_restaurant.infrastructure.model.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserGatewayImpl implements IUserGateway {

    private final UserRepository userRepository;
    private final GenericMapper<UserEntity, User, User> mapper;

    public UserGatewayImpl(UserRepository userRepository, GenericMapper<UserEntity, User, User> mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return this.userRepository.findByLogin(login).map(userEntity -> mapper.fromEntity(userEntity, User.class));
    }

    @Override
    public Optional<User> findUserByMail(String mail) {
        return this.userRepository.findByMail(mail).map(userEntity -> mapper.fromEntity(userEntity, User.class));
    }

    @Override
    public Optional<User> findUserById(String id) {
        return this.userRepository.findById(id).map(userEntity -> mapper.fromEntity(userEntity, User.class));
    }

    @Override
    public User save(User user) {
        UserEntity userCreated = this.userRepository.save(mapper.toEntity(user, UserEntity.class));
        return mapper.fromEntity(userCreated, User.class);
    }

    @Override
    public void deleteUserById(String userId) {
        this.userRepository.deleteById(userId);
    }
}
