package br.com.dabage.investments.carteira;

import java.util.ArrayList;
import java.util.List;

public class CarteiraItemTO {

	public CarteiraItemTO(String stock) {
		this.stock = stock;
		avgValue = 0D;
		quantity = 0L;
		negotiations = new ArrayList<NegotiationTO>();
	}

	private String stock;

	private Long quantity;

	private Double avgValue;

	private Double totalValue;

	private List<NegotiationTO> negotiations;

	public void addNegotiation(NegotiationTO neg) {
		negotiations.add(neg);

		Double oldTotal = avgValue * quantity;
		Double thisTotal = (neg.getValue() * neg.getQuantity());

		if (neg.getNegociationType().equals(NegotiationType.Compra)) {
			thisTotal += neg.getCosts();
			quantity += neg.getQuantity();
		} else {
			thisTotal -= neg.getCosts();
			quantity -= neg.getQuantity();
		}

		oldTotal += thisTotal;
		avgValue = oldTotal / quantity;
	}
	
	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getAvgValue() {
		return avgValue;
	}

	public void setAvgValue(Double avgValue) {
		this.avgValue = avgValue;
	}

	public Double getTotalValue() {
		totalValue = quantity * avgValue;
		return totalValue;
	}

	public List<NegotiationTO> getNegotiations() {
		return negotiations;
	}

	public void setNegotiations(List<NegotiationTO> negotiations) {
		this.negotiations = negotiations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
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
		CarteiraItemTO other = (CarteiraItemTO) obj;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CarteiraItemTO [stock=" + stock + ", quantity=" + quantity
				+ ", avgValue=" + avgValue + "]";
	}

}
