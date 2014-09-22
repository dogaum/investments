package br.com.dabage.investments.carteira;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.dabage.investments.repositories.AbstractDocument;
import br.com.dabage.investments.user.UserTO;

@Document(collection="carteiras")
public class CarteiraTO extends AbstractDocument {

	public CarteiraTO(String name) {
		this.name = name;
		this.createDate = new Date();
		this.status = CarteiraStatus.Ativa;
	}

	@DBRef
	private UserTO user;

	private Date createDate;

	private String name;

	@DBRef
	private List<NegotiationTO> negotiations;

	private Date deleteDate;

	private CarteiraStatus status;

	public UserTO getUser() {
		return user;
	}

	public void setUser(UserTO user) {
		this.user = user;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<NegotiationTO> getNegotiations() {
		return negotiations;
	}

	public void setNegotiations(List<NegotiationTO> negotiations) {
		this.negotiations = negotiations;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public CarteiraStatus getStatus() {
		return status;
	}

	public void setStatus(CarteiraStatus status) {
		this.status = status;
	}


}
