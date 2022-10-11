package service.core.message;

import java.util.ArrayList;

import service.core.ClientInfo;
import service.core.Quotation;

public class ClientApplicationMessage implements java.io.Serializable {
 public ClientInfo info;
 public ArrayList<Quotation> quotations;

 /**
 * @param info
 */
public ClientApplicationMessage(ClientInfo info) {
 this.info = info;
 this.quotations = new ArrayList<Quotation>();
 }
} 