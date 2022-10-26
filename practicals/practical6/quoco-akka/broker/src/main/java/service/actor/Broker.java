package service.actor;

import java.util.ArrayList;
import java.util.List;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import service.core.ClientInfo;
import service.messages.QuotationRequest;
import service.messages.QuotationResponse;

public class Broker extends AbstractActor {
    private List<ActorRef> actorRefs = new ArrayList<ActorRef>();
   
    @Override
    public Receive createReceive() {
        return receiveBuilder()
        .match(String.class,
        msg -> {
        if (!msg.equals("register")) {
            System.out.println("Registering"+getSender());
        actorRefs.add(getSender());
        getSender().tell(new QuotationRequest(1,
        new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1")),getSelf());
        };
        })
        .match(QuotationResponse.class,
        msg -> {
            System.out.println(msg.getID()+"from "+getSender());
        }).build(); 
    }
   } 
