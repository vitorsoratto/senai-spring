package com.senai.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.senai.model.Produto;
import com.senai.repository.Produtos;

@Controller
@RequestMapping("/produto")
public class ProdutoController {
	public static final Logger logger = Logger.getLogger(ProdutoController.class.getName());

	@Autowired(required = true)
	private Produtos produtos;

	@RequestMapping("/consulta")
	public ModelAndView consulta() {
		ModelAndView mv = new ModelAndView("produtos.html");
		mv.addObject("produtos", produtosList());

		return mv;
	}

	@RequestMapping("/novo")
	public ModelAndView produtosCadatro() {
		ModelAndView mv = new ModelAndView("cadastroProdutos.html");
		mv.addObject(new Produto());

		return mv;
	}

	@RequestMapping("{id}")
	public ModelAndView edit(@PathVariable("id") Produto produto) {
		ModelAndView mv = new ModelAndView("cadastroProdutos.html");
		mv.addObject(produto);
		
		return mv;
	}
	
	@RequestMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Produto produto) {
		produtos.deleteById(produto.getId());

		return "redirect:/produto/consulta";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(Produto produto) {
		String retorno;
		if (produto.getId() == null) {
			retorno = "redirect:/produto/novo";
		} else {
			retorno = "redirect:/produto/consulta";
		}
		produtos.save(produto);
		
		
		return retorno;
	}

	public List<Produto> produtosList() {
		return produtos.findAll();
	}
}
