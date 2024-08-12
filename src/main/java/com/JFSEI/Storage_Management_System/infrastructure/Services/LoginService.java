package com.JFSEI.Storage_Management_System.infrastructure.Services;

import com.JFSEI.Storage_Management_System.api.dtos.request.LoginRequest;
import com.JFSEI.Storage_Management_System.api.dtos.response.LoginResponse;
import com.JFSEI.Storage_Management_System.domain.entities.Login;
import com.JFSEI.Storage_Management_System.domain.repositories.LoginRepository;
import com.JFSEI.Storage_Management_System.infrastructure.abstract_services.ILoginService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LoginService implements ILoginService {
    @Autowired
    private final LoginRepository loginRepository;
    @Override
    public LoginResponse VerifyUser(LoginRequest request) {
        List<Login> loginList = this.loginRepository.findAll();
        LoginResponse response = new LoginResponse();
        loginList.forEach(l->{
            if (l.getEmail().equals(request.getEmail())){
                response.setApproved(l.getPassword().equals(request.getPassword()));
            }else {
                response.setApproved(false);
            }
        });
        return response;
    }

    @Override
    public String register(LoginRequest request) {
        Login login = new Login() ;
        login.setEmail(request.getEmail());
        login.setPassword(request.getPassword());
        login.setStatus(true);
        String response= "";
        login = this.loginRepository.save(login);
        if (login.getId() !=null){
            response = "The user has been created";
        }else {
            response = "The user has not been created";
        }
        return response;
    }
}
