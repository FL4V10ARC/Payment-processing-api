package com.flavio.paymentprocessing.controller;

import com.flavio.paymentprocessing.dto.AuthRequestDTO;
import com.flavio.paymentprocessing.dto.AuthResponseDTO;
import com.flavio.paymentprocessing.dto.RegisterRequestDTO;
import com.flavio.paymentprocessing.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponseDTO register(@RequestBody @Valid RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody @Valid AuthRequestDTO request) {
        return authService.login(request);
    }
}