   package service.core; 
    
    import service.core.AbstractQuotationService;
    import service.core.ClientInfo;
    import service.core.Quotation;
    import javax.jws.WebMethod;
    import javax.jws.WebService;
    import javax.jws.soap.SOAPBinding;
    import javax.jws.soap.SOAPBinding.*;
    import javax.xml.ws.Endpoint;
    import java.net.*;
    import com.sun.net.httpserver.*;
    import java.util.concurrent.Executors;
    import java.util.LinkedList;
    import javax.xml.namespace.QName;
import java.util.List;
import javax.xml.ws.Service;
    
    /**
     * Implementation of the AuldFellas insurance quotation service.
     * 
     * @author Rem
     *
     */
    
    @WebService
    @SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public class Broker{

    String host = "localhost";

    public static void main(String[] args) {
        try {
        Endpoint endpoint = Endpoint.create(new Broker());
        HttpServer server = HttpServer.create(new InetSocketAddress(9000), 5);
        server.setExecutor(Executors.newFixedThreadPool(5));
        HttpContext context = server.createContext("/broker");
        endpoint.publish(context);
        server.start();
        } catch (Exception e) {
        e.printStackTrace();
        }
       }

        // @Override
        @WebMethod
        public List<Quotation> getQuotations(ClientInfo info) {
            List<Quotation> quotations = new LinkedList<Quotation>();
            int[] servicesAvailable = new int[]{9001, 9002, 9003};
            try{
            
            for (int port : servicesAvailable) {

                try{
    // broker looking up insurance providers web services
      URL wsdlUrl = new URL("http://" + host + ":" + port + "/quotation?wsdl");
      QName serviceName = new QName("http://core.service/", "QuoterService");
      Service service = Service.create(wsdlUrl, serviceName);
      QName portName = new QName("http://core.service/", "QuoterPort");
      QuoterService quotationService = service.getPort(portName, QuoterService.class);
    //   displayProfile(info);
      Quotation quotation = quotationService.generateQuotation(info);
    //   displayQuotation(quotation);
    //   System.out.println("\n");
      } catch (Exception e) {
      e.printStackTrace();
      }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return quotations;
        }
    
           
    
    }