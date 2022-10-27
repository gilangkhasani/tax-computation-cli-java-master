package systems.achilles.tax.cli.transaction;

import org.junit.Test;

import systems.achilles.tax.cli.transaction.exception.MalformedRecordException;
import systems.achilles.tax.cli.transaction.impl.SimpleTransactionRecordParser;

public class TransactionRecordParserTest {

	@Test
	public void shouldReturnTheTaxAmount() {
		SimpleTransactionRecordParser simpleTransactionRecordParser = new SimpleTransactionRecordParser();
		String taxRecord = "4527,2010/11/aaabababb-62312,2021-11-24T17:32:57.534,882.49,CAPITOL_GAIN";
		try {
			simpleTransactionRecordParser.getTaxAmountForUser(taxRecord, "4527", TaxType.valueOf("CAPITOL_GAIN"));
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
