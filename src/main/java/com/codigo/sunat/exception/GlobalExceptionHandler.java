package com.codigo.sunat.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {
		Map<String, String> response = new HashMap<>();
		response.put("message", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<Map<String, String>> handleFeignException(FeignException e){
		Map<String, String> response = new HashMap<>();
		
		String message;
		
		//String message = e.contentUTF8();
		/*
		if(message == null || message.isEmpty()) {
			message = "Error en el proveedor externo";
		}*/
		
		if (e.status() == 401) {
			message = "Error de autenticación, token inválido o expirado";
		}else if (e.status() >= 500) {
			message = "El servicio de SUNAT no esta disponible";
		}else {
			String body = e.contentUTF8();
			message = (body != null && !body.isEmpty())?body:"Error inesperado en el proveedor";
		}
		
		response.put("message", message);
		return ResponseEntity.status(e.status()>0?e.status():500).body(response);
	}
}
