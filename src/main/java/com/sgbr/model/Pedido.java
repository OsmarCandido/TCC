package com.sgbr.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPedido;
	private Date dataCriacao;
	private Date encerramento;
	private String observacao;
	private boolean comissionado = true;
	private BigDecimal valorComissao = BigDecimal.ZERO;
	private BigDecimal valorDesconto = BigDecimal.ZERO;
	private BigDecimal subTotal = BigDecimal.ZERO;
	private BigDecimal valorTotal = BigDecimal.ZERO;
	private StatusPedido status = StatusPedido.ABERTO;
	private FormaPagamento pagamento;
	private Funcionario funcionario;
	private Funcionario operador;
	private Caixa caixa;
	private Mesa mesa;
	
	private List<ItemPedido> itens = new ArrayList<>();

	@Id
	@GeneratedValue
	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false)
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "encerramento")
	public Date getEncerramento() {
		return encerramento;
	}

	public void setEncerramento(Date encerramento) {
		this.encerramento = encerramento;
	}

	@Column(columnDefinition = "text")
	public String getObservacao() {
		return observacao;
	}

	@NotNull
	@Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@NotNull
	@Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "pagamento", nullable = false, length = 20)
	public FormaPagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(FormaPagamento pagamento) {
		this.pagamento = pagamento;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "mesa", nullable = false)
	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "operador", nullable = false)
	public Funcionario getOperador() {
		return operador;
	}

	public void setOperador(Funcionario operador) {
		this.operador = operador;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "caixa", nullable = false)
	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "funcionario", nullable = false)
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPedido == null) ? 0 : idPedido.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (idPedido == null) {
			if (other.idPedido != null)
				return false;
		} else if (!idPedido.equals(other.idPedido))
			return false;
		return true;
	}

	@Transient
	public boolean isNovo() {
		return getIdPedido() == null;
	}

	@Transient
	public boolean isExistente() {
		return !isNovo();
	}

	@Transient
	public boolean isValorTotalNegativo() {
		return this.getValorTotal().compareTo(BigDecimal.ZERO) < 0;
	}

	@Transient
	public boolean isFechado() {
		return StatusPedido.FECHADO.equals(this.getStatus());
	}

	@Transient
	public boolean isCancelavel() {
		return this.isExistente() && !this.isCancelado();
	}

	@Transient
	private boolean isCancelado() {
		return StatusPedido.CANCELADO.equals(this.getStatus());
	}

	@Transient
	public boolean isNaoCancelavel() {
		return !this.isCancelavel();
	}

	@NotNull
	@Column(name = "valor_comissao", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorComissao() {
		return valorComissao;
	}

	public void setValorComissao(BigDecimal valorComissao) {
		this.valorComissao = valorComissao;
	}

	@NotNull
	@Column(name = "valor_desconto", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	@Transient
	public BigDecimal getValorSubtotal() {
		return this.calcularSubtotal();
	}
	
	public BigDecimal calcularSubtotal(){
		BigDecimal subtotal = BigDecimal.ZERO;
		for (ItemPedido item : this.getItens()) {
			if (item.getProduto() != null && item.getProduto().getIdProduto() != null) {
				subtotal = subtotal.add(item.getValorTotal());
			}
		}
		return subtotal;
	}
	
	public void calcularComissao(){

		if(this.isComissionado())
			this.setValorComissao(this.getValorSubtotal().multiply(BigDecimal.valueOf(0.10)));
		else {
			this.setValorComissao(BigDecimal.ZERO);
		}
	}

	public void recalcularValorTotal() {
		BigDecimal total = BigDecimal.ZERO;
		total = total.add(this.getValorSubtotal());
		total = total.add(this.getValorComissao()).subtract(this.getValorDesconto());
		this.setValorTotal(total);
	}

	public void adicionarItemVazio() {
		if (this.isAberto()) {
			Produto produto = new Produto();

			ItemPedido item = new ItemPedido();
			item.setQuantidade(1);
			item.setProduto(produto);
			item.setPedido(this);

			this.getItens().add(0, item);
		}
	}

	@Transient
	public boolean isAberto() {
		return StatusPedido.ABERTO.equals(this.getStatus());
	}

	public void removerItemVazio() {
		ItemPedido primeiroItem = this.getItens().get(0);
		
		if(primeiroItem != null && primeiroItem.getProduto().getIdProduto() == null){
			this.getItens().remove(0);
			
		}
		
	}
	
	@Transient
	public boolean isNaoFechavel() {
		return !this.isFechavel();
	}
	
	@Transient
	private boolean isFechavel() {
		return this.isExistente() && this.isAberto();
	}
	
	@Transient
	public boolean isAlteravel() {
		return this.isAberto();
	}
	
	@Transient
	public boolean isNaoAlteravel() {
		return !this.isAlteravel();
	}
	
	@Transient
	public boolean isAguardandoPagamento(){
		return this.isExistente() && this.isFechado();
	}
	
	@Transient
	public boolean isNaoAguardandoPagamento(){
		return !isAguardandoPagamento();
	}
	
	@Transient
	public boolean isComissionado() {
		return comissionado;
	}

	public void setComissionado(boolean comissionado) {
		this.comissionado = comissionado;
	}
}
