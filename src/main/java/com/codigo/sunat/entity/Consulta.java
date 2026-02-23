package com.codigo.sunat.entity;

import java.time.LocalDateTime;

import com.codigo.sunat.enums.ResultadoConsulta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "consultas")
public class Consulta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 11)
	private String rucConsultado;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ResultadoConsulta resultado;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;
	
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@Column(name = "mensaje_error")
	private String mensajeError;
	
	@Column(name = "provider_status_code")
	private Integer providerStatusCode;

	public Long getId() {
		return id;
	}

	public String getRucConsultado() {
		return rucConsultado;
	}

	public ResultadoConsulta getResultado() {
		return resultado;
	}

	public Company getCompany() {
		return company;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public Integer getProviderStatusCode() {
		return providerStatusCode;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRucConsultado(String rucConsultado) {
		this.rucConsultado = rucConsultado;
	}

	public void setResultado(ResultadoConsulta resultado) {
		this.resultado = resultado;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public void setProviderStatusCode(Integer providerStatusCode) {
		this.providerStatusCode = providerStatusCode;
	}
	
	
	public Consulta() {
		super();
	}

	public Consulta(String rucConsultado, ResultadoConsulta resultado, Company company, LocalDateTime createdAt,
			String mensajeError, Integer providerStatusCode) {
		super();
		this.rucConsultado = rucConsultado;
		this.resultado = resultado;
		this.company = company;
		this.createdAt = createdAt;
		this.mensajeError = mensajeError;
		this.providerStatusCode = providerStatusCode;
	}

	
}
