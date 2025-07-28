package com.br.devs.shared_restaurant.core.gateways;

import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;
import com.br.devs.shared_restaurant.core.entities.Address;
import com.br.devs.shared_restaurant.core.entities.User;
import com.br.devs.shared_restaurant.core.interfaces.IDataSource;
import com.br.devs.shared_restaurant.core.interfaces.IUserGateway;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class UserGateway implements IUserGateway {
    private final IDataSource dataSource;

    private UserGateway(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static UserGateway create(IDataSource dataSource) {
        return new UserGateway(dataSource);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        UserOutputDTO userOutputDTO = dataSource.findByLogin(login);
        log.info("User output {}", userOutputDTO);
        Address address = Address.create(userOutputDTO.getAddress());
        return Optional.ofNullable(User.create(userOutputDTO, address));
    }

    @Override
    public Optional<User> findByMail(String mail) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }
}
