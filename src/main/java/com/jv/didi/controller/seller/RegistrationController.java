package com.jv.didi.controller.seller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jv.didi.dto.seller.SellerLoginDTO;
import com.jv.didi.dto.seller.SellerRegisterDTO;
import com.jv.didi.dto.seller.response.SellerLoginResponseDTO;
import com.jv.didi.dto.seller.response.SellerRegisterResponse;
import com.jv.didi.entity.seller.Seller;
import com.jv.didi.exception.ResourceNotFoundException;
import com.jv.didi.exception.UserSaveException;
import com.jv.didi.service.seller.SellerProfileService;

@RestController
@RequestMapping("/seller")
public class RegistrationController {

	private ModelMapper modelMapper;
	private Logger logger = LogManager.getLogger(RegistrationController.class);

	@Autowired
	private SellerProfileService sellerProfileService;

	@PostMapping("/register")
	public ResponseEntity<SellerRegisterResponse> sellerRegister(@RequestBody SellerRegisterDTO sellerDto) {
		modelMapper = new ModelMapper();

		Seller seller = sellerProfileService.registerSeller(sellerDto);
		if (seller == null) {
			logger.error("unable to register the seller");
			throw new UserSaveException("Exception Occurred!");
		}
		logger.info("seller created with Id:" + seller.getId());
		SellerRegisterResponse response = modelMapper.map(seller, SellerRegisterResponse.class);

		HttpHeaders headers = new HttpHeaders();
		headers.add("seller-registered", "yes");

		return new ResponseEntity<SellerRegisterResponse>(response, headers, HttpStatus.OK);
	}

	@PutMapping("/login")
	public ResponseEntity<SellerLoginResponseDTO> userLogin(@RequestBody SellerLoginDTO sellerDto) {
		modelMapper = new ModelMapper();

		Seller seller = sellerProfileService.login(sellerDto.getId(), sellerDto.getPassword());
		if (seller == null) {
			logger.error("the requested seller with id->{} is not available", sellerDto.getId());
			throw new ResourceNotFoundException("seller with given id->" + sellerDto.getId() + " not found");
		}
		logger.info("logged seller with id->{} and name ->{}", sellerDto.getId(), seller.getName());
		SellerLoginResponseDTO response = modelMapper.map(seller, SellerLoginResponseDTO.class);

		HttpHeaders headers = new HttpHeaders();
		headers.add("seller-available", "yes");

		return new ResponseEntity<SellerLoginResponseDTO>(response, headers, HttpStatus.OK);
	}

	@GetMapping("/status/{userId}")
	public ResponseEntity<SellerLoginResponseDTO> userStatus(@PathVariable long userId) {

		try {
			SellerLoginResponseDTO userResponse = sellerProfileService.getSellerById(userId);
			logger.info("seller with id->{} and name ->{} found", userResponse.getId(), userResponse.getName());

			HttpHeaders headers = new HttpHeaders();
			headers.add("seller-available", "yes");
			return new ResponseEntity<SellerLoginResponseDTO>(userResponse, headers, HttpStatus.OK);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("seller with id->{} not found", userId);
			throw new ResourceNotFoundException("seller with given id->" + userId + " not found");

		}
	}

}