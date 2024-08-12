package com.JFSEI.Storage_Management_System.api.Controller;

import com.JFSEI.Storage_Management_System.api.dtos.request.LoginRequest;
import com.JFSEI.Storage_Management_System.api.dtos.response.LoginResponse;
import com.JFSEI.Storage_Management_System.infrastructure.abstract_services.ILoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/login")
@Tag(name= "login")
@AllArgsConstructor
public class LoginController {
    @Autowired
    private final ILoginService iLoginService;
    @Operation(
            summary = "verify login ",
            description = "Please enter your details to verify your login."
    )
    @PatchMapping
    public ResponseEntity<LoginResponse> Verify(@Validated @RequestBody LoginRequest request){
        return ResponseEntity.ok(this.iLoginService.VerifyUser(request));
    }

    @PostMapping
    public ResponseEntity<String> create(@Validated @RequestBody LoginRequest request){
        return ResponseEntity.ok(this.iLoginService.register(request));
    }
}
