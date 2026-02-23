package com.codigo.sunat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codigo.sunat.entity.Consulta;

public interface SunatConsultaRepository extends JpaRepository<Consulta, Long>{
	List<Consulta> findByRucConsultadoOrderByCreatedAtDesc(String ruc);
}
