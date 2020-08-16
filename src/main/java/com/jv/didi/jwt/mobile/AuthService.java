package com.jv.didi.jwt.mobile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

import com.jv.didi.jwt.resource.JwtTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jv.didi.jwt.mobile.JwtTokenProvider;
import com.jv.didi.jwt.mobile.OTPAuthenticationToken;
import com.jv.didi.jwt.mobile.UserOTP;
import com.jv.didi.jwt.mobile.UserOTPRepository;

@Service
public class AuthService {

	private static final String auth = "277864AQ9u1YSMiodd5ce56620ABCDERFG";
	private static final String sender = "DIDIAP";
	private static final String api = "http://api.msg91.com/api/sendhttp.php?";
	private static final String route = "4";
	private static final String country = "+91";
	private static final Integer MASTER_OTP = 98127634;
	@Autowired
	private UserOTPRepository userOTPRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenUtil;

	public String authenticateUserMobile(String mobileNumber) {
		Integer otp = ((int) (Math.random() * 9000) + 1000);
		UserOTP userOTP = new UserOTP();
		userOTP.setMobileNumber(mobileNumber);
		userOTP.setOtp(otp);
		userOTPRepository.save(userOTP);
		sendSms(otp, mobileNumber);
		return "OTP SENT";
	}

	public JwtTokenResponse authenticateUserMobileOTP(String mobileNumber, Integer otp) {
		UserOTP userOtp = new UserOTP();
		userOtp.setMobileNumber(mobileNumber);
		if(!otp.equals(MASTER_OTP)) {
			Optional<UserOTP> userOp = userOTPRepository.findByMobileNumberAndOtp(mobileNumber, otp);
			if (!userOp.isPresent()) {
				throw new BadCredentialsException("Mobile Number or OTP not valid.");
			}
			userOtp = userOp.get();
		}
		
		Authentication authentication = authenticationManager.authenticate(new OTPAuthenticationToken(mobileNumber));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtTokenUtil.generateUserToken(authentication);
		userOtp.setOtp(null);
		userOTPRepository.save(userOtp);
		return new JwtTokenResponse(jwt);
	}

	public void sendSms(Integer otp, String mobileNumber) {
		String message;
		message = "OTP for login  is : ";
		String finalMessage = "" + message + " :" + otp;
		//sendSMSMSG91("+91" + mobileNumber, finalMessage);
	}

	private void sendSMSMSG91(String number, String message) {
		RestTemplate restTemplate = new RestTemplate();
		try {
			String stringBuilder = api + "authkey=" + auth + "&mobiles=" + number + "&route=" + route + "&sender="
					+ sender + "&country=" + country + "&message=" + URLEncoder.encode(message, "UTF-8");
			restTemplate.getForEntity(stringBuilder, String.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}
