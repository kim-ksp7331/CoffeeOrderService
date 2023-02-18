package com.codestates.coffee;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring") // unmappedTargetPolicy = ReportingPolicy.IGNORE 속성으로 오류 메시지 제거 가능
public interface CoffeeMapper {
    // @Mapping 기능 확인
    Coffee coffeePostDTOToCoffee(CoffeeDTO.Post coffeePostDTO);
    Coffee coffeePatchDTOToCoffee(CoffeeDTO.Patch coffeePatchDTO);
    CoffeeDTO.Response coffeeToCoffeeResponseDTO(Coffee coffee);
}
