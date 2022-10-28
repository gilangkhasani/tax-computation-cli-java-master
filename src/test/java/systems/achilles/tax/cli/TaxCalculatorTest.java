package systems.achilles.tax.cli;

import org.junit.Test;
import systems.achilles.tax.cli.transaction.TaxType;
import systems.achilles.tax.cli.transaction.impl.SimpleTransactionRecordParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;

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
            double actual = cal.getAmountTotal(new BufferedReader(reader));
            double expected = (882.49 * 0.1) + (643.82 * 0.1);
            assertEquals("Amount is there because there is custId in CSV data", expected, actual, 0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    
   
}
