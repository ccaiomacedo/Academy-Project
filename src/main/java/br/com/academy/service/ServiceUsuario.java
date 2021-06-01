package br.com.academy.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academy.Exceptions.CriptoExistException;
import br.com.academy.Exceptions.EmailExistsException;
import br.com.academy.Exceptions.ServiceExc;
import br.com.academy.dao.UsuarioDao;
import br.com.academy.model.Usuario;
import br.com.academy.util.Util;
/********camada de negocio***********/

@Service
public class ServiceUsuario {
	@Autowired
	UsuarioDao repository;

	//faz a verificação pra ver se ja existe esse email
	public void salvarUsuario(Usuario user) throws Exception {
		try{
			if(repository.findByEmail(user.getEmail())!=null) {
				throw new EmailExistsException("Já existe um email cadastrado para: "+user.getEmail());
			}
			user.setSenha(Util.md5(user.getSenha()));//estou setando a senha para a senha criptografada
			
		}catch(NoSuchAlgorithmException e){
			throw new CriptoExistException("Erro na criptografia da senha");
		}
		repository.save(user);
	}
	
	//faz a verificação do usuario e senha passado
	public Usuario loginUser(String user,String senha) throws ServiceExc { 
		Usuario userLogin = repository.buscarLogin(user, senha);
		return userLogin;
	}


}
