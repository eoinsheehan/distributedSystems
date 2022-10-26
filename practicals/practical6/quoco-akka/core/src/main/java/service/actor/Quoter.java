package service.actor;

import akka.actor.AbstractActor;
import service.core.Quotation;
import service.core.QuotationService;
import service.messages.Init;
import service.messages.QuotationRequest;
import service.messages.QuotationResponse;


public class Quoter extends AbstractActor {
    private QuotationService service;
   
    @Override
    public Receive createReceive() {
    return receiveBuilder()
    // initialise the relevant quotation service
    .match(Init.class,
    msg ->{
        service = msg.getQuotationService();
    })
    // upon receipt of message of type quotation request
    .match(QuotationRequest.class,
    msg -> {
    // generate a quotation using client info
    Quotation quotation = service.generateQuotation(msg.getClientInfo());
    // send quotation response message back to initial sender
    getSender().tell(new QuotationResponse(msg.getId(), quotation), getSelf());
    }).build();
    }
   } 
