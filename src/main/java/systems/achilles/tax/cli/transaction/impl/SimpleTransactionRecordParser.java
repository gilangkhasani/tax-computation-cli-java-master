package systems.achilles.tax.cli.transaction.impl;

import systems.achilles.tax.cli.model.*;
import systems.achilles.tax.cli.transaction.TaxType;
import systems.achilles.tax.cli.transaction.TransactionRecordParser;
import systems.achilles.tax.cli.transaction.exception.MalformedRecordException;
import systems.achilles.tax.cli.utils.Constants;
import systems.achilles.tax.cli.utils.Validation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class SimpleTransactionRecordParser  implements TransactionRecordParser {



	/**
	 * Parse the record string, and return the tax amount for given userId, and tax type
	 * The record is a string separated by comma, such as:
	 * 123, 2021/04/customer1-12212, 2021-04-29T13:15:54, 10.00, GST
	 * @param taxRecord, not null
	 * @return the tax amount in the record
	 * @throws MalformedRecordException
	 */
	public double getTaxAmountForUser(String taxRecord, String userId, TaxType taxType) throws MalformedRecordException{
		Validation validation = new Validation();
		if(validation.checkRecord(taxRecord)) {
			String[] taxRecordArr = validation.getRecord(taxRecord);
			TaxType taxTypeCustId = TaxType.valueOf(taxRecordArr[4]);
			boolean matchFound = validation.validRegex(Constants.REGEX_PATTERN_INVOICE_NUMBER, taxRecordArr[1]);
			if (matchFound) {
				Tax tax = new Tax();
				tax.setCustId(taxRecordArr[0]);
				tax.setCommercialInvoiceNumber(taxRecordArr[1]);
				tax.setCurrentDateTime(LocalDateTime.parse(taxRecordArr[2]));
				tax.setTaxTypeCustId(taxTypeCustId);

				Double amount = Double.parseDouble(taxRecordArr[3]);

				double total = 0.00;

				if (taxType.equals(tax.getTaxTypeCustId()) && userId.equals(tax.getCustId())) {
					try {
						Class className = Class.forName(taxTypeCustId.getTaxClass().getName());
						Method taxRate = className.getDeclaredMethod("getTaxRate", new Class[]{});
						Object classObject = className.getDeclaredConstructor().newInstance();
						total = amount * (double) taxRate.invoke(classObject);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					return total;
				}
			} else {
				System.out.println("Wrong Format : " + taxRecordArr[1]);
				throw new MalformedRecordException("Wrong Format : " + taxRecordArr[1]);

			}
		}
		return 0.00;
	}

}
