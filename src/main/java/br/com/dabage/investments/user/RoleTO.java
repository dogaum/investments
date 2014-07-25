package br.com.dabage.investments.user;

import org.springframework.data.mongodb.core.mapping.Document;

import br.com.dabage.investments.repositories.AbstractDocument;

@Document(collection="role")
public class RoleTO extends AbstractDocument {

	String name;

	String description;

	public RoleTO() { }

	public RoleTO(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Role [name=" + name + ", description=" + description + "]";
	}

}
