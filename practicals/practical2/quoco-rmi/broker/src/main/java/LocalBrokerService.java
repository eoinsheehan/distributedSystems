package service.broker;

import java.util.LinkedList;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.List;

import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;

public class LocalBrokerService implements BrokerService {
    public List<Quotation> getQuotations(ClientInfo info) throws java.rmi.RemoteException{
		List<Quotation> quotations = new LinkedList<Quotation>();
        try{
        Registry registry = LocateRegistry.getRegistry("localhost",1099);
		
		for (String name : registry.list()) {
			if (name.startsWith("qs-")) {
				QuotationService service = (QuotationService) registry.lookup(name);
				quotations.add(service.generateQuotation(info));
			}
		}
    }
    catch(Exception e){
        e.printStackTrace();
    }
    return quotations;
	}

    public void registerObject(String name, java.rmi.Remote service) throws java.rmi.RemoteException{
        Registry registry = LocateRegistry.getRegistry(1099);
        try{
            registry.bind(name,service);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
