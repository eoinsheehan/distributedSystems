package service.receiver;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.jms.Queue;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.MessageConsumer;
import service.core.message.*;
import service.core.Quotation;
import service.dodgydrivers.DDQService;
import service.core.QuotationService;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver {
    public static void main(String[] args){
		String host = "localhost";
		if (args.length > 0) {
			host = args[0];
		}
try {
 ConnectionFactory factory = new ActiveMQConnectionFactory("failover://tcp://"+host+":61616");
 Connection connection = factory.createConnection();
 connection.setClientID("dodgydrivers");
 Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
 
 Queue queue = session.createQueue("QUOTATIONS");
 Topic topic = session.createTopic("APPLICATIONS");
 MessageConsumer consumer = session.createConsumer(topic);
 MessageProducer producer = session.createProducer(queue);

 connection.start();
 while (true) {
 // Get the next message from the APPLICATION topic
 Message message = consumer.receive();
// Check it is the right type of message
 if (message instanceof ObjectMessage) {
 // It’s an Object Message
 Object content = ((ObjectMessage) message).getObject();
if (content instanceof QuotationRequestMessage) {
 // It’s a Quotation Request Message
 QuotationRequestMessage request = (QuotationRequestMessage) content;
 // Generate a quotation and send a quotation response message…
QuotationService  service = new DDQService();
 Quotation quotation = service.generateQuotation(request.info);
 Message response = session.createObjectMessage(
new QuotationResponseMessage(request.id, quotation));
 producer.send(response);
}
 } else {
 System.out.println("Unknown message type: " +
message.getClass().getCanonicalName());
 }
 } 
}
catch(JMSException e){
    e.printStackTrace();
}
    }
    
}


