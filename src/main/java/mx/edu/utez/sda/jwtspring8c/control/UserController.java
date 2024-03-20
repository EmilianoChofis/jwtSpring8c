package mx.edu.utez.sda.jwtspring8c.control;

import mx.edu.utez.sda.jwtspring8c.model.AuthRequest;
import mx.edu.utez.sda.jwtspring8c.model.UserInfo;
import mx.edu.utez.sda.jwtspring8c.service.JwtService;
import mx.edu.utez.sda.jwtspring8c.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/index")
    public String index(){
        return "Servicio index";
    }

    @PostMapping("/registrame")
    public String registrame(@RequestBody UserInfo userInfo){
        return userInfoService.guardaUser(userInfo);
    }

    @GetMapping("/soloadmin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String soloAdmin(){
        return "Esto solo lo reciben admins";
    }

    @GetMapping("/paraUser")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public String paraUser(){
        return "user y admin";
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest){
        try {
            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                            )
                    );
            if (authentication.isAuthenticated()){
                return jwtService.generateToken(authRequest.getUsername());
            }else{
                throw new UsernameNotFoundException("Usuario invalido");
            }
        }catch (Exception e){
            throw new UsernameNotFoundException("Usuario invalido");
        }
    }
}
