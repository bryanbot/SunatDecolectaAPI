package com.codigo.sunat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codigo.sunat.entity.Company;

public interface SunatCompanyRepository extends JpaRepository<Company, Long> {
	Optional<Company> findByRuc(String ruc);
}
