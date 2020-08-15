package com.jv.didi.service.seller;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.validation.annotation.Validated;

import com.jv.didi.dto.seller.SellerRegisterDTO;
import com.jv.didi.dto.seller.response.SellerLoginResponseDTO;
import com.jv.didi.entity.seller.Seller;

@Validated
public interface SellerProfileService {
	Seller login(@Min(value = 1, message = "Invalid User ID.") long id,
			@NotBlank(message = "Password may not be blank") String password);

	Seller saveSeller(Seller seller);

	SellerLoginResponseDTO getSellerById(long id) throws NotFoundException;

	Seller registerSeller(SellerRegisterDTO sellerDto);

}