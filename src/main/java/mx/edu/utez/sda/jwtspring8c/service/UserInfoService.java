package mx.edu.utez.sda.jwtspring8c.service;

import mx.edu.utez.sda.jwtspring8c.model.UserInfo;
import mx.edu.utez.sda.jwtspring8c.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByUsername(username);

        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Usuario no encontrado")
        );
    }
}
