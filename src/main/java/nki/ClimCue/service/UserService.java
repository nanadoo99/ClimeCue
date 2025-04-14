package nki.ClimCue.service;

import nki.ClimCue.model.member.CheckUserDto;
import nki.ClimCue.model.member.Role;
import nki.ClimCue.model.member.User;
import nki.ClimCue.model.member.RegisterUserDto;
import nki.ClimCue.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public User join(RegisterUserDto registerUserDto) {

        User user = User.builder()
                .username(registerUserDto.getUsername())
                .password(bCryptPasswordEncoder.encode(registerUserDto.getPassword()))
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }

    public void checkUserDuplication(CheckUserDto checkUserDto, BindingResult result) {
        if(checkUsernameDuplication(checkUserDto)) {
            result.rejectValue("username", "duplicate", "중복된 아이디입니다.");
        }
    }

    @Transactional
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public Optional<User> findByUsername(String email) {
        return userRepository.findByUsername(email);
    }

    private boolean checkUsernameDuplication(CheckUserDto checkUserDto) {
        return userRepository.existsByUsername(checkUserDto.getUsername());
    }

    public void delete(Long memberId) {
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        userRepository.delete(user);
    }
}
