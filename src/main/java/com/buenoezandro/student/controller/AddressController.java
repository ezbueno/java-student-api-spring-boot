package com.buenoezandro.student.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/address")
public class AddressController {
	
	@GetMapping(path = "/getAddress")
	public String getAddress() {
		return "This is address of student!";
	}

}
