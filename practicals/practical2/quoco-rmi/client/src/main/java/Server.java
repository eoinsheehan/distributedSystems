package client;

import java.text.NumberFormat;
import java.rmi.registry.Registry;
// import service.auldfellas.AFQService;
// import service.broker.LocalBrokerService;
import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.Constants;
// import service.dodgydrivers.DDQService;
// import service.girlpower.GPQService;
// import service.registry.ServiceRegistry;
import java.rmi.registry.LocateRegistry;

public class Server {
	// static {
	// 	// Create the services and bind them to the registry.
	// 	ServiceRegistry.bind(Constants.GIRL_POWER_SERVICE, new GPQService());
	// 	ServiceRegistry.bind(Constants.AULD_FELLAS_SERVICE, new AFQService());
	// 	ServiceRegistry.bind(Constants.DODGY_DRIVERS_SERVICE, new DDQService());
	// 	ServiceRegistry.bind(Constants.BROKER_SERVICE, new LocalBrokerService());
	// }
	
	/**
	 * This is the starting point for the application. Here, we must
	 * get a reference to the Broker Service and then invoke the
	 * getQuotations() method on that service.
	 * 
	 * Finally, you should print out all quotations returned
	 * by the service.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// BrokerService brokerService = ServiceRegistry.lookup(Constants.BROKER_SERVICE, BrokerService.class);
		try{
			Registry registry = LocateRegistry.getRegistry(args[0],1099);

        BrokerService brokerService = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
		// Create the broker and run the test data
		for (ClientInfo info : clients) {
			displayProfile(info);
			
			// Retrieve quotations from the broker and display them...
			for(Quotation quotation : brokerService.getQuotations(info)) {
				displayQuotation(quotation);
			}
			
			// Print a couple of lines between each client
			System.out.println("\n");
		}

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Display the client info nicely.
	 * 
	 * @param info
	 */
	public static void displayProfile(ClientInfo info) {
		System.out.println("|=================================================================================================================|");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println(
				"| Name: " + String.format("%1$-29s", info.name) + 
				" | Gender: " + String.format("%1$-27s", (info.gender==ClientInfo.MALE?"Male":"Female")) +
				" | Age: " + String.format("%1$-30s", info.age)+" |");
		System.out.println(
				"| License Number: " + String.format("%1$-19s", info.licenseNumber) + 
				" | No Claims: " + String.format("%1$-24s", info.noClaims+" years") +
				" | Penalty Points: " + String.format("%1$-19s", info.points)+" |");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println("|=================================================================================================================|");
	}

	/**
	 * Display a quotation nicely - note that the assumption is that the quotation will follow
	 * immediately after the profile (so the top of the quotation box is missing).
	 * 
	 * @param quotation
	 */
	public static void displayQuotation(Quotation quotation) {
		System.out.println(
				"| Company: " + String.format("%1$-26s", quotation.company) + 
				" | Reference: " + String.format("%1$-24s", quotation.reference) +
				" | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.price))+" |");
		System.out.println("|=================================================================================================================|");
	}
	
	/**
	 * Test Data
	 */
	public static final ClientInfo[] clients = {
		new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1"),
		new ClientInfo("Old Geeza", ClientInfo.MALE, 65, 0, 2, "ABC123/4"),
		new ClientInfo("Hannah Montana", ClientInfo.FEMALE, 16, 10, 0, "HMA304/9"),
		new ClientInfo("Rem Collier", ClientInfo.MALE, 44, 5, 3, "COL123/3"),
		new ClientInfo("Jim Quinn", ClientInfo.MALE, 55, 4, 7, "QUN987/4"),
		new ClientInfo("Donald Duck", ClientInfo.MALE, 35, 5, 2, "XYZ567/9")		
	};
}

