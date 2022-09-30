import service.core.BrokerService;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import service.core.Constants;
import service.broker.LocalBrokerService;

public class Server {
    public static void main(String args[]) {
        try {
            Registry registry = null;
            if (args.length == 0) {
             registry = LocateRegistry.createRegistry(1099);
            } else {
             registry = LocateRegistry.getRegistry(args[0], 1099);
            } 
            
            // Create the Remote object
            BrokerService broker = new LocalBrokerService(); 
            BrokerService brokerService = (BrokerService) UnicastRemoteObject.exportObject(broker,0);
        
            // Register the object with the RMI Registry
            registry.bind(Constants.BROKER_SERVICE, brokerService);

            System.out.println("BROKER OPEN FOR BUSINESS");
while (true) {Thread.sleep(1000); }
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

}
