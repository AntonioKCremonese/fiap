package com.br.devs.shared_restaurant.infrastructure.repository;

import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;
import com.br.devs.shared_restaurant.core.interfaces.IDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl implements IDataSource {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public UserRepositoryImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserOutputDTO findByLogin(String login) {
        UserOutputDTO userOutputDTO = null;
        var userOptional = this.userRepository.findByLogin(login);
        if (userOptional.isEmpty()) {
            return userOutputDTO;
        } else {
            userOutputDTO = modelMapper.map(userOptional.get(), UserOutputDTO.class);
        }
//        return userOutputDTO;
//        var o = new UserOutputDTO();
//        o.setName("tste");
//        o.setMail("teste@gmail.com");
//        o.setLogin(login);
//        return o;
        return null;
    }
}
