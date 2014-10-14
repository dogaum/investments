package br.com.dabage.investments.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Controller;

import br.com.dabage.investments.carteira.CarteiraItemTO;
import br.com.dabage.investments.carteira.CarteiraTO;
import br.com.dabage.investments.carteira.NegotiationTO;
import br.com.dabage.investments.carteira.NegotiationType;
import br.com.dabage.investments.company.CompanyTO;
import br.com.dabage.investments.repositories.CarteiraRepository;
import br.com.dabage.investments.repositories.CompanyRepository;
import br.com.dabage.investments.repositories.NegotiationRepository;
import br.com.dabage.investments.user.UserTO;

@Controller(value="carteiraView")
@RequestScoped
public class CarteiraView extends BasicView implements Serializable {

	/** */
	private static final long serialVersionUID = 6601802404818265880L;

	private List<CarteiraTO> carteiras;

	private CarteiraTO selectedCarteira;
	private List<CarteiraItemTO> carteiraItens;

	private String newName;

	private NegotiationTO negotiation;
	private List<SelectItem> negotiationTypes;

	private int firstCarteiraRing;

    @Resource
    CarteiraRepository carteiraRepository;

    @Resource
    NegotiationRepository negotiationRepository;

	@Resource
	CompanyRepository companyRepository;

	@PostConstruct
	public void prepare() {
		negotiationTypes = new ArrayList<SelectItem>();
		for (int i = 0; i < NegotiationType.values().length; i++) {
			SelectItem item = new SelectItem(NegotiationType.values()[i], NegotiationType.values()[i].name());
			negotiationTypes.add(item);
		}
	}
	
	public String init() {
		UserTO user = getUserLoggedIn();
		carteiras = carteiraRepository.findByUser(user);
		if (carteiras != null) {
			firstCarteiraRing = 0;
			selectedCarteira = carteiras.get(firstCarteiraRing);
			selectCarteira();
		}

		return "carteiras";
	}

	/**
	 * Criar uma nova Carteira
	 */
	public void create(ActionEvent event) {
		CarteiraTO carteira = new CarteiraTO(newName);
		carteira.setUser(getUserLoggedIn());

		carteiras.add(carteira);
		carteiraRepository.save(carteira);
		newName = "";
	}

	/**
	 * Apresentar detalhes de uma carteira
	 */
	public void selectCarteira() {
		if (selectedCarteira == null) {
			addWarnMessage("Selecione uma carteira.");
		} else {

			// Seta qual o Ring vai ficar selecionado no refresh da tela.
			firstCarteiraRing = carteiras.indexOf(selectedCarteira);

			selectedCarteira = carteiraRepository.findOne(selectedCarteira.getId());
			selectedCarteira.setNegotiations(negotiationRepository.findByCarteira(selectedCarteira));
			if (selectedCarteira.getNegotiations() == null) {
				selectedCarteira.setNegotiations(new ArrayList<NegotiationTO>());
			}

			carteiraItens = new ArrayList<CarteiraItemTO>();
			for (NegotiationTO neg : selectedCarteira.getNegotiations()) {
				if (neg.getRemoveDate() != null) {
					continue;
				}
				CarteiraItemTO item = new CarteiraItemTO(neg.getStock());
				if (carteiraItens.contains(item)) {
					int idx = carteiraItens.indexOf(item);
					item = carteiraItens.get(idx);
				} else {
					carteiraItens.add(item);
				}
				item.addNegotiation(neg);
			}
		}
	}

	/**
	 * Prepara insercao de negociacao
	 * @param event
	 */
	public void prepareNegotiation(ActionEvent event) {
		negotiation = new NegotiationTO();
		negotiation.setAddDate(new Date());
	}

	public void addNegotiation(ActionEvent event) {
		if (!checkNegotiation(negotiation)) return; 

		negotiation.setCarteira(selectedCarteira);
		negotiationRepository.save(negotiation);
		negotiation = new NegotiationTO();
		this.selectCarteira();
	}

	public void editNegotiation(RowEditEvent event) {

		NegotiationTO neg = (NegotiationTO) event.getObject();
		if (!checkNegotiation(neg)) return;

		neg.setCarteira(selectedCarteira);
		negotiationRepository.save(neg);
		this.selectCarteira();
	}

	/**
	 * Validates a Negotiation insert/update
	 * @param neg
	 * @return
	 */
	private boolean checkNegotiation(NegotiationTO neg) {
		if (neg == null) {
			addWarnMessage("Valores invalidos.");
			return false;
		}

		if (neg.getDtNegociation() == null) {
			addWarnMessage("Informe uma data.");
			return false;
		}
		
		if (neg.getQuantity() == 0) {
			addWarnMessage("Quantidade deve ser maior de 0 (zero)");
			return false;
		}
		neg.setStock(neg.getStock().toUpperCase());

		if (neg.getStock().length() < 5) {
			addWarnMessage("Código inválido: " + neg.getStock());
			return false;
		} else {
			CompanyTO company = companyRepository.findByTicker(neg.getStock());
			if (company == null) {
				addWarnMessage("Código inválido: " + neg.getStock());
				return false;
			}
		}
	
		return true;
	}

	public void deleteNegotiation() {
		negotiation.setRemoveDate(new Date());
		negotiationRepository.save(negotiation);
		selectCarteira();
	}

	public List<CarteiraTO> getCarteiras() {
		return carteiras;
	}

	public void setCarteiras(List<CarteiraTO> carteiras) {
		this.carteiras = carteiras;
	}

	public CarteiraTO getSelectedCarteira() {
		return selectedCarteira;
	}

	public void setSelectedCarteira(CarteiraTO selectedCarteira) {
		this.selectedCarteira = selectedCarteira;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public List<CarteiraItemTO> getCarteiraItens() {
		return carteiraItens;
	}

	public void setCarteiraItens(List<CarteiraItemTO> carteiraItens) {
		this.carteiraItens = carteiraItens;
	}

	public NegotiationTO getNegotiation() {
		return negotiation;
	}

	public void setNegotiation(NegotiationTO negotiation) {
		this.negotiation = negotiation;
	}

	public List<SelectItem> getNegotiationTypes() {
		return negotiationTypes;
	}

	public void setNegotiationTypes(List<SelectItem> negotiationTypes) {
		this.negotiationTypes = negotiationTypes;
	}

	public int getFirstCarteiraRing() {
		return firstCarteiraRing;
	}

	public void setFirstCarteiraRing(int firstCarteiraRing) {
		this.firstCarteiraRing = firstCarteiraRing;
	}

}