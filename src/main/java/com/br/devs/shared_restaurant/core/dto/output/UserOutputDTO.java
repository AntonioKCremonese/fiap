package com.br.devs.shared_restaurant.core.dto.output;

import com.br.devs.shared_restaurant.core.entities.enums.UserTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserOutputDTO {
    private String id;
    private String name;
    private String mail;
    private String login;
    private UserTypeEnum userType;
    private AddressOutputDTO address;
}