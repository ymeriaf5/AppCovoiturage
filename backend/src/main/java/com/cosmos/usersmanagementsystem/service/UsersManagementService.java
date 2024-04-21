package com.cosmos.usersmanagementsystem.service;

import com.cosmos.usersmanagementsystem.dto.ReqRes;
import com.cosmos.usersmanagementsystem.repository.UsersRepo;
import com.cosmos.usersmanagementsystem.entity.OurUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UsersManagementService {

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public ReqRes register(ReqRes registrationRequest){
        // Create a response object to hold the registration response
        ReqRes resp = new ReqRes();

        try {
            // Create a new user object from the registration request
            OurUsers ourUser = new OurUsers();
            // Set user details from the registration request
            ourUser.setEmail(registrationRequest.getEmail());
            ourUser.setCity(registrationRequest.getCity());
            ourUser.setRole(registrationRequest.getRole());
            ourUser.setName(registrationRequest.getName());
            // Encrypt the user's password before storing it
            ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

            // Generate a JWT token for authentication
            var jwt = jwtUtils.generateToken(ourUser);
            // Generate a refresh token for future authentication
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), ourUser);
            // Save the user details in the repository
            OurUsers ourUsersResult = usersRepo.save(ourUser);

            // If user is successfully saved
            if (ourUsersResult.getId() != null) {
                // Set success response status code
                resp.setStatusCode(200);
                // Set JWT token in the response
                resp.setToken(jwt);
                // Set refresh token in the response
                resp.setRefreshToken(refreshToken);
                // Set expiration time for the tokens
                resp.setExpirationTime("24Hrs");
                // Set the saved user details in the response
                resp.setOurUsers(ourUsersResult);
                // Set success message in the response
                resp.setMessage("User Saved Successfully");
            }

        } catch (Exception e) {
            // If an exception occurs during registration
            // Set error response status code
            resp.setStatusCode(500);
            // Set error message in the response
            resp.setError(e.getMessage());
        }
        // Return the registration response
        return resp;
    }



    public ReqRes login(ReqRes loginRequest){
        ReqRes response = new ReqRes();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                    loginRequest.getPassword()));
            Optional<OurUsers> userOptional = usersRepo.findByEmail(loginRequest.getEmail());
            if (userOptional.isPresent()) {
                OurUsers user = userOptional.get();
                var jwt = jwtUtils.generateToken(user);
                var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRole(user.getRole());
                response.setRefreshToken(refreshToken);
                response.setExpirationTime("24Hrs");
                response.setMessage("Successfully Logged In");
            } else {
                response.setStatusCode(404);
                response.setMessage("User not found");
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }


    public ReqRes refreshToken(ReqRes refreshTokenRequest){
        ReqRes response = new ReqRes();
        try{
            String userEmail = jwtUtils.extractUsername(refreshTokenRequest.getRefreshToken());
            Optional<OurUsers> userOptional = usersRepo.findByEmail(userEmail);
            if (userOptional.isPresent()) {
                OurUsers user = userOptional.get();
                if (jwtUtils.isTokenValid(refreshTokenRequest.getRefreshToken(), user)) {
                    var jwt = jwtUtils.generateToken(user);
                    response.setStatusCode(200);
                    response.setToken(jwt);
                    response.setRefreshToken(refreshTokenRequest.getRefreshToken());
                    response.setExpirationTime("24Hrs");
                    response.setMessage("Successfully Refreshed Token");
                } else {
                    response.setStatusCode(401); // Unauthorized
                    response.setMessage("Invalid refresh token");
                }
            } else {
                response.setStatusCode(404); // Not Found
                response.setMessage("User not found");
            }
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    // Other methods remain unchanged...
    // Other methods remain unchanged...

    public ReqRes getAllUsers() {
        ReqRes reqRes = new ReqRes();

        try {
            List<OurUsers> result = usersRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setOurUsersList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes getUsersById(Integer id) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(id);
            if (userOptional.isPresent()) {
                OurUsers user = userOptional.get();
                reqRes.setOurUsers(user);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User with id '" + id + "' found successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found with id: " + id);
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes deleteUser(Integer userId) {
        ReqRes reqRes = new ReqRes();
        try {
            if (usersRepo.existsById(userId)) {
                usersRepo.deleteById(userId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes updateUser(Integer userId, OurUsers updatedUser) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                OurUsers existingUser = userOptional.get();
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setName(updatedUser.getName());
                existingUser.setCity(updatedUser.getCity());
                existingUser.setRole(updatedUser.getRole());

                // Check if password is present in the request
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    // Encode the password and update it
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                OurUsers savedUser = usersRepo.save(existingUser);
                reqRes.setOurUsers(savedUser);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes getMyInfo(String email){
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                OurUsers user = userOptional.get();
                reqRes.setOurUsers(user);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User info retrieved successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;
    }

}
