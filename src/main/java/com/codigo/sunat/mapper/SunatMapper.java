package com.codigo.sunat.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.codigo.sunat.dto.SunatRucResponse;
import com.codigo.sunat.entity.Company;
import com.codigo.sunat.enums.CondicionDomicilio;
import com.codigo.sunat.enums.EstadoContribuyente;

@Component
public class SunatMapper {
	public static Company toEntity(SunatRucResponse dto) {
		if(dto == null) return null;
		Company company = new Company();
		company.setRuc(dto.numero_documento());
		company.setRazonSocial(dto.razon_social());
		company.setEstado(mapEstado(dto.estado()));
		company.setCondicion(mapCondicion(dto.condicion()));
		company.setDireccion(dto.direccion());
		company.setUbigeo(dto.ubigeo());
		company.setDepartamento(dto.departamento());
		company.setProvincia(dto.provincia());
		company.setDistrito(dto.distrito());
		company.setEsAgenteRetencion(dto.es_agente_retencion());
		company.setEsBuenContribuyente(dto.es_buen_contribuyente());
		company.setCreatedAt(LocalDateTime.now());
		
		return company;
	}
	
	private static EstadoContribuyente mapEstado(String estado) {
		try {
			return EstadoContribuyente.valueOf(estado.toUpperCase());
		} catch (Exception e) {
			// TODO: handle exception
			return EstadoContribuyente.DESCONOCIDO;
		}
	}
	
	private static CondicionDomicilio mapCondicion(String condicion) {
		try {
			return CondicionDomicilio.valueOf(condicion.toUpperCase());
		} catch (Exception e) {
			// TODO: handle exception
			return CondicionDomicilio.DESCONOCIDO;
		}
	}
}
