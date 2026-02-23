package com.codigo.sunat.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.codigo.sunat.client.SunatFeignClient;
import com.codigo.sunat.dto.SunatRucResponse;
import com.codigo.sunat.entity.Company;
import com.codigo.sunat.entity.Consulta;
import com.codigo.sunat.enums.ResultadoConsulta;
import com.codigo.sunat.mapper.SunatMapper;
import com.codigo.sunat.repository.SunatCompanyRepository;
import com.codigo.sunat.repository.SunatConsultaRepository;

import feign.FeignException;

@Service
public class SunatService {
	private final SunatFeignClient sunatFeignClient;
	private final SunatCompanyRepository companyRepository;
	private final SunatConsultaRepository consultaRepository;
	private final SunatMapper sunatMapper;

	@Value("${decoleta.token}")
	private String token;

	public SunatService(SunatFeignClient sunatFeignClient, SunatCompanyRepository companyRepository,
			SunatConsultaRepository consultaRepository, SunatMapper sunatMapper) {
		super();
		this.sunatFeignClient = sunatFeignClient;
		this.companyRepository = companyRepository;
		this.consultaRepository = consultaRepository;
		this.sunatMapper = sunatMapper;
	}

	public Company obtenerYGuardarRuc(String ruc) {
		if (ruc == null || !ruc.matches("\\d{11}")) {
			throw new IllegalArgumentException("RUC debe tener 11 dígitos");
		}
		
		//========================================================
		Optional<Company> existingCompany = companyRepository.findByRuc(ruc);
		if (existingCompany.isPresent()) {
			Company company = existingCompany.get();
			if(company.getCreatedAt() != null && 
					company.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(10))) {
				
				registrarConsulta(ruc, ResultadoConsulta.SUCCESS, "SUCCESS CACHE", 200, company);
				return company;
			}
		}
		//========================================================
		
		try {
			SunatRucResponse response = sunatFeignClient.getRucData("Bearer " + token, ruc);
			
			Company company = sunatMapper.toEntity(response);
			
			/*
			Company guardarCompany = companyRepository.findByRuc(ruc)
					.map(existing -> {
						updateExistingCompany(existing, company);
						return companyRepository.save(existing);
					})
					.orElseGet(() -> {
						return companyRepository.save(company);
					});
			*/
			//========================================================
			Company guardarCompany = existingCompany.map(existing -> {
                updateExistingCompany(existing, company);
                existing.setCreatedAt(LocalDateTime.now());
                return companyRepository.save(existing);
            })
            .orElseGet(() -> companyRepository.save(company));
			//========================================================
			
			registrarConsulta(ruc, ResultadoConsulta.SUCCESS, null, 200, guardarCompany);
			
			return guardarCompany;
			
		} catch (FeignException e) {
			// TODO: handle exception
			registrarConsulta(ruc, ResultadoConsulta.ERROR, e.getMessage(), e.status(), null);
            throw e;
		}
	}
	
	private void registrarConsulta(String ruc, ResultadoConsulta resultado, String mensaje, Integer status, Company company) {
        Consulta consulta = new Consulta();
        consulta.setRucConsultado(ruc);
        consulta.setResultado(resultado);
        consulta.setMensajeError(mensaje);
        consulta.setProviderStatusCode(status);
        consulta.setCompany(company);
        
        consultaRepository.save(consulta);
    }

	private void updateExistingCompany(Company existing, Company newData) {
        existing.setRazonSocial(newData.getRazonSocial());
        existing.setEstado(newData.getEstado());
        existing.setCondicion(newData.getCondicion());
        existing.setDireccion(newData.getDireccion());
        existing.setUbigeo(newData.getUbigeo());
        existing.setDepartamento(newData.getDepartamento());
        existing.setProvincia(newData.getProvincia());
        existing.setDistrito(newData.getDistrito());
        existing.setEsAgenteRetencion(newData.isEsAgenteRetencion());
        existing.setEsBuenContribuyente(newData.isEsBuenContribuyente());
        existing.setCreatedAt(LocalDateTime.now());
    }
}
