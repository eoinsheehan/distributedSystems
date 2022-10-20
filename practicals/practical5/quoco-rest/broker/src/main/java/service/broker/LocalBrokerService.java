package service.broker;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestMapping;

import service.core.ClientApplication;
import service.core.ClientInfo;
import service.core.Quotation;

/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */

@RestController
public class LocalBrokerService {

    private Map<Long, ClientApplication> cache = new HashMap<>();
    private static long applicationID=0;

    String[] services = new String[]{"http://localhost:8081/","http://localhost:8082/","http://localhost:8083/"};
    
    @RequestMapping(value="/applications",method=RequestMethod.POST)
    public ResponseEntity<ClientApplication> postApplication(@RequestBody ClientInfo info) {
        // get the application ID from the incoming application
        applicationID++;
        // try add it as a key in the cache
        if(!cache.containsKey(applicationID))   { 
        cache.put(applicationID,new ClientApplication(info,new LinkedList<Quotation>()));
        }
        // call the getQuotations method
        List<Quotation> quotations = getQuotations(info);
        // the list of quotations returned by this method should be associated with the ClientInfo and a unique application number stored in the map
        // after all services have been checked add all quotations to cache for get requests
        cache.put(applicationID, new ClientApplication(info, quotations));
        String path = ServletUriComponentsBuilder.fromCurrentContextPath().
        build().toUriString()+ "/applications/";
        HttpHeaders headers = new HttpHeaders();
        try {
            // set location of the object which has been created
            headers.setLocation(new URI(path));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // get the relevant application from the cache
        ClientApplication finalQuotations = cache.get(applicationID);
        // return quotations back to the client
        return new ResponseEntity<>(finalQuotations, headers, HttpStatus.CREATED);
    }


	public List<Quotation> getQuotations(ClientInfo info) {
		List<Quotation> quotations = new LinkedList<Quotation>();

        // for each of the available services
        for(String url: services){
            RestTemplate restTemplate = new RestTemplate();
            // create the body of the request
            HttpEntity<ClientInfo> request = new HttpEntity<>(info);
            // retrieve the client application from the broker API
            Quotation quotationResponse = restTemplate.postForObject(url +"quotations",
            request, Quotation.class);
            // add quotation returned to list of quotations
            quotations.add(quotationResponse);
            // store response in cache to enable get requests
        }
        // return the quotations returned by all of the services
		return quotations;
	}

    // get method for all applications
@RequestMapping(value="/applications/{applicationID}",method=RequestMethod.GET)
public ClientApplication getApplicationByID(@PathVariable("applicationID") long applicationID) {
    ClientApplication requestedApplication;
    // check cache for this ID
    if(cache.containsKey(applicationID)){
        requestedApplication = cache.get(applicationID);
	return requestedApplication;
    }
    return null;
}



    // return list of client application objects

    // get method for specific application ID@RequestMapping(value="/quotations/{reference}",method=RequestMethod.GET)
// public Quotation getResource(@PathVariable("reference") String reference) {
// 	Quotation quotation = quotations.get(reference);
// 	if (quotation == null) throw new NoSuchQuotationException();

// 	return quotation;
// }

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchQuotationException extends RuntimeException {
 static final long serialVersionUID = -6516152229878843037L;
}


}

