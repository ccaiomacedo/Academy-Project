package br.com.academy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.academy.Enums.Curso;
import br.com.academy.Enums.Status;

@Entity
public class Aluno {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "nome")
	@Size(min=4,max=35, message="Nome não pode conter menos que 5 caracteres")
	@NotBlank(message="Esse campo não pode ser vazio")
	private String nome;

	@Column(name = "curso")
	@NotNull(message="Esse campo não pode ser nulo")
	private Curso curso;

	@Column(name = "matricula")
	@NotNull(message="Clique em gerar matricula")
	@Size(min=3,message="Clique em gerar matricula!")
	private String matricula;

	@Column(name = "status")
	@NotNull(message="Esse campo não pode ser nulo")
	private Status status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}
	@Size(min=4, message="O campo turno não pode conter menos que 4 caracteres")
	@NotBlank(message="Esse campo não pode ser vazio")
	private String turno;

}
