package com.jv.didi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class ProfileController {

	@GetMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}
}
