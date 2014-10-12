package br.com.dabage.investments.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class BasicView implements Serializable {

	/** */
	private static final long serialVersionUID = 8359936032778514688L;

	public void addInfoMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

	public void addWarnMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

	public void addErrorMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

	public String getUserName() {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails.getUsername();
	}
}
