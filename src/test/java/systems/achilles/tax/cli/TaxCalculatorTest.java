package systems.achilles.tax.cli;

import org.junit.Test;
import systems.achilles.tax.cli.transaction.TaxType;
import systems.achilles.tax.cli.transaction.impl.SimpleTransactionRecordParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Unit test for Tax calculator.
 */
public class TaxCalculatorTest 
{
    private static final String path = "src/test/resources";

    @Test
    public void shouldHaveReturnTheTaxAmount() {
        String fileName = path + "/" + "transaction-30lines.csv";
        String[] args = {"CAPITOL_GAIN", "4527", fileName};
        TaxCalculator cal = new TaxCalculator(args[1], TaxType.valueOf(args[0]), new SimpleTransactionRecordParser());

        try {
            FileReader reader = new FileReader(fileName);
            double amount = cal.getAmountTotal(new BufferedReader(reader));
            System.out.println(amount);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    
   
}
