package service.core;

import java.util.ArrayList;

public class ClientApplication implements java.io.Serializable  {
    private ClientInfo info;
    private ArrayList<Quotation> quotations;

    public ClientApplication(ClientInfo info,ArrayList<Quotation> quotations){
        this.info=info;
        this.quotations=quotations;
    }

    public ClientApplication(){};

    public ClientInfo getInfo(){
        return this.info;
    }

    public void setInfo(ClientInfo info){
        this.info = info;
    }

    
    public ArrayList<Quotation> getQuotations(){
        return this.quotations;
    }

    public void setQuotations(ArrayList<Quotation> quotation){
        this.quotations = quotation;
    }
    
}
