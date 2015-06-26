package com.test;

//import gov.nih.nci.cadsr.domain.DataElement;
//import gov.nih.nci.cadsr.domain.DataElementConcept;
//import gov.nih.nci.cadsr.domain.EnumeratedValueDomain;
//import gov.nih.nci.cadsr.domain.PermissibleValue;
//import gov.nih.nci.cadsr.domain.ValueDomain;
//import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
//import gov.nih.nci.system.applicationservice.ApplicationService;
//import gov.nih.nci.system.client.ApplicationServiceProvider;
import java.util.Collection;
import java.util.List;

/**
 * A sample use of the caDSR api.
 * 
 * @author caDSR team.
 */
public class TestCaDsrApi {
	/**
	 * Search for Data Elements using the Long Name
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		try {
			ApplicationService appService = ApplicationServiceProvider
					.getApplicationService("CaDsrServiceInfo");
			System.out.println("Searching for DataElements");
			// Search for the Data Element with the Long Name
			// "Patient Race Category*". The asterisk (*) is a wild card.
			DataElement dataElement = new DataElement();
			dataElement.setLongName("Patient Race Category*");
			dataElement.setLatestVersionIndicator("Yes");
			List<Object> results = appService.search(DataElement.class,
					dataElement);
			for (Object obj : results) {
				// Show the DE and its DEC and VD
				DataElement de = (DataElement) obj;
				System.out.println("Data Element " + de.getLongName());
				DataElementConcept dec = de.getDataElementConcept();
				System.out.println("Data Element Concept " + dec.getLongName());
				ValueDomain vd = de.getValueDomain();
				System.out.println("Value Domain " + vd.getLongName());
				if (vd instanceof EnumeratedValueDomain) {
					// Get the PermissibleValues for the ValueDomain
					EnumeratedValueDomain evd = (EnumeratedValueDomain) vd;
					Collection<ValueDomainPermissibleValue> vdpvs = evd
							.getValueDomainPermissibleValueCollection();
					for (ValueDomainPermissibleValue vdpv : vdpvs) {
						PermissibleValue pv = vdpv.getPermissibleValue();
						System.out.println(" Permissible Value : "
								+ pv.getValue());
					}
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Error in the TestCaDsrApi");
		}
*/	
	}
}