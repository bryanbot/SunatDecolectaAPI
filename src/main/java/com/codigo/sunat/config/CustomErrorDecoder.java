package com.codigo.sunat.config;

import org.springframework.stereotype.Component;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class CustomErrorDecoder implements ErrorDecoder {
	@Override
	public Exception decode(String methodKey, Response response) {
		return FeignException.errorStatus(methodKey, response);
	}
}
