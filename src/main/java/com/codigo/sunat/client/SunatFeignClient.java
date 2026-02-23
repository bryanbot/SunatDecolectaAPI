package com.codigo.sunat.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.codigo.sunat.dto.SunatRucResponse;

@FeignClient(name = "sunat-client", url = "${decoleta.base-url}/v1")
public interface SunatFeignClient {
	@GetMapping("/sunat/ruc")
	SunatRucResponse getRucData(
			@RequestHeader("Authorization") String token,
			@RequestParam("numero") String ruc);
}
