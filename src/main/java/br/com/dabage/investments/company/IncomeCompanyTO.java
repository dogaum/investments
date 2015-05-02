package br.com.dabage.investments.company;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.dabage.investments.repositories.AbstractDocument;

@Document(collection="incomescompanies")
public class IncomeCompanyTO extends AbstractDocument implements Comparable<IncomeCompanyTO> {

	@Indexed
	private String stock;

	private Double value;

	private Date incomeDate;

	private Integer yearMonth;
	
	@Indexed
	private BigInteger idCompany;

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

	public Date getIncomeDate() {
		return incomeDate;
	}

	public void setIncomeDate(Date incomeDate) {
		this.incomeDate = incomeDate;
	}

	public BigInteger getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(BigInteger idCompany) {
		this.idCompany = idCompany;
	}

	public Integer getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Integer yearMonth) {
		this.yearMonth = yearMonth;
	}

	@Override
	public String toString() {
		return "IncomeCompanyTO [stock=" + stock + ", value=" + value
				+ ", incomeDate=" + incomeDate + ", idCompany=" + idCompany
				+ "]";
	}

	@Override
	public int compareTo(IncomeCompanyTO o) {
		return this.incomeDate.compareTo(o.getIncomeDate());
	}

}
