package com.thesun4sky.todoparty1.user;


//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {


    private final PasswordEncoder passwordEncoder;


    private final UserRepository userRepository;


    public void signup(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = passwordEncoder.encode(userRequestDto.getPassword());

        if(userRepository.findByUsername(username).isPresent()){
           throw new IllegalArgumentException("이미 존재하는 유저 입니다.") ;
        }
        User user = new User(username,password);
        userRepository.save(user);
    }
    public UserDetails getUser(String username){
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Not Found"+username));
        return new UserDetailsImpl(user);
    }
}
