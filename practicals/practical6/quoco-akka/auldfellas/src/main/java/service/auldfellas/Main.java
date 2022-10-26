package service.auldfellas;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.actor.Quoter;
import service.messages.Init;

public class Main {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();
        ActorRef ref = system.actorOf(Props.create(Quoter.class), "auldfellas");
        ref.tell(new Init(new AFQService()), null);
        ActorSelection selection =
        system.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");
        selection.tell("register", ref);
       } 
    
}
