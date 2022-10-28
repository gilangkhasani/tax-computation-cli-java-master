package systems.achilles.tax.cli;

import static org.junit.Assert.*;

import com.sun.tools.javac.Main;
import org.junit.Test;
import systems.achilles.tax.cli.transaction.exception.MalformedRecordException;

import java.io.File;
import java.io.IOException;


public class AppTest {

	private static final String path = "src/test/resources";

	@Test
	public void shouldReturnFalseIfNoArugumentsPassed() {
		App app = new App();
		assertFalse(app.validateArguments(new String[0]));
	}

	@Test
	public void shouldReturnFalseIfArgumentsHaveOnly1() {
		App app = new App();
		String[] args = {"GST"};
		assertFalse(app.validateArguments(args));
	}

	@Test
	public void shouldReturnFalseIfArgumentsHaveOnly2() {
		App app = new App();
		String[] args = {"GST", "1234"};
		assertFalse(app.validateArguments(args));
	}

	@Test
	public void shouldReturnFalseIfFileNotExisting() {
		App app = new App();
		String[] args = {"GST", "1234", ""};
		assertFalse(app.validateArguments(args));
	}

	@Test
	public void shouldReturnFalseIfFileIsExisting() {
		App app = new App();
		String fileName = "transaction-30lines.csv";
		String[] args = {"GST", "1234", fileName};
		assertFalse(app.validateArguments(args));
	}

	@Test
	public void shouldReturnTrueIfFileIsExistingFromResourcesTestFile() {
		App app = new App();
		
		String fileName = path + "/" + "transaction-30lines.csv";
		String[] args = {"GST", "1234", fileName};
		assertTrue(app.validateArguments(args));
	}

	@Test
	public void shouldReturnFalseIfTaxTypeIsIncorrect() {
		App app = new App();

		String fileName = path + "/" + "transaction-30lines.csv";
		String[] args = {"GST2", "1234", fileName};
		assertFalse(app.validateArguments(args));
	}

	@Test
	public void shouldReturnZeroBecauseThereIsNoCustId() {
		App app = new App();

		String fileName = path + "/" + "transaction-30lines.csv";
		String[] args = {"GST", "1234", fileName};
		if(app.validateArguments(args)){
			try {
				double actual = app.run(args[0], args[1], args[2]);
				assertEquals("Amount is 0 because there is no custId in CSV data", 0, actual, 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void shouldHaveReturnUsingMain() {
		App app = new App();

		String fileName = path + "/" + "transaction-30lines.csv";
		String[] args = {"PAYROLL", "3327", fileName};
		app.main(args);
	}

	@Test
	public void shouldFileIsNotExistingUsingMain() {
		App app = new App();

		String fileName = path + "/" + "transaction-0lines.csv";
		String[] args = {"PAYROLL", "3327", fileName};
		app.main(args);
	}

	@Test
	public void shouldHaveReturnBecauseCustId() {
		App app = new App();

		String fileName = path + "/" + "transaction-30lines.csv";
		String[] args = {"PAYROLL", "3327", fileName};
		double custAmount = 751.83;
		double taxRate = 0.1;
		double expected = custAmount * taxRate;
		if(app.validateArguments(args)){
			try {
				double actual = app.run(args[0], args[1], args[2]);
				assertEquals("Amount is there because there is custId in CSV data", expected, actual, 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void shouldHaveReturnBecauseCustIdAndCalculateMultipleTimes() {
		App app = new App();

		String fileName = path + "/" + "transaction-30lines.csv";
		String[] args = {"CAPITOL_GAIN", "4527", fileName};
		double[] custAmount = {882.49, 643.82, };
		double taxRate = 0.1;
		double expected = 0.00;
		for(int i = 0; i < custAmount.length; i++) {
			expected += custAmount[i] * taxRate;
		}
		if(app.validateArguments(args)){
			try {
				double actual = app.run(args[0], args[1], args[2]);
				assertEquals("Amount is there because there is custId in CSV data", expected, actual, 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void shouldHaveReturnBecauseCustIdAndCalculateMultipleTimesForGST() {
		App app = new App();

		String fileName = path + "/" + "transaction-30lines.csv";
		String[] args = {"GST", "3077", fileName};
		double[] custAmount = {977.07, 647.43};
		double taxRate = 0.1;
		double expected = 0.00;
		for(int i = 0; i < custAmount.length; i++) {
			expected += custAmount[i] * taxRate;
		}
		if(app.validateArguments(args)){
			try {
				double actual = app.run(args[0], args[1], args[2]);
				assertEquals("Amount is there because there is custId in CSV data", expected, actual, 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void shouldHaveReturnBecauseCustIdAndCalculateForCompanyTax() {
		App app = new App();

		String fileName = path + "/" + "transaction-30lines.csv";
		String[] args = {"COMPANY_TAX", "3077", fileName};
		double[] custAmount = {447.87};
		double taxRate = 0.1;
		double expected = 0.00;
		for(int i = 0; i < custAmount.length; i++) {
			expected += custAmount[i] * taxRate;
		}
		if(app.validateArguments(args)){
			try {
				double actual = app.run(args[0], args[1], args[2]);
				assertEquals("Amount is there because there is custId in CSV data", expected, actual, 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void shouldHaveReturnBecauseCustIdAndCalculateMultipleTimesForLandTax() {
		App app = new App();

		String fileName = path + "/" + "transaction-30lines.csv";
		String[] args = {"LAND_TAX", "3201", fileName};
		double[] custAmount = {986.59};
		double taxRate = 0.1;
		double expected = 0.00;
		for(int i = 0; i < custAmount.length; i++) {
			expected += custAmount[i] * taxRate;
		}
		if(app.validateArguments(args)){
			try {
				double actual = app.run(args[0], args[1], args[2]);
				assertEquals("Amount is there because there is custId in CSV data", expected, actual, 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void shouldHaveZeroReturnBecauseThereIsNoTaxType() {
		App app = new App();

		String fileName = path + "/" + "transaction-30lines.csv";
		String[] args = {"PAYROLL", "4527", fileName};
		
		double expected = 0.00;
		
		if(app.validateArguments(args)){
			try {
				double actual = app.run(args[0], args[1], args[2]);
				assertEquals("Amount is there because there is custId in CSV data", expected, actual, 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void shouldHaveReturnBecauseCustIdAndCalculateMultipleTimesWithDifferentData() {
		App app = new App();

		String fileName = path + "/" + "transaction-30lines.csv";
		String[] args = {"CAPITOL_GAIN", "4527", fileName};
		double[] custAmount = {882.49, 643.82};
		double taxRate = 0.1;
		double expected = 0.00;
		for(int i = 0; i < custAmount.length; i++) {
			expected += custAmount[i] * taxRate;
		}
		if(app.validateArguments(args)){
			try {
				double actual = app.run(args[0], args[1], args[2]);
				assertEquals("Amount is there because there is custId in CSV data", expected, actual, 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
