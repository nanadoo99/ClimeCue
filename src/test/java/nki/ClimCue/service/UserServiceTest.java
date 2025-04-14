package nki.ClimCue.service;

import nki.ClimCue.model.member.RegisterUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void join() {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setUsername("kim");
        registerUserDto.setPassword("1234");

    }

    @Test
    void findById() {
    }

    @Test
    void findByUsername() {
    }
}