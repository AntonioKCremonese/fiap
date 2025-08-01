package com.br.devs.shared_restaurant.core.interfaces;

import com.br.devs.shared_restaurant.core.dto.input.AuthDTO;

public interface IAuthUseCase {
    boolean isValidPassword(AuthDTO authDTO);
}
