package com.JFSEI.Storage_Management_System.infrastructure.abstract_services;

import java.util.List;

public interface GenericService<Request, Response , ID> {
    Response create(Request request);
    List<Response> getAll();
    Response read(ID id);
    Response update (ID id,Request request);




}
