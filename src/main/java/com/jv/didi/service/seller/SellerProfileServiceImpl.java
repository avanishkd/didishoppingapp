package com.jv.didi.service.seller;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jv.didi.dao.seller.SellerRepository;
import com.jv.didi.dto.seller.SellerRegisterDTO;
import com.jv.didi.dto.seller.response.SellerLoginResponseDTO;
import com.jv.didi.entity.seller.Seller;
import com.jv.didi.entity.seller.SellerStatus;

@Service

public class SellerProfileServiceImpl implements SellerProfileService {

	@Autowired
	private SellerRepository sellerRepo;

	ModelMapper modelMapper;

	private Seller seller;

	@Override
	@Transactional
	public Seller login(@Min(value = 1, message = "Invalid Seller ID.") long id,
			@NotBlank(message = "Password may not be blank") String password) {

		seller = new Seller();
		seller.setId(id);
		seller.setPassword(password);
		Seller getSeller = sellerRepo.findByIdAndPassword(id, password);
		if (getSeller != null) {
			getSeller.setStatus(SellerStatus.LOGIN.name());
			return sellerRepo.save(getSeller);
		}

		return null;
	}

	@Override
	@Transactional
	public Seller saveSeller(Seller seller) {
		// TODO Auto-generated method stub
		Seller sellerReturn = sellerRepo.save(seller);
		return sellerReturn;
	}

	@Override
	@Transactional

	public Seller registerSeller(SellerRegisterDTO sellerDto) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setAmbiguityIgnored(true);
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

		Seller seller = mapper.map(sellerDto, Seller.class);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		seller.setPassword(encoder.encode(sellerDto.getPassword()));
		seller.setStatus(SellerStatus.ACTIVE.name());
		// TODO Auto-generated method stub
		Seller sellerReturn = sellerRepo.save(seller);
		return sellerReturn;
	}

	@Override
	@Transactional
	public SellerLoginResponseDTO getSellerById(long id) throws NotFoundException {
		modelMapper = new ModelMapper();
		Optional<Seller> Seller = sellerRepo.findById(id);

		SellerLoginResponseDTO response = modelMapper.map(Seller.orElseThrow(() -> new NotFoundException()),
				SellerLoginResponseDTO.class);
		return response;
	}

}