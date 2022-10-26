package service.messages;

import service.core.Quotation;

public class QuotationResponse implements MySerializable{
    private int id;
    private Quotation quotation;
    public QuotationResponse(int id, Quotation quotation) {
    this.id = id;
    this.quotation = quotation;
    }
    // add get and set methods for each field
    public void setID(int id){
        this.id=id;
    }

    public int getID(){
        return this.id;
    }

    public void setQuotation(Quotation quotation){
        this.quotation = quotation;
    }

    public Quotation getQuotation(){
        return this.quotation;
    }
   } 