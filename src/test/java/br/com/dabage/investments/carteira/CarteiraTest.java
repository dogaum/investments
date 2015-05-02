package br.com.dabage.investments.carteira;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.dabage.investments.repositories.CarteiraRepository;
import br.com.dabage.investments.repositories.IncomeRepository;
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

	@Resource
	IncomeRepository incomeRepository;

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

	@Test
	public void importIncomes() throws IOException {
	    File myFile = new File("C://fii/FIIP11B.xlsx");
        FileInputStream fis = new FileInputStream(myFile);

        // Finds the workbook instance for XLSX file
        XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
       
        // Return first sheet from the XLSX workbook
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
       
        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = mySheet.iterator();

        CarteiraTO carteira = carteiraRepository.findAll().get(0);

        // Traversing over each row of XLSX file
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            // For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();

            IncomeTO income = new IncomeTO();
            income.setAddDate(new Date());
            income.setIdCarteira(carteira.getId());
            income.setType(IncomeTypes.INCOME);

            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();

                switch (cell.getColumnIndex()) {
                case 0:
                    income.setStock(cell.getStringCellValue());
                    break;
                case 1:
                    income.setIncomeDate(cell.getDateCellValue());
                    break;
                case 2:
                	income.setValue(cell.getNumericCellValue());
                    break;
                default :
             
                }
            }
            if (income.getValue() == null) {
            	continue;
            }
            incomeRepository.save(income);
            carteira.getIncomes().add(income);
    		carteiraRepository.save(carteira);
            System.out.println(income);
        }
	}
}
