package br.com.dabage.investments.carteira;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.dabage.investments.repositories.CarteiraRepository;
import br.com.dabage.investments.repositories.NegotiationRepository;

@ContextConfiguration
(
  {
   "file:src/main/webapp/WEB-INF/mongo-config.xml",
   "file:src/main/webapp/WEB-INF/spring-config.xml",
   "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml"
  }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class CarteiraTest {

	@Autowired
	NegotiationRepository negotiationRepository;

	@Autowired
	CarteiraRepository carteiraRepository;

	@Autowired
	PortfolioService portfolioService;
	
	@Test
	public void testFindByIdCarteiraAndDtNegotiationBetween() {
		CarteiraTO carteiraTO = carteiraRepository.findAll().get(0);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -16);

		Date from  = cal.getTime();
		Date to = null;
		List<NegotiationTO> negs = portfolioService.getSellNegotiationsByFilter(Collections.singletonList(carteiraTO), from, to);
		for (NegotiationTO negotiationTO : negs) {
			System.out.println(negotiationTO);
		}
	}
}
