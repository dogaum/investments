package br.com.dabage.investments.news;

import java.util.List;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.dabage.investments.company.CompanyTO;
import br.com.dabage.investments.mail.SendMailSSL;
import br.com.dabage.investments.quote.GetQuotation;
import br.com.dabage.investments.repositories.CompanyRepository;
import br.com.dabage.investments.repositories.NewsRepository;

@Component
public class CheckNews {

	@Autowired
	private GetQuotation getQuotation;

	@Resource
	private NewsRepository newsRepository;

	@Resource
	private CompanyRepository companyRepository;

	public int run(String query) {

		int qtyNews = 0;

		Document doc;
		String endFII = "http://www.bmfbovespa.com.br/Agencia-Noticias/ListarNoticias.aspx?idioma=pt-br&q=" + query + "&tipoFiltro=0";
		String prefix = "http://www.bmfbovespa.com.br/Agencia-Noticias/";
		try {
			doc = Jsoup.connect(endFII).get();
			Element pagina = doc.getElementById("linksNoticias");
			if (pagina != null) {
				Elements links = pagina.getElementsByTag("li");
				for (Element link : links) {
					String href = link.getElementsByTag("a").get(0).attr("href");
					String newsName = link.text();

					doc = Jsoup.connect(prefix + href).get();
					Element news = doc.getElementById("contentNoticia");

					NewsTO newsBean = new NewsTO();
					newsBean.setNewsHeader(newsName.substring(19));
					newsBean.setNewsDate(getDateFromNews(newsName));
					newsBean.setStockType(getStockType(newsBean.getNewsHeader()));
					newsBean.setTicker(getStockTicker(newsBean.getNewsHeader()));
					newsBean.setNews(news.text());
					newsBean.setNewsHref(prefix + href);

					if(insertNews(newsBean) && newsBean.getNewsHeader().toLowerCase().contains("fii")) {
						qtyNews++;
						SendMailSSL.send(newsName, getQuotationsByPrefix(newsBean.getTicker()) + news.text());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qtyNews;
	}

	private boolean insertNews(NewsTO newsTO) {
		NewsTO obj = newsRepository.findByNewsHeaderAndNewsDate(newsTO.getNewsHeader(), newsTO.getNewsDate());
		if (obj == null) {
			newsRepository.save(newsTO);
			return true;
		}

		return false;
	}

	private String getDateFromNews(String newsHeader) {
		String dateStr = newsHeader.substring(0, 16);
		return dateStr;
	}

	private String getStockType(String newsHeader) {
		if (newsHeader.substring(0, 3).equals("FII")) {
			return "FII";
		}
		return "ACOES";
	}

	private String getStockTicker(String newsHeader) {
		String ticker = "";
		int initialIndex = newsHeader.indexOf("(");
		ticker = newsHeader.substring(initialIndex+1, initialIndex + 5);

		return ticker;
	}

	private String getQuotationsByPrefix(String prefix) {
		StringBuffer result = new StringBuffer();
		result.append("Cotacoes:\n");

		List<CompanyTO> cursor = companyRepository.findByPrefix(prefix);
		for (CompanyTO company : cursor) {
			String ticker = company.getTicker();
			String lastQuotation = getQuotation.getLastQuotation(ticker);
			result.append(ticker);
			result.append(" : ");
			result.append(lastQuotation);
			result.append("\n");
		}
		result.append("\n");
		result.append("\n");
		result.append("\n");
		return result.toString();
	}
}