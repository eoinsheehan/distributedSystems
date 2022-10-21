package service.core;

import java.util.List;

public class ClientApplication  {
    public ClientInfo info;
    public List<Quotation> quotations;

    public ClientApplication(ClientInfo info,List<Quotation> quotations){
        this.info=info;
        this.quotations=quotations;
    }

    public ClientApplication(){};

    // public ClientInfo getInfo(){
    //     return this.info;
    // }

    // public void setInfo(ClientInfo info){
    //     this.info = info;
    // }

    
    // public List<Quotation> getQuotations(){
    //     return this.quotations;
    // }

    // public void setQuotations(Quotation quotation){
    //     this.quotations.add(quotation);
    // }
    
}
