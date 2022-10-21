package service.core;

import java.util.ArrayList;
import java.util.List;

public class ClientApplication  {
    public ClientInfo info;
    public ArrayList<Quotation> quotations;

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

    
    // public List<Quotation> getQuotations(){
    //     return this.quotations;
    // }

    // public void setQuotations(Quotation quotation){
    //     this.quotations.add(quotation);
    // }
    
}
