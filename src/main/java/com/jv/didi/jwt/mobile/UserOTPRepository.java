package com.jv.didi.jwt.mobile;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserOTPRepository extends CrudRepository<UserOTP, Integer> {

	Optional<UserOTP> findByMobileNumberAndOtp(String mobileNumber, Integer otp);

    UserOTP findByMobileNumber(String mobileNumber);
}

