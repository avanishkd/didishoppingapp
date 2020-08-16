package com.jv.didi.jwt.mobile;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserOTP {

    @Id
    private String mobileNumber;
    private Integer otp;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

}
