package com.backend.blog.controller;


import com.backend.blog.exception.ApiException;
import com.backend.blog.model.dto.JwtAuthRequest;
import com.backend.blog.model.dto.UserDto;
import com.backend.blog.security.JwtAuthResponse;
import com.backend.blog.security.JwtTokenHelper;
import com.backend.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private  final JwtTokenHelper jwtTokenHelper;

    private  final UserDetailsService userDetailsService;

    private  final AuthenticationManager authenticationManager;

    private  final UserService userService;



    @Autowired
    public AuthController(JwtTokenHelper jwtTokenHelper, UserDetailsService userDetailsService, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse>  createToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {

        authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());

      String token = jwtTokenHelper.generateToken(userDetails);

      JwtAuthResponse response = new JwtAuthResponse();
      response.setToken(token);

      return  new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);

    }

    private  void authenticate(String username,String password) throws Exception {
        UsernamePasswordAuthenticationToken  usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }catch (BadCredentialsException exception){
           System.out.println("Invalid Details  !!");
           throw new ApiException("Invalid username or password !!");
        }
        }

        @PostMapping("/register")
        public  ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){


        UserDto registeredUser=userService.registerNewUser(userDto);

        return  new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
        }

}
