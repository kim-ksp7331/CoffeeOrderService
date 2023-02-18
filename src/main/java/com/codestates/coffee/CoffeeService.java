package com.codestates.coffee;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeService {
    public Coffee createCoffee(Coffee coffee) {
        System.out.println("CoffeeService.createCoffee");
        return coffee;
    }

    public Coffee updateCoffee(Coffee coffee) {
        System.out.println("CoffeeService.updateCoffee");
        return coffee;
    }

    public Coffee findCoffee(long coffeeId) {
        System.out.println("CoffeeService.findCoffee");
        return new Coffee(coffeeId, "아메리카노", "Americano", 2500);
    }

    public List<Coffee> findCoffees() {
        System.out.println("CoffeeService.findCoffees");
        return List.of(
                new Coffee(1L, "아메리카노", "Americano", 2500),
                new Coffee(2L, "카라멜 라뗴", "Caramel Latte", 5000)

        );
    }
    public void deleteCoffee(long coffeeId) {
        System.out.println("CoffeeService.deleteCoffee");
    }
}
