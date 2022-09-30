import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import service.girlpower.GPQService;
import service.core.ClientInfo;
import service.core.BrokerService;
import service.core.Quotation;
import service.core.QuotationService;
import service.core.Constants;
public class Server {
 public static void main(String[] args) {
 QuotationService gpqService = new GPQService();
 try {
 // Connect to the RMI Registry - creating the registry will be the 
// responsibility of the broker.
Registry registry = null;
 registry = LocateRegistry.getRegistry(args[0], 1099);

// Create the Remote Object
QuotationService quotationService = (QuotationService)
UnicastRemoteObject.exportObject(gpqService,0);
// Register the object with the RMI Registry by invoking the register service method

BrokerService brokerService = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
brokerService.registerObject(Constants.GIRL_POWER_SERVICE,quotationService);

System.out.println("STOPPING SERVER SHUTDOWN");
while (true) {Thread.sleep(1000); }
} catch (Exception e) {
System.out.println("Trouble: " + e);
}
}
} 
