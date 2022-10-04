package service.core;

import java.net.InetAddress;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceEvent;
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
import javax.jmdns.JmDNS;

    
    /**
     * 
     * @author Rem
     *
     */
    
@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public class Broker{
   static LinkedList<URL> servicesAvailable = new LinkedList<URL>();
    public static void main(String[] args) {
        try {
        Endpoint endpoint = Endpoint.create(new Broker());
        HttpServer server = HttpServer.create(new InetSocketAddress(9000), 5);
        server.setExecutor(Executors.newFixedThreadPool(5));
        HttpContext context = server.createContext("/broker");
        endpoint.publish(context);
        server.start();
        JmDNS jmDNS = JmDNS.create(InetAddress.getLocalHost());
        jmDNS.addServiceListener("_http._tcp.local.", new WSDLServiceListener());
        } catch (Exception e) {
        e.printStackTrace();
        }

       }

       public static class WSDLServiceListener implements ServiceListener {
        @Override
        public void serviceAdded(ServiceEvent event) {
            System.out.println("Service added: " + event.getInfo());
        }
        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.println("Service removed: " + event.getInfo());
        }
        @Override
        public void serviceResolved(ServiceEvent event) {
            System.out.println("Service resolved: " + event.getInfo());
            String path = event.getInfo().getPropertyString("path");
            if (path != null) {
                try {
                    String url =event.getInfo().getURLs()[0];
                    servicesAvailable.add(new URL(url));
                } catch (Exception e) {
                    System.out.println("Problem with service: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
  


        // @Override
@WebMethod
public List<Quotation> getQuotations(ClientInfo info) {
    List<Quotation> quotations = new LinkedList<Quotation>();
        try{
            
        for (URL  wsdlUrl: servicesAvailable) {
            try{
                QName serviceName = new QName("http://core.service/", "QuoterService");
                Service service = Service.create(wsdlUrl, serviceName);
                QName portName = new QName("http://core.service/", "QuoterPort");
                QuoterService quotationService = service.getPort(portName, QuoterService.class);
                quotations.add(quotationService.generateQuotation(info));
            } 
            catch (Exception e) {
            e.printStackTrace();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        // return quotations from all insurers
        return quotations;
        }
    
           
    
    
}
