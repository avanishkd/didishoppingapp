package com.jv.didi.jwt.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*")
public class AuthController {

	 @Autowired
	  private AuthService authService;
	 
	 @PostMapping("/login/mobile/v1")
	  public ResponseEntity<?> authenticateUserMobile(@RequestParam(name = "mobileNumber")
	  	String mobileNumber) {
	      return ResponseEntity.ok(authService.authenticateUserMobile(mobileNumber));
	  }

	  @PutMapping("/login/mobile/v1")
	  public ResponseEntity<?> authenticateUserMobileOTP(@RequestParam(name = "mobileNumber") String mobileNumber,
	                                                     @RequestParam(name = "otp") Integer otp) {
	      return ResponseEntity.ok(authService.authenticateUserMobileOTP(mobileNumber,otp));
	  }

}
