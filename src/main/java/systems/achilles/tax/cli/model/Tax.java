package systems.achilles.tax.cli.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Pattern;
import systems.achilles.tax.cli.transaction.TaxType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tax {

    private String custId;
    private String commercialInvoiceNumber;
    private LocalDateTime currentDateTime;
    private TaxType taxTypeCustId;

}
