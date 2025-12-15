package chaewonan.springbootdeveloper.service;

import chaewonan.springbootdeveloper.domain.User;
import chaewonan.springbootdeveloper.dto.AddUserRequest;
import chaewonan.springbootdeveloper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // 일반 회원가입
    public Long save(AddUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .build()).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    // 기존 일반 회원 조회
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    // OAuth 로그인용 Optional 조회
    public Optional<User> findByEmailOptional(String email) {
        return userRepository.findByEmail(email);
    }

    // OAuth 신규 로그인 시 사용자 생성
    public User createOAuthUser(String email) {
        User user = User.builder()
                .email(email)
                .password("")   // OAuth는 비밀번호 없음
                .build();
        return userRepository.save(user);
    }
}
