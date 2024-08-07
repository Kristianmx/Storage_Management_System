package com.JFSEI.Storage_Management_System.infrastructure.helpers.mappers;

import java.util.List;

public interface GenericMapper<RequestDTO, ResponseDTO, Entity> {
    Entity toEntity(RequestDTO userRequest);

    ResponseDTO toResponse(Entity userEntity);

    List<ResponseDTO> toListResponse(List<Entity> entityList);

}
