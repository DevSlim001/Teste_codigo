package com.fatec.API3.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;

@Entity
public class Administrador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/*Colunas da tabela ADM*/
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Long id;
	
	@NotEmpty
	private String nome; 
	
	@NotEmpty
	private String celular;
	
	@NotEmpty
	private String nascimento;
	
	@NotEmpty
	private String senha;
	
	@NotEmpty
	private String email;
	
	private String nomearquivo;
	
	@Nullable
	private String codigosenha;
	
	private String novidade;
	
	/*Gets e Setters das colunas*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getNascimento() {
		return nascimento;
	}

	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomearquivo() {
		return nomearquivo;
	}

	public void setNomearquivo(String nomearquivo) {
		this.nomearquivo = nomearquivo;
	}

	public String getNovidade() {
		return novidade;
	}

	public void setNovidade(String novidade) {
		this.novidade = novidade;
	}

	public String getCodigosenha() {
		return codigosenha;
	}

	public void setCodigosenha(String codigosenha) {
		this.codigosenha = codigosenha;
	}

	
}
