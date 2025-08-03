package com.br.devs.shared_restaurant.core.gateways;

import com.br.devs.shared_restaurant.core.entities.MenuItem;
import org.springframework.stereotype.Repository;

@Repository
public interface IMenuItemGateway {
    MenuItem save(MenuItem menuItem);
    MenuItem findMenuItemById(String id);
    void deleteMenuItemById(String id);
}
