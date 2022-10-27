package systems.achilles.tax.cli.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandTax extends Tax {

    private double taxRate = 0.1;
}
