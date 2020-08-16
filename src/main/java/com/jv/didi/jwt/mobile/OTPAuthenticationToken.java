package com.jv.didi.jwt.mobile;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class OTPAuthenticationToken implements Authentication {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private boolean authenticated = true;
	private final String mobileNumber;

	public OTPAuthenticationToken(Object principal) {
		this.mobileNumber = (String) principal;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@Override
	public Object getCredentials() {
		return mobileNumber;
	}

	@Override
	public Object getDetails() {
		return mobileNumber;
	}

	@Override
	public Object getPrincipal() {
		return mobileNumber;
	}

	@Override
	public boolean isAuthenticated() {
		return this.authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated = isAuthenticated;
	}

	@Override
	public String getName() {
		return mobileNumber;
	}
}
