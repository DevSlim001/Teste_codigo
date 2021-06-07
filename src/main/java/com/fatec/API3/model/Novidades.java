package com.fatec.API3.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Novidades implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		/*Colunas da tabela Novidades*/
		@Id
		@GeneratedValue( strategy = GenerationType.AUTO )
		private Long id;
		
		@NotEmpty
		private String titulo; 
		
		@NotEmpty
		private String descricao;
		
		private String imagem;

		/*Gets e Setters das colunas*/
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public String getImagem() {
			return imagem;
		}

		public void setImagem(String imagem) {
			this.imagem = imagem;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
			
}
