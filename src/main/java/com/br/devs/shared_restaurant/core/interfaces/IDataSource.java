package com.br.devs.shared_restaurant.core.interfaces;

import com.br.devs.shared_restaurant.core.dto.output.UserOutputDTO;

public interface IDataSource {
    UserOutputDTO findByLogin(String login);
}
