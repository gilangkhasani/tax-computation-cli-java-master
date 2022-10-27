package systems.achilles.tax.cli.transaction;

import systems.achilles.tax.cli.model.*;

public enum TaxType {
	GST("GST", Gst.class), PAYROLL("PAYROLL", Payroll.class),
	COMPANY_TAX("COMPANY_TAX", CompanyTax.class),
	LAND_TAX("LAND_TAX", LandTax.class), CAPITOL_GAIN("CAPITOL_GAIN", CapitolGain.class);

	private String name;

	private Class<? extends Tax> taxClass;

	TaxType(String name, Class<? extends Tax> taxClass) {
		this.name = name;
		this.taxClass = taxClass;
	}

	public static boolean contains(String name) {

		for(TaxType type: TaxType.values()) {
			if (type.name().equals(name)){
				return true;
			}
		}


		return false;
	}

	public Class<? extends Tax> getTaxClass() {
		return taxClass;
	}

}