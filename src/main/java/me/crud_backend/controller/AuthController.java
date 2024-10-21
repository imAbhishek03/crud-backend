package me.crud_backend.controller;

import me.crud_backend.dto.JwtRequest;
import me.crud_backend.dto.JwtResponse;
import me.crud_backend.pojo.User;
import me.crud_backend.security.JwtHelper;
import me.crud_backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
//@CrossOrigin("http://localhost:3000/")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger((AuthController.class));


    // login controller


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest request) {
        try {
            // Log the login attempt
            logger.info("Attempting to log in with credentials: {}", request.toString());

            // Load user details
            UserDetails userDetails;
            try {
                userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            } catch (UsernameNotFoundException e) {
                logger.warn("Login failed. Username {} does not exist.", request.getUsername());
                return new ResponseEntity<>("Username does not exist!", HttpStatus.NOT_FOUND);
            }


            // Authenticate user
            doAuthenticate(request.getUsername(), request.getPassword());


            String token = helper.generateToken(userDetails.getUsername());

            // Create and return the response
            JwtResponse response = JwtResponse.builder()
                    .token(token)
                    .username(userDetails.getUsername()).build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            logger.error("Invalid credentials provided for username: {}", request.getUsername());
            return new ResponseEntity<>("Invalid Username or Password!", HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            // Log the exception and return a generic error message
            logger.error("An error occurred during login for username: {}", request.getUsername(), e);
            return new ResponseEntity<>("An unexpected error occurred during login. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try {
            manager.authenticate(authentication);
            logger.info("User {} authenticated successfully.", username);
        } catch (BadCredentialsException e) {
            logger.warn("Authentication failed for user {}. Invalid credentials.", username);
            throw new BadCredentialsException("Invalid Username or Password!");
        } catch (Exception e) {
            logger.error("An error occurred during authentication for user {}: {}", username, e.getMessage());
            throw new RuntimeException("Authentication failed due to an internal error. Please try again." + username + e.getMessage());
        }
    }


    // register user controller

    @PostMapping("/register-user")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        System.out.println("username ----- " + user.getUsername());
        System.out.println("register controller started-----");

        user.setPassword((passwordEncoder.encode(user.getPassword()))); // encoding user password

        User response = userService.createUser(user);

        return ResponseEntity.ok(response);
    }
}
