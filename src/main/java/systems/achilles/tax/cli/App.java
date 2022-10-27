package systems.achilles.tax.cli;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import systems.achilles.tax.cli.transaction.impl.SimpleTransactionRecordParser;
import systems.achilles.tax.cli.transaction.TaxType;

/**
 * 
 *
 */
public class App {
	public static void main(String[] args) {
		App app = new App();
		if (app.validateArguments(args)) {
			String taxType = args[0];
			String userId = args[1];

			try {

				double taxAmt = app.run(args[0], args[1], args[2]);
				System.out.println(String.format("For tax %s, customer %s has declared $%s", taxType, userId, taxAmt));
			} catch (IOException e) {
				System.out.println(
						String.format("Error occurred while try to parse and calculate the tax for tax %s, customer %s",
								taxType, userId));
				System.exit(1);
			}
		}
	}

	double run(String taxType, String userId, String file) throws IOException {
		FileReader reader = new FileReader(file);

		TaxCalculator cal = new TaxCalculator(userId, TaxType.valueOf(taxType), new SimpleTransactionRecordParser());
		return cal.getAmountTotal(new BufferedReader(reader));

	}

	boolean validateArguments(String[] args) {
		if (args.length != 3) {
			printUsage();
			return false;
		}

		if (!TaxType.contains(args[0])) {
			printUsage();
			return false;
		}

		Path path = Paths.get(args[2]);

		if (!Files.exists(path) || Files.isDirectory(path)) {
			System.out.println(String.format("The file %s is not existing", args[2]));
			printUsage();
			return false;
		}

		return true;
	}

	private void printUsage() {
		String usage = "Tax Report CLI.\n" + "\n" + "Usage:\n"
				+ "  tcli (GST | PAYROLL | COMPANY_TAX | LAND_TAX | CAPITOL_GAIN) <user_id> <tax_transaction_file>\n\n"
				+ "Example:\n" + "  tcli GST 123 filename.csv";

		System.out.println(usage);

	}
}
