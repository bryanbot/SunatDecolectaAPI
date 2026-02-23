package com.codigo.sunat.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.codigo.sunat.enums.CondicionDomicilio;
import com.codigo.sunat.enums.EstadoContribuyente;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "companies")
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length = 11)
	private String ruc;
	
	private String razonSocial;
	
	@Enumerated(EnumType.STRING)
	private EstadoContribuyente estado;
	
	@Enumerated(EnumType.STRING)
	private CondicionDomicilio condicion;
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Consulta> consultas;
	
	private String direccion;
	private String ubigeo;
	private String departamento;
	private String provincia;
	private String distrito;
	
	private boolean esAgenteRetencion;
	private boolean esBuenContribuyente;
	
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	public Long getId() {
		return id;
	}
	public String getRuc() {
		return ruc;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public EstadoContribuyente getEstado() {
		return estado;
	}
	public CondicionDomicilio getCondicion() {
		return condicion;
	}
	public List<Consulta> getConsultas() {
		return consultas;
	}
	public String getDireccion() {
		return direccion;
	}
	public String getUbigeo() {
		return ubigeo;
	}
	public String getDepartamento() {
		return departamento;
	}
	public String getProvincia() {
		return provincia;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public void setEstado(EstadoContribuyente estado) {
		this.estado = estado;
	}
	public void setCondicion(CondicionDomicilio condicion) {
		this.condicion = condicion;
	}
	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public void setUbigeo(String ubigeo) {
		this.ubigeo = ubigeo;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	public boolean isEsAgenteRetencion() {
		return esAgenteRetencion;
	}
	public boolean isEsBuenContribuyente() {
		return esBuenContribuyente;
	}
	public void setEsAgenteRetencion(boolean esAgenteRetencion) {
		this.esAgenteRetencion = esAgenteRetencion;
	}
	public void setEsBuenContribuyente(boolean esBuenContribuyente) {
		this.esBuenContribuyente = esBuenContribuyente;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
