package com.jv.didi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptEncoderTest {

	public static void main(String[] args) throws IOException {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter password to encrypt:");
		String passwordToEncode = br.readLine();
		for (int i = 1; i <= 10; i++) {
			System.out.println(encoder.encode(passwordToEncode));
		}

	}

}
