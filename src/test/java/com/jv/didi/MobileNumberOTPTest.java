package com.jv.didi;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.jv.didi.jwt.mobile.UserOTP;
import com.jv.didi.jwt.mobile.UserOTPRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import com.jv.didi.jwt.mobile.AuthController;

@SpringBootTest
public class MobileNumberOTPTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private UserOTPRepository userOTPRepository;
    @Test
    void testMobileNumberOTPGeneration() {
        // given
        String mobileNumber = "9809809809";

        //when
        ResponseEntity<?> msg = authController.authenticateUserMobile(mobileNumber);

        //then
        assertSame(msg.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testMobileNumberOTPValidationWrong() {
        // given
        String mobileNumber = "9809809809";
        Integer invalidOTP = 0000;

        //when
        assertThrows(BadCredentialsException.class, () -> {
            authController.authenticateUserMobileOTP(mobileNumber, invalidOTP);
        });
    }

    @Test
    void testMobileNumberOTPValidationMaster() {
        // given
        String mobileNumber = "9809809809";
        authController.authenticateUserMobile(mobileNumber);

        //when
        ResponseEntity<?> msg = authController.authenticateUserMobileOTP(mobileNumber, 98127634);
        System.out.println(msg.getBody());
        //then
        assertSame(msg.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testMobileNumberOTPValidation() {
        // given
        String mobileNumber = "9809809809";
        authController.authenticateUserMobile(mobileNumber);
        UserOTP userOTP = userOTPRepository.findByMobileNumber(mobileNumber);

        //when
        ResponseEntity<?> msg = authController.authenticateUserMobileOTP(mobileNumber, userOTP.getOtp());
        System.out.println(msg.getBody());

        //then
        assertSame(msg.getStatusCode(), HttpStatus.OK);
    }


}
