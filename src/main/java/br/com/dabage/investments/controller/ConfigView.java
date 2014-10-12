package br.com.dabage.investments.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;

import br.com.dabage.investments.config.StockTypeTO;
import br.com.dabage.investments.quote.GetQuotation;
import br.com.dabage.investments.repositories.CompanyRepository;
import br.com.dabage.investments.repositories.StockTypeRepository;

@Controller(value="configView")
@RequestScoped
public class ConfigView extends BasicView implements Serializable {

	/** */
	private static final long serialVersionUID = -2524943863550149439L;

	@Resource
	CompanyRepository companyRepository;

	@Resource
	GetQuotation getQuotation;

	@Resource
	StockTypeRepository stockTypeRepository;

	@Autowired
	MongoTemplate template;

	private List<StockTypeTO> stockTypes = new ArrayList<StockTypeTO>();

	private StockTypeTO stockType;

    public String init() {
        stockTypes = stockTypeRepository.findByRemoveDateNull();
    	return "config";
    }

	/**
	 * Prepare to add a new StockType
	 * @param event
	 */
	public void prepareStockType(ActionEvent event) {
		stockType = new StockTypeTO();
		stockType.setAddDate(new Date());
	}

	/**
	 * Add a new StockType (Ie: FII, Stock, Stock Option, etc...)
	 * @param event
	 */
	public void addStockType(ActionEvent event) {
		if (!checkStockType(stockType)) return;
		stockTypeRepository.save(stockType);
		stockTypes = stockTypeRepository.findByRemoveDateNull();
	}

	/**
	 * Edit a StockType
	 * @param event
	 */
	public void editStockType(RowEditEvent event) {
		StockTypeTO toEdit = (StockTypeTO) event.getObject();
		if (!checkStockType(toEdit)) return;
		stockTypeRepository.save(toEdit);
	}

	public void deleteStockType() {
		stockType.setRemoveDate(new Date());
		stockTypeRepository.save(stockType);
		stockTypes = stockTypeRepository.findByRemoveDateNull();
	}

	private boolean checkStockType(StockTypeTO stock) {
		if (stock == null) {
			addWarnMessage("Valores inválidos.");
			return false;
		}

		if (stock.getName() == null || stock.getName().isEmpty()) {
			addWarnMessage("Informe um nome.");
			return false;
		}
		stock.setName(stock.getName().toUpperCase());

		if (stock.getIrNormal() == null) {
			addWarnMessage("Informe a % de IR Normal.");
			return false;
		}

		if (stock.getIrDayTrade() == null) {
			addWarnMessage("Informe a % de IR Daytrade.");
			return false;
		}

		return true;
	}

	public List<StockTypeTO> getStockTypes() {
		return stockTypes;
	}

	public void setStockTypes(List<StockTypeTO> stockTypes) {
		this.stockTypes = stockTypes;
	}

	public StockTypeTO getStockType() {
		return stockType;
	}

	public void setStockType(StockTypeTO stockType) {
		this.stockType = stockType;
	}

}