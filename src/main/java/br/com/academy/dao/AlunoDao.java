package br.com.academy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.academy.model.Aluno;

/* essa interface serve para implementação padrão que ele já nos dá em
repositórios, incluindo métodos como save, delete, findOne, entre outros.*/
public interface AlunoDao extends JpaRepository<Aluno, Integer> {

	@Query("select a from Aluno a where a.status = 'ATIVO' ")//anotação do JPQL pra fazer um consulta específica
	public List<Aluno> findByStatusAtivos();
	
	@Query("select a from Aluno a where a.status = 'INATIVO' ")
	public List<Aluno> findByStatusInativos();
	
	@Query("select a from Aluno a where a.status = 'CANCELADO' ")
	public List<Aluno> findByStatusCancelado();
	
	@Query("select a from Aluno a where a.status = 'TRANCADO' ")
	public List<Aluno> findByStatusTrancados();
	
	
	public List<Aluno> findByNomeContainingIgnoreCase(String nome);//ele usa o jpa pra fazer o filtro de caso digite o aluno com letra maiuscula, nome incompleto...
}
