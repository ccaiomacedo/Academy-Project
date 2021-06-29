package br.com.academy.controllers;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.academy.Exceptions.ServiceExc;
import br.com.academy.dao.UsuarioDao;
import br.com.academy.model.Aluno;
import br.com.academy.model.Usuario;
import br.com.academy.service.ServiceUsuario;
import br.com.academy.util.Util;

@Controller //classe controller é a responsavel por pegar requisições e dar respostas
public class UsuarioController {
	
	@Autowired
	private UsuarioDao ur;
	@Autowired
	private ServiceUsuario su;
	
	@GetMapping("/")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login/login");
		mv.addObject("usuario",new Usuario());
		return mv;
	}
	@GetMapping("/cadastro")
	public ModelAndView cadastrar() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario",new Usuario());
		mv.setViewName("login/cadastro");
		return mv;
	}
	@GetMapping("/index")//  a requisição barra refere-se a página inicial do projeto
	public ModelAndView index() {// do tipo ModelAndView pq ela recebe as requisições e trata elas pra poder retonar ao browser
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home/index");//estou setando qual a view da aplicação,aqui que eu chamo o objeto
		mv.addObject("aluno", new Aluno());//pagina index recebe um objeto do tipo aluno
		return mv;
	}
	@PostMapping("salvarUsuario")
	public ModelAndView salvarCadastro(Usuario usuario) throws Exception {
		ModelAndView mv = new ModelAndView();
		su.salvarUsuario(usuario);
		mv.setViewName("redirect:/");
		return mv;
	}
	
	@PostMapping("/login")
	public ModelAndView login(@Valid Usuario usuario,BindingResult br,HttpSession session) throws NoSuchAlgorithmException, ServiceExc{
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario",new Usuario());
		if(br.hasErrors()) {
			mv.setViewName("login/login");//se tiver erro, volta pra mesma página por causa do BindingResult
		}
		Usuario userLogin = su.loginUser(usuario.getNome(), Util.md5(usuario.getSenha()));
		if(userLogin==null) {
			mv.addObject("msg","Usuário não encontrado.Tente novamente");
		}else{
			session.setAttribute("usuarioLogado", userLogin);
			return index();
		}
		return mv;
	}
	@PostMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		return login();
	}
	
}
