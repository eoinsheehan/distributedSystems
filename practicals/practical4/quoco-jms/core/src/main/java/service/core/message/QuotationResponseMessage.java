package service.core.message;

import service.core.Quotation;

public class QuotationResponseMessage implements java.io.Serializable {
 public long id;
 public Quotation quotation;

 public QuotationResponseMessage(long id, Quotation quotation) {
 this.id = id;
 this.quotation = quotation;
 }
} 
