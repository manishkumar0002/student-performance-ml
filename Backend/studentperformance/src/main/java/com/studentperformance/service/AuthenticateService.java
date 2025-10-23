package com.studentperformance.service;

import com.studentperformance.dto.LoginRequestDTO;
import com.studentperformance.dto.RegisterRequestDTO;
import com.studentperformance.model.User;

public interface AuthenticateService {

    User register(RegisterRequestDTO registerRequest);

    String login(LoginRequestDTO loginRequest);
}
