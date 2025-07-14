package com.br.devs.shared_restaurant.dto.output;

import com.br.devs.shared_restaurant.model.enums.UserTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOutputDTO {
    private String id;
    private String name;
    private String mail;
    private UserTypeEnum userType;
    private AddressOutputDTO address;
}