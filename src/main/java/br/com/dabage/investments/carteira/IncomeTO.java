package br.com.dabage.investments.carteira;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.dabage.investments.repositories.AbstractDocument;

@Document(collection="incomes")
public class IncomeTO extends AbstractDocument {

	@Indexed
	private String stock;

	private Double value;

	private String type;

	private Date incomeDate;

	private Date addDate;

	@Indexed
	private BigInteger idCarteira;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getIncomeDate() {
		return incomeDate;
	}

	public void setIncomeDate(Date incomeDate) {
		this.incomeDate = incomeDate;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public BigInteger getIdCarteira() {
		return idCarteira;
	}

	public void setIdCarteira(BigInteger idCarteira) {
		this.idCarteira = idCarteira;
	}

	@Override
	public String toString() {
		return "IncomeTO [stock=" + stock + ", value=" + value + ", type="
				+ type + ", incomeDate=" + incomeDate + ", addDate=" + addDate
				+ "]";
	}

}
