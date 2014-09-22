package br.com.dabage.investments.controller;


import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import br.com.dabage.investments.news.NewsTO;
import br.com.dabage.investments.quote.GetQuotation;
import br.com.dabage.investments.repositories.CompanyRepository;
import br.com.dabage.investments.repositories.NewsRepository;

@Controller(value="newsView")
@RequestScoped
public class NewsView implements Serializable {

	/** */
	private static final long serialVersionUID = -2524943863550149439L;

	@Resource
	CompanyRepository companyRepository;

	@Resource
	GetQuotation getQuotation;

	@Resource
	NewsRepository newsRepository;

	private List<NewsTO> news;

	private NewsTO selectedNews;

	@PostConstruct
	public void init() {
		news = newsRepository.findAll(new Sort(Sort.Direction.DESC, "newsDate"));
	}

    public List<NewsTO> getNews() {
		return news;
	}

	public void setNews(List<NewsTO> news) {
		this.news = news;
	}

	public NewsTO getSelectedNews() {
		return selectedNews;
	}

	public void setSelectedNews(NewsTO selectedNews) {
		this.selectedNews = selectedNews;
	}

	public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}