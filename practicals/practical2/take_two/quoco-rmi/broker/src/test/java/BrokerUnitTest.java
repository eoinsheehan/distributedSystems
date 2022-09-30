import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import service.core.Constants;
import service.core.ClientInfo;
import service.core.Quotation;
import java.util.List;
import service.core.BrokerService;
import service.broker.LocalBrokerService;
import org.junit.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
public class BrokerUnitTest {
 private static Registry registry;
 @BeforeClass
 public static void setup() {
 BrokerService localBrokerService = new LocalBrokerService();
 try {
 registry = LocateRegistry.createRegistry(1099);
 BrokerService brokerService = (BrokerService)
 UnicastRemoteObject.exportObject(localBrokerService,0);
 registry.bind(Constants.BROKER_SERVICE, brokerService);
 } catch (Exception e) {
 System.out.println("Trouble: " + e);
 }
 } 


 @Test
 public void connectionTest() throws Exception {
 BrokerService service = (BrokerService)
 registry.lookup(Constants.BROKER_SERVICE);
 assertNotNull(service);
 }

 @Test
 public void generateQuotationiTest() throws Exception {
    ClientInfo info = new ClientInfo("Eoin",'M',28,0,0,"testing");
 BrokerService service = (BrokerService)
 registry.lookup(Constants.BROKER_SERVICE);
    assertNotNull(service.getQuotations(info) );

 }

 @Test
 public void checkListReturned() throws Exception {
    ClientInfo info = new ClientInfo("Eoin",'M',28,0,0,"testing");
 BrokerService service = (BrokerService)
 registry.lookup(Constants.BROKER_SERVICE);
    assertTrue(service.getQuotations(info) instanceof List);

 }

} 
