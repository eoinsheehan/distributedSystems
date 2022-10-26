package service.messages;

import service.core.ClientInfo;

public class QuotationRequest implements MySerializable {
    private int id;
    private ClientInfo clientInfo;
    public QuotationRequest(int id, ClientInfo clientInfo) {
    this.id = id;
    this.clientInfo = clientInfo;
    }
    // add get and set methods for each field
    public ClientInfo getClientInfo(){
        return this.clientInfo;
    }

    public void setClientInfo(ClientInfo info){
        this.clientInfo = info;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }
   } 