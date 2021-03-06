package com.sgbr.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@RequestScoped
public class Seguranca {

	public String getNomeFuncionario() {
		String nome = null;

		FuncionarioSistema funcionarioLogado = getFuncionarioLogado();

		if (funcionarioLogado != null) {
			nome = funcionarioLogado.getFuncionario().getNome();
		}
		return nome;

	}

	private FuncionarioSistema getFuncionarioLogado() {
		FuncionarioSistema funcionario = null;

		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();

		if (auth != null && auth.getPrincipal() != null) {
			funcionario = (FuncionarioSistema) auth.getPrincipal();
		}
		return funcionario;

	}

}
