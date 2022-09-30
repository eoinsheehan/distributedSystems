import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import service.core.Constants;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;
import service.dodgydrivers.DDQService;
import org.junit.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
public class DodgyDriversUnitTest{
 private static Registry registry;
 @BeforeClass
 public static void setup() {
 QuotationService ddqService = new DDQService();
 try {
 registry = LocateRegistry.createRegistry(1099);
 QuotationService quotationService = (QuotationService)
 UnicastRemoteObject.exportObject(ddqService,0);
 registry.bind(Constants.DODGY_DRIVERS_SERVICE, quotationService);
 } catch (Exception e) {
 System.out.println("Trouble: " + e);
 }
 } 


 @Test
 public void connectionTest() throws Exception {
 QuotationService service = (QuotationService)
 registry.lookup(Constants.DODGY_DRIVERS_SERVICE);
 assertNotNull(service);
 }

 @Test
 public void generateQuotationiTest() throws Exception {
    ClientInfo info = new ClientInfo("Eoin",'M',28,0,0,"testing");
    QuotationService service = (QuotationService)
 registry.lookup(Constants.DODGY_DRIVERS_SERVICE);
    assertTrue(service.generateQuotation(info) instanceof Quotation);

 }

} 