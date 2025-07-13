package com.br.devs.shared_restaurant.repository;

import com.br.devs.shared_restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurant, String> {

}