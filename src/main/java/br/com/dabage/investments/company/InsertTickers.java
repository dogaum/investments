package br.com.dabage.investments.company;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.dabage.investments.repositories.CompanyRepository;

@Component
public class InsertTickers {

	@Autowired
	CompanyRepository companyRepository;
	
	/**
	 * @param args
	 */
	public void run() {
			String uolTickers = "http://cotacoes.economia.uol.com.br/acoes-bovespa.html?exchangeCode=.BVSP&page=1&size=2000";
			boolean first = true;
			try {
				Document doc = Jsoup.connect(uolTickers).get();
				Element pagina = doc.getElementById("resultado-busca");
				Elements lis = pagina.getElementsByTag("li");
				for (Element li : lis) {
					if (first) {
						first = false;
						continue;
					}
					String[] infos = li.text().split(" ");
					String tickerFull = infos[infos.length-1];
					String ticker = tickerFull.substring(0, tickerFull.length() - 3); 
					String companyName = "";
					for (int i = 0; i < infos.length - 1; i++) {
						companyName += infos[i] + " ";
					}

					CompanyTO companyTO = new CompanyTO(ticker, companyName, companyName);
					companyRepository.save(companyTO);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

	}

}
