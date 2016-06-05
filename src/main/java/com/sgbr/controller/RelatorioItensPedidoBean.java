package com.sgbr.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.Session;

import com.sgbr.util.report.ExecutorRelatorio;


@Named
@ViewScoped
public class RelatorioItensPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;


	private Double codigoPedido;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	public void emitir(){
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("codigo_pedido", this.codigoPedido);
			
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/itenspedido.jasper",
				this.response, parametros, "Itens Pedido.pdf");
		

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		facesContext.responseComplete();
	}
	@NotNull
	public Double getCodigoPedido() {
		return codigoPedido;
	}

	public void setCodigoPedido(Double codigoPedido) {
		this.codigoPedido = codigoPedido;
	}
}
