package com.sgbr.controller;

import com.sgbr.model.Pedido;

public class PedidoAlteradoEvent {
	
	private Pedido pedido;
	
	public PedidoAlteradoEvent(Pedido pedido){
		this.pedido = pedido;
	}
	
	public Pedido getPedido(){
		return pedido;
	}
}
