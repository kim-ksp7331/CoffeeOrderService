package com.codestates.coffee;

import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import com.codestates.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;
    public Coffee createCoffee(Coffee coffee) {
        String coffeeCode = coffee.getCoffeeCode().toUpperCase();
        // 이미 등록된 커피코드이면 예외 발생
        verifyExistCoffee(coffeeCode);
        coffee.setCoffeeCode(coffeeCode);

        return coffeeRepository.save(coffee);
    }

    public Coffee updateCoffee(Coffee coffee) {
        Coffee findCoffee = findVerifiedCoffee(coffee.getCoffeeId());
        Optional.ofNullable(coffee.getKorName()).ifPresent(korName -> findCoffee.setKorName(korName));
        Optional.ofNullable(coffee.getEngName()).ifPresent(engName -> findCoffee.setEngName(engName));
        Optional.ofNullable(coffee.getPrice()).ifPresent(price -> findCoffee.setPrice(price));
        return coffeeRepository.save(findCoffee);
    }

    public Coffee findCoffee(long coffeeId) {
        return findVerifiedCoffee(coffeeId);
    }

    public List<Coffee> findCoffees() {
        return (List<Coffee>) coffeeRepository.findAll();
    }

    public List<Coffee> findOrderedCoffees(Order order) {
        return order.getOrderCoffees().stream()
                .map(coffeeRef -> findVerifiedCoffee(coffeeRef.getCoffeeId())).collect(Collectors.toList());
    }

    public void deleteCoffee(long coffeeId) {
        Coffee findCoffee = findVerifiedCoffee(coffeeId);
        coffeeRepository.delete(findCoffee);
    }

    public Coffee findVerifiedCoffee(long coffeeId) {
        Optional<Coffee> optionalCoffee = coffeeRepository.findById(coffeeId);
        Coffee findCoffee = optionalCoffee.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COFFEE_NOT_FOUND));
        return findCoffee;
    }

    private void verifyExistCoffee(String coffeeCode) {
        Optional<Coffee> coffee = coffeeRepository.findByCoffeeCode(coffeeCode);
        if (coffee.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.COFFEE_CODE_EXISTS);
        }
    }
}
