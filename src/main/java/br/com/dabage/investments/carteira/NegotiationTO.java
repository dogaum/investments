package br.com.dabage.investments.carteira;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.dabage.investments.config.StockTypeTO;
import br.com.dabage.investments.repositories.AbstractDocument;

@Document(collection="negotiations")
public class NegotiationTO extends AbstractDocument implements Comparable<NegotiationTO> {

	private String stock;

	private Double value;

	private Date dtNegociation;

	private Long quantity;

	private NegotiationType negociationType;

	private Double costs;

	/** Data de criação do item; */
	private Date addDate;

	/** Data de remoção do item. */
	private Date removeDate;

	@DBRef
	private CarteiraTO carteira;

	@DBRef
	private StockTypeTO stockType;

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Date getDtNegociation() {
		return dtNegociation;
	}

	public void setDtNegociation(Date dtNegociation) {
		this.dtNegociation = dtNegociation;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public NegotiationType getNegociationType() {
		return negociationType;
	}

	public void setNegociationType(NegotiationType negociationType) {
		this.negociationType = negociationType;
	}

	public Double getCosts() {
		return costs;
	}

	public void setCosts(Double costs) {
		this.costs = costs;
	}

	public CarteiraTO getCarteira() {
		return carteira;
	}

	public void setCarteira(CarteiraTO carteira) {
		this.carteira = carteira;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Date getRemoveDate() {
		return removeDate;
	}

	public void setRemoveDate(Date removeDate) {
		this.removeDate = removeDate;
	}

	public StockTypeTO getStockType() {
		return stockType;
	}

	public void setStockType(StockTypeTO stockType) {
		this.stockType = stockType;
	}

	@Override
	public String toString() {
		return "Movimentação [Carteira: " + carteira.getName() + " Código: " + stock + " Valor: " + value
				+ " Data: " + dtNegociation + " Qtde: " + quantity
				+ " Tipo: " + negociationType + " Custos: " + costs
				+ "]";
	}

	@Override
	public int compareTo(NegotiationTO o) {
        int lastCmp = dtNegociation.compareTo(o.dtNegociation);
        return (lastCmp != 0 ? lastCmp : dtNegociation.compareTo(o.dtNegociation));
	}
}
