package service;

import java.util.HashMap;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import service.core.message.ClientApplicationMessage;
import service.core.message.QuotationRequestMessage;
import service.core.message.QuotationResponseMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Broker {
    static HashMap<Long,ClientApplicationMessage> cache = new HashMap<Long,ClientApplicationMessage>();
    public static void main(String[] args)  {
		// BrokerService brokerService = ServiceRegistry.lookup(Constants.BROKER_SERVICE, BrokerService.class);
        String host = "localhost";
		if (args.length > 0) {
			host = args[0];
		}

try{
        ConnectionFactory factory =
new ActiveMQConnectionFactory("failover://tcp://"+host+":61616");
 Connection connection = factory.createConnection();
 connection.setClientID("broker");
 Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

 // create the three queues and the topic needed to implement the system
 Queue requestsQueue= session.createQueue("REQUESTS");
 Queue responsesQueue = session.createQueue("RESPONSES");
 Queue quotationsQueue = session.createQueue("QUOTATIONS");
 Topic applicationsTopic = session.createTopic("APPLICATIONS");

 // add relevant producer and consumers
 MessageProducer producer = session.createProducer(applicationsTopic);
 MessageConsumer quotationsConsumer = session.createConsumer(quotationsQueue);
 MessageConsumer requestsConsumer = session.createConsumer(requestsQueue);
 MessageProducer responsesProducer = session.createProducer(responsesQueue);
 connection.start();

new Thread(()->{
while(true){
    try{
// consume the request from the client
Message message = requestsConsumer.receive();
// add the reference ID to the broker hashmap if it is not already present
if (message instanceof ObjectMessage) {
    Object content = ((ObjectMessage) message).getObject();
    if (content instanceof QuotationRequestMessage) {
    QuotationRequestMessage quotationRequest = (QuotationRequestMessage) content;
    Message request = session.createObjectMessage(quotationRequest);
    // if the request id is not already in the broker cache then add it
        if(!cache.containsKey(quotationRequest.id)){
            // map the ID to a placehoder Client application object
            cache.put(quotationRequest.id, new ClientApplicationMessage(quotationRequest.info));
            // send the request out on the applications topic
            producer.send(request);
        }

    // sleep the thread before sending back quotations to the client
    try {
        Thread.sleep(5000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    //  send all quotations back for this ID
    // get key value pair for this particular ID
    ClientApplicationMessage allQuotations = cache.get(quotationRequest.id);
    // package quotations as JMS message object
    Message clientResponse = session.createObjectMessage(allQuotations);
    // send back the response with all quotations to the client
    responsesProducer.send(clientResponse);
    }
    message.acknowledge();
   } else {
    System.out.println("Unknown message type: " +
   message.getClass().getCanonicalName());
   } 
}
catch(JMSException e){
    e.printStackTrace();
} 
}}).start();

new Thread(()->{
while(true){
    try{
// consume the message on the quotations queue
Message message = quotationsConsumer.receive();
// add the reference ID to the broker hashmap if it is not already presen
if (message instanceof ObjectMessage) {
    Object content = ((ObjectMessage) message).getObject();
    if (content instanceof QuotationResponseMessage) {
    QuotationResponseMessage quotationResponse = (QuotationResponseMessage) content;

// check the id of the quotation versus the cache
if(cache.get(quotationResponse.id)!=null){
// add it to relevant location on broker cache
    ClientApplicationMessage relevantClientMessage = cache.get(quotationResponse.id);
    relevantClientMessage.quotations.add(quotationResponse.quotation);
}
    }
}
message.acknowledge();
    }
// catch statement for second thread listening for quotations and adding the to hashmap
catch(JMSException e){
    e.printStackTrace();
} 
}
}).start();

}
// catch statement for connection establishment
    catch(JMSException e){
        e.printStackTrace();
    } 
  }
}