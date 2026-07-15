package com.example.bds.service;

import com.example.bds.dto.Request.*;
import com.example.bds.dto.Response.LoginResponse;
import com.example.bds.dto.Response.RoleResponse;
import com.example.bds.dto.Response.UserResponse;
import com.example.bds.entity.BlacklistToken;
import com.example.bds.entity.rbac.Roles;
import com.example.bds.entity.rbac.Users;
import com.example.bds.mapper.UserMapper;
import com.example.bds.repository.BlacklistTokenRepository;
import com.example.bds.repository.RoleRepository;
import com.example.bds.repository.UserRepository;
import com.example.bds.security.JwtUltil;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServices {

    private final MailServices mailServices;
    @Value("${app.otp.max-failed-attempts}")
    private String failedCount;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CustomUserService customUserService;
    private final RoleRepository roleRepository;
    private final OtpServices otpServices;
    private final JwtUltil  jwtUltil;
    private final BlacklistTokenRepository blacklistTokenRepository;
    private static final Map<String,Integer> failedAttempts = new HashMap<>();
    private static final Integer MAX_FAILED_ATTEMPTS = 5;
    private static final Integer LOCKED_ATTEMPTS = 15;
    private static final Map<String, LocalDateTime> lockedAttempts = new HashMap<>();
    private final UserMapper userMapper;

    public LoginResponse login(LoginRequest loginRequest) {
        if(isAccountLocked(loginRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"TAI KHOAN BI KHOA ROI");
        } 
       Users user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()
               -> {
                   incrementFailedAttempts(loginRequest.getUsername());
                   return new ResponseStatusException(HttpStatus.UNAUTHORIZED);
               });

       if(user.getStatus() == Users.AccountStatus.LOCKED && user.getStatus() == Users.AccountStatus.BANNED){
           throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"TAI KHOAN BI BAN ROI");
       }
       if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
           incrementFailedAttempts(loginRequest.getUsername());
           throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"MAT KHAU KHONG KHOP");
       }



       resetFailedCount(loginRequest.getUsername());

       String token = jwtUltil.generateToken(user);
       String refresh_token = jwtUltil.generateRefreshToken(user);
       String hashRefreshToken = hashToken(refresh_token) ;


       String refreshKey = "refresh_token:"+user.getId();
       redisTemplate.opsForHash().put(refreshKey,"value",hashRefreshToken);
       redisTemplate.opsForHash().put(refreshKey,"userId",user.getId());
       redisTemplate.expire(refreshKey, Duration.ofHours(10));

       UserResponse userResponse = mapToResponse(user);
       LoginResponse loginResponse = new LoginResponse();
       loginResponse.setToken(token);
       loginResponse.setRefreshToken(refresh_token);
       loginResponse.setUser(userResponse);
       return loginResponse;

    }


    public UserResponse registerWithEmail(RegisterRequest registerRequest) {
        Users user = new Users();
        try{
            if(otpServices.verified(registerRequest.getEmail1(), registerRequest.getOtp())) {
                user = userMapper.toEntity(registerRequest);
                user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                Roles roles = roleRepository.findByRoleName("ROLE_USER");
                Set<Roles> roleSet = new HashSet<>();
                roleSet.add(roles);
                user.setRoles(roleSet);
//                user.setEmail(registerRequest.getEmail1());
                user = userRepository.save(user);
                otpServices.deleteOtpMail(user.getEmail());
            }
        }catch(Exception e){
              e.printStackTrace();
              throw new RuntimeException("lỗi khi đăng kí");
        }
        return mapToResponse(user);
    }


    public CompletableFuture<ResponseEntity<String>> register(String username, String password, String email) {
        String redisKey ="otp:"+email;
                try {


                        String otpCode = String.valueOf(otpServices.genneraOtp());
                        otpServices.sendOtpMail(email, otpCode);
                        redisTemplate.opsForHash().put(redisKey, "otpCode", otpCode);
                        redisTemplate.opsForHash().put(redisKey, "FailedCount", failedCount);
                        redisTemplate.opsForHash().put(redisKey, "userName", username);
                        redisTemplate.opsForHash().put(redisKey, "password", password);
                        redisTemplate.expire(redisKey, Duration.ofMinutes(5));

                    }
                catch (Exception e) {
                    e.printStackTrace();
                    throw new ResponseStatusException(HttpStatus.CONFLICT,"vui lòng sử dụng tên đăng nhập hoặc email khác ");
                }
                return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK).body("he heh he h eheh"));
    }


    public ResponseEntity<String> forgotPasswordStep(ForgotPasswordStepRequest forgotPasswordStepRequest) throws Exception{
           String email = forgotPasswordStepRequest.getEmail();
           Users user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           UserDetails userDetails = (UserDetails) authentication.getPrincipal();

           if(user.getStatus() == Users.AccountStatus.LOCKED && !userDetails.getUsername().equals(user.getUsername())) {
               throw new ResponseStatusException(HttpStatus.FORBIDDEN);
           }
        String otpCode = String.valueOf(otpServices.genneraOtp());
           otpServices.sendOtpMail(email, otpCode);
           String redisKey ="otpForgot:"+email;
           redisTemplate.opsForHash().put(redisKey, "otpCode", otpCode);
           redisTemplate.opsForHash().put(redisKey, "FailedCount", 0);
           redisTemplate.opsForHash().put(redisKey, "email", email);
           redisTemplate.expire(redisKey, Duration.ofMinutes(5));

           return ResponseEntity.status(HttpStatus.OK).body("mã otp đã được gửi về mail của bạn")  ;

    }

    public UserResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {

        String email = forgotPasswordRequest.getEmail();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if(user.getStatus() == Users.AccountStatus.LOCKED && !userDetails.getUsername().equals(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        String otpCode = forgotPasswordRequest.getOtpCode();
        String redisKey = "otpForgot:" + email;
        String otp = String.valueOf(redisTemplate.opsForHash().get(redisKey, "otpCode"));
        if (!otp.equals(otpCode)) {
            redisTemplate.opsForHash().increment(redisKey, "FailedCount", 1);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Integer count = (Integer) redisTemplate.opsForHash().get(redisKey, "FailedCount");

        if (count != null && count >= 5) {

            redisTemplate.opsForHash().put("tmpBan", "user_id",user.getId());
            redisTemplate.expire("tmpBan", Duration.ofMinutes(15));
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"ban da nhap qua so lan quy dinh");
        }
      String password = passwordEncoder.encode(forgotPasswordRequest.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        return mapToResponse(user);
    }

    private UserResponse mapToResponse(Users user){
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setUsername(user.getUsername());
        Set<Roles> roleSet = user.getRoles();
        Set<RoleResponse>  roleSetResponse =roleSet.stream().map(role ->{
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setRoleName(role.getRoleName());
            return roleResponse;
        }).collect(Collectors.toSet());
        userResponse.setRole(roleSetResponse);
        return userResponse;
    }

    private void incrementFailedAttempts (String username){
        int attempts = failedAttempts.getOrDefault(username,0) + 1 ;
        failedAttempts.put(username,attempts);

        if(attempts > MAX_FAILED_ATTEMPTS){
            lockedAttempts.put(username,LocalDateTime.now());
        }

    }
    private boolean isAccountLocked(String username){
        LocalDateTime lockedTime = lockedAttempts.get(username);
        if(lockedTime != null){
            if(lockedTime.isBefore(lockedTime.plusMinutes(LOCKED_ATTEMPTS))){
                return  true;
            }
            else {
                lockedAttempts.remove(username);
                failedAttempts.remove(username);
            }

        }
        return false;
    }

    private void resetFailedCount(String username){
        lockedAttempts.remove(username);
        failedAttempts.remove(username);
    }


    public boolean logout(LogoutRequest logoutRequest){
        String token = logoutRequest.getToken();

        String username = jwtUltil.getUsername(token);

        Users user = userRepository.findByUsername(username).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.UNAUTHORIZED));

       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       UserDetails userDetails = (UserDetails) authentication.getPrincipal();
       if (!userDetails.getUsername().equals(username)) {
           throw  new ResponseStatusException(HttpStatus.FORBIDDEN,"không được đăng xuất tài khoản của người khác ");
       }

      BlacklistToken blacklistToken = new BlacklistToken();
       blacklistToken.setToken(token);
       blacklistToken.setUser(user);
       blacklistTokenRepository.save(blacklistToken);


       return true;

    }

    private String hashToken(String token)  {
      try{
          MessageDigest digest = MessageDigest.getInstance("SHA-256");
          byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
          return HexFormat.of().formatHex(hash);
      } catch (Exception e) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"khoong hash duoc");
      }
    }
}
