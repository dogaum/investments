package br.com.dabage.investments.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;

import br.com.dabage.investments.config.StockTypeTO;
import br.com.dabage.investments.quote.GetQuotation;
import br.com.dabage.investments.repositories.CompanyRepository;
import br.com.dabage.investments.repositories.RoleRepository;
import br.com.dabage.investments.repositories.StockTypeRepository;
import br.com.dabage.investments.user.RoleTO;
import br.com.dabage.investments.user.UserTO;

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

	@Resource
	RoleRepository roleRepository;
	
	private List<StockTypeTO> stockTypes = new ArrayList<StockTypeTO>();

	private StockTypeTO stockType;

	private UserTO user;

	private List<UserTO> users = new ArrayList<UserTO>();

	private DualListModel<RoleTO> roles;

	public String init() {
		// Stock Types
		stockTypes = stockTypeRepository.findByRemoveDateNull();

		// All Users
		users = userRepository.findAll();

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

	/**
	 * Validates a StockType
	 * @param stock
	 * @return
	 */
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

	/** Prepare Roles to add */
	private void prepareRoles() {
        // Roles
        List<RoleTO> rolesSource = roleRepository.findAll();
        List<RoleTO> rolesTarget = new ArrayList<RoleTO>();
        roles = new DualListModel<RoleTO>(rolesSource, rolesTarget);		
	}

	/**
	 * Prepare to add a new User
	 * @param event
	 */
	public void prepareUser(ActionEvent event) {
		prepareRoles();

		user = new UserTO();
		user.setAddDate(new Date());
		user.setActivated(Boolean.TRUE);
		user.setActivateDate(new Date());
	}

	/**
	 * Add a new User
	 * @param event
	 */
	public void addUser(ActionEvent event) {
		if (!checkUser(user)) return;
		userRepository.save(user);
		users = userRepository.findAll();
	}

	/**
	 * Edit a User
	 * @param event
	 */
	public void editUser(ActionEvent event) {
		if (!checkUser(user)) return;
		userRepository.save(user);
	}

	public void deleteUser() {
		user.setRemoveDate(new Date());
		userRepository.save(user);
		users = userRepository.findAll();
	}

	/**
	 * Validates a new User
	 * @param user
	 * @return
	 */
	private boolean checkUser(UserTO user) {
		if (user == null) {
			addWarnMessage("Valores inválidos.");
			return false;
		}

		if (user.getName() == null || user.getName().isEmpty()) {
			addWarnMessage("Informe um nome.");
			return false;
		}
		user.setName(user.getName().toUpperCase());

		if (user.getSurname() == null || user.getSurname().isEmpty()) {
			addWarnMessage("Informe um sobrenome.");
			return false;
		}

		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			addWarnMessage("Informe um email valido.");
			return false;
		}

		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			addWarnMessage("Informe uma senha valida.");
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

	public UserTO getUser() {
		return user;
	}

	public void setUser(UserTO user) {
		this.user = user;
	}

	public List<UserTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserTO> users) {
		this.users = users;
	}

	public DualListModel<RoleTO> getRoles() {
		return roles;
	}

	public void setRoles(DualListModel<RoleTO> roles) {
		this.roles = roles;
	}

}