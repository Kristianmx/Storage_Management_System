package com.JFSEI.Storage_Management_System.infrastructure.abstract_services;

import com.JFSEI.Storage_Management_System.api.dtos.request.LoginRequest;
import com.JFSEI.Storage_Management_System.api.dtos.response.LoginResponse;

public interface ILoginService {
    LoginResponse VerifyUser(LoginRequest request);
    String register(LoginRequest request);
}
