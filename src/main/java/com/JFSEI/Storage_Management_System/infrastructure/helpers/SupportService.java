package com.JFSEI.Storage_Management_System.infrastructure.helpers;

import com.JFSEI.Storage_Management_System.utils.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SupportService<Entity> {
    public  Entity findById(JpaRepository<Entity,Long> repositoryn, Long id, String message){
        return repositoryn.findById(id).orElseThrow(()->new IdNotFoundException(message));
    }
}
