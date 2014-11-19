package br.com.dabage.investments.company;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.dabage.investments.repositories.CompanyRepository;


@ContextConfiguration
(
  {
   "file:src/main/webapp/WEB-INF/mongo-config.xml",
   "file:src/main/webapp/WEB-INF/spring-config.xml",
   "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml"
  }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyRepositoryTest {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	InsertFIITickers insertFIITickers;

	@Autowired
	InsertTickers insertTickers;
	
	@Test
	public void testFindAll() {
		List<CompanyTO> all = companyRepository.findAll();
		for (CompanyTO companyTO : all) {
			System.out.println(companyTO.getTicker() + "-" + companyTO.getId());
		}
	}

	@Test
	public void testInsertFIICompanyTO() {
		//insertFIITickers.run();
	}

	@Test
	public void testInsertCompanyTO() {
		//insertTickers.run();
	}
	
}
