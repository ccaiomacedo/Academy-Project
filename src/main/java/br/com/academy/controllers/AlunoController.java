package br.com.academy.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.academy.dao.AlunoDao;
import br.com.academy.model.Aluno;

@Controller
public class AlunoController {

	@Autowired
	private AlunoDao ar;

	@GetMapping("/inserirAlunos")
	public ModelAndView aluno(Aluno aluno) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("aluno/formAluno");
		mv.addObject("aluno", new Aluno());
		return mv;
	}

	@PostMapping("/InsertAlunos") // é post pq estou pegando a requisiçaõ que foi passada do formulário
	public ModelAndView inserirAluno(@Valid Aluno aluno, BindingResult br) {//o binding serve pra basicamente voltar ao formulário após o erro
		ModelAndView mv = new ModelAndView();
		if (br.hasErrors()) {
			mv.setViewName("aluno/formAluno");
			mv.addObject("aluno");
		} else {
			mv.setViewName("redirect:/alunos-adicionados");
			ar.save(aluno);
		}

		return mv;
	}

	@GetMapping("alunos-adicionados")
	public ModelAndView listagemAlunos() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("aluno/listAlunos");
		mv.addObject("alunosList", ar.findAll());// responsável por listar todos os alunos
		return mv;
	}

	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") Integer id) {// o @path... serve pra pegar o id que foi passado
		ModelAndView mv = new ModelAndView();
		mv.setViewName("aluno/alterar");
		Aluno aluno = ar.getOne(id);// esta pegando o id que foi passado como parametro
		mv.addObject("aluno", aluno);
		return mv;
	}

	@PostMapping("/alterar")
	public ModelAndView alterar(Aluno aluno) {
		ModelAndView mv = new ModelAndView();
		ar.save(aluno);
		mv.setViewName("redirect:/alunos-adicionados");
		return mv;

	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Integer id) {
		ar.deleteById(id);
		return "redirect:/alunos-adicionados";
	}
	@GetMapping("filtro-alunos")
	public ModelAndView filtroALunos() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("aluno/filtroAlunos");
		return mv;
	}
	@GetMapping("/alunos-ativos")
	public ModelAndView listaAlunosAtivos() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("aluno/alunos-Ativos");
		mv.addObject("alunosAtivos", ar.findByStatusAtivos());// objeto pra eu passar no th:each
		return mv;
	}
	@GetMapping("/alunos-inativos")
	public ModelAndView listaAlunosInativos() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("aluno/alunos-inativos");
		mv.addObject("alunosInativos",ar.findByStatusInativos());
		return mv;
	}
	@GetMapping("/alunos-cancelados")
	public ModelAndView listaAlunosCancelados() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("aluno/alunos-cancelados");
		mv.addObject("alunosCancelados",ar.findByStatusCancelado());
		return mv;
	}
	@GetMapping("/alunos-trancados")
	public ModelAndView listaAlunosTrancados() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("aluno/alunos-trancados");
		mv.addObject("alunosTrancados",ar.findByStatusTrancados());
		return mv;
	}
	@PostMapping("pesquisar-aluno")
	public ModelAndView pesquisarAlunos(@RequestParam(required = false) String nome) {//usa-se o requestParam, pq está pegando o parametro da view(do formulário)
		ModelAndView mv = new ModelAndView();
		List<Aluno> listaAlunos;//recebe a lista de alunos pelo dao
		if(nome==null || nome.trim().isEmpty()) {
			listaAlunos = ar.findAll();
		}else {
			listaAlunos = ar.findByNomeContainingIgnoreCase(nome);
		}
		mv.addObject("ListaDeAlunos",listaAlunos);
		mv.setViewName("Aluno/pesquisa-resultado");
		return mv;
	}

}
