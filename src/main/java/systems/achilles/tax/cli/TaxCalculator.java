package systems.achilles.tax.cli;

import java.io.BufferedReader;
import java.io.IOException;

import systems.achilles.tax.cli.transaction.TaxType;
import systems.achilles.tax.cli.transaction.TransactionRecordParser;
import systems.achilles.tax.cli.transaction.exception.MalformedRecordException;

public class TaxCalculator {

	private String userId;
	private TaxType taxType;
	private TransactionRecordParser parser;

	public TaxCalculator(String userId, TaxType taxType, TransactionRecordParser parser) {
		this.userId = userId;
		this.taxType = taxType;
		this.parser = parser;
	}

	public double getAmountTotal(BufferedReader reader) {
		String line;
		double total = 0.0;

		try {
			while ((line = reader.readLine()) != null) {
				total = total + this.parser.getTaxAmountForUser(line, this.userId, this.taxType);
			}

		} catch (MalformedRecordException | IOException e) {
			total = 0.0;
			e.printStackTrace();
		}

		return total;
	}

}
