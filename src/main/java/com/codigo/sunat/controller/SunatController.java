package com.codigo.sunat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codigo.sunat.entity.Company;
import com.codigo.sunat.entity.Consulta;
import com.codigo.sunat.repository.SunatConsultaRepository;
import com.codigo.sunat.service.SunatService;

@RestController
@RequestMapping("/api/sunat")
public class SunatController {
	
	private final SunatService sunatService;
	private final SunatConsultaRepository consultaRepository;
	
	public SunatController(SunatService sunatService, SunatConsultaRepository consultaRepository) {
		super();
		this.sunatService = sunatService;
		this.consultaRepository = consultaRepository;
	}
	
	@GetMapping("/ruc/{ruc}")
	public ResponseEntity<Company> consultarRuc(@PathVariable String ruc){
		Company company = sunatService.obtenerYGuardarRuc(ruc);
		return ResponseEntity.ok(company);
	}
	
	@GetMapping("/ruc/{ruc}/consultas")
	public ResponseEntity<List<Consulta>> obtenerHistorial(@PathVariable String ruc) {
		// TODO Auto-generated constructor stub
		List<Consulta> historial = consultaRepository.findByRucConsultadoOrderByCreatedAtDesc(ruc);
		return ResponseEntity.ok(historial);
	}
}
