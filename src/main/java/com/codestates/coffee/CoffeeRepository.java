package com.codestates.coffee;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
    Optional<Coffee> findByCoffeeCode(String coffeeCode);

    // @Query 사용법 테스트, findById와 동일
    @Query("SELECT * FROM COFFEE WHERE coffee_id = :coffeeId")
    Optional<Coffee> findByCoffee(long coffeeId);
}
