package systems.achilles.tax.cli.transaction;

import systems.achilles.tax.cli.transaction.exception.MalformedRecordException;

public interface TransactionRecordParser {
	double getTaxAmountForUser(String taxRecord, String userId, TaxType taxType) throws MalformedRecordException;
}
