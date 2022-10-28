package systems.achilles.tax.cli.transaction;

import org.junit.Test;

import systems.achilles.tax.cli.transaction.exception.MalformedRecordException;
import systems.achilles.tax.cli.transaction.impl.SimpleTransactionRecordParser;

import static org.junit.Assert.assertEquals;

public class TransactionRecordParserTest {

	@Test
	public void shouldReturnTheTaxAmount() {
		SimpleTransactionRecordParser simpleTransactionRecordParser = new SimpleTransactionRecordParser();
		String taxRecord = "4527,2010/11/aaabababb-62312,2021-11-24T17:32:57.534,882.49,CAPITOL_GAIN";
		try {
			double actual = simpleTransactionRecordParser.getTaxAmountForUser(taxRecord, "4527", TaxType.valueOf("CAPITOL_GAIN"));
			double expected = 882.49 * 0.1;
			assertEquals("Amount is there because there is custId in CSV data", expected, actual, 0);
		} catch (MalformedRecordException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = MalformedRecordException.class)
	public void shouldReturnTheTransactionAmountAsZeroWithNotNumberAmt() throws MalformedRecordException {
		SimpleTransactionRecordParser simpleTransactionRecordParser = new SimpleTransactionRecordParser();
		String taxRecord = "4527,2010/11-aaabababb-62312,2021-11-24T17:32:57.534,882.49,CAPITOL_GAIN";
		simpleTransactionRecordParser.getTaxAmountForUser(taxRecord, "4527", TaxType.valueOf("CAPITOL_GAIN"));
	}
}
