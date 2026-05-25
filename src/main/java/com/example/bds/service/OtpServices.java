package com.example.bds.service;

import com.example.bds.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OtpServices {


    private final MailServices mailServices;



    @Value("${app.otp.expire-minutes}")
    private int expiryDate;

    @Value("${app.otp.max-failed-attempts}")
    private int failedCountMax;


    private SecureRandom random = new SecureRandom();
    private final RedisTemplate<String, Object> redisTemplate;


    public int genneraOtp(){
        return random.nextInt(900000) + 100000;
    }

    public void sendOtpMail(String email, String otpCode) throws Exception{
        try {
            mailServices.sendMail(email, otpCode);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }

    public boolean verified (String email, String otp) throws Exception {

        String redisKey ="otp:"+email;
        if(!Objects.equals(redisTemplate.opsForHash().get(redisKey, "otpCode"), otp)){
            redisTemplate.opsForHash().increment(redisKey,"failedCount",1);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "OTP is not match");
        }

        Integer failedCount = (Integer) redisTemplate.opsForHash().get(redisKey,"fbailedCount");
        if(failedCount != null && failedCount > failedCountMax){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "you are not allowed to send otp, please try again after a few minutes");
        }
       return true;
    }

    public void deleteOtpMail(String email){
        try {
            String redisKey ="otp:"+email;
            redisTemplate.delete(redisKey);
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,"ô mai gót lỗi rồi");
        }
    }
}
