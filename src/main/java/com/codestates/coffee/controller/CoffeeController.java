package com.codestates.coffee.controller;

import com.codestates.coffee.Coffee;
import com.codestates.coffee.CoffeeDTO;
import com.codestates.coffee.CoffeeMapper;
import com.codestates.coffee.CoffeeService;
import com.codestates.validator.PatchValidation;
import com.codestates.validator.PostValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v10/coffees")
@Validated
public class CoffeeController {
    private static final String COFFEE_DEFAULT_URL = "/v10/coffees";
    private final CoffeeService coffeeService;
    private final CoffeeMapper mapper;

    public CoffeeController(CoffeeService coffeeService, CoffeeMapper mapper) {
        this.coffeeService = coffeeService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<CoffeeDTO.Response> postCoffee(@Validated(PostValidation.class) @RequestBody CoffeeDTO.Post coffeePostDTO) {
        Coffee coffee = coffeeService.createCoffee(mapper.coffeePostDTOToCoffee(coffeePostDTO));
        URI location = UriComponentsBuilder.newInstance()
                .path(COFFEE_DEFAULT_URL + "/{coffee-id}")
                .buildAndExpand(coffee.getCoffeeId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{coffee-id}")
    public ResponseEntity<CoffeeDTO.Response> patchCoffee(@PathVariable("coffee-id") @Positive long coffeeId,
                                                          @Validated(PatchValidation.class) @RequestBody CoffeeDTO.Patch coffeePatchDTO) {
        coffeePatchDTO.setCoffeeId(coffeeId);
        Coffee response = coffeeService.updateCoffee(mapper.coffeePatchDTOToCoffee(coffeePatchDTO));
        return new ResponseEntity<>(mapper.coffeeToCoffeeResponseDTO(response), HttpStatus.OK);
    }


    @GetMapping("/{coffee-id}")
    public ResponseEntity<CoffeeDTO.Response> getCoffee(@PathVariable("coffee-id") @Positive long coffeeId) {
        Coffee response = coffeeService.findCoffee(coffeeId);
        return new ResponseEntity<>(mapper.coffeeToCoffeeResponseDTO(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CoffeeDTO.Response>> getCoffees() {
        List<Coffee> coffees = coffeeService.findCoffees();
        List<CoffeeDTO.Response> response = coffees.stream()
                .map(mapper::coffeeToCoffeeResponseDTO).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{coffee-id}")
    public ResponseEntity<CoffeeDTO.Response> deleteCoffee(@PathVariable("coffee-id") @Positive long coffeeId) {
        coffeeService.deleteCoffee(coffeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
