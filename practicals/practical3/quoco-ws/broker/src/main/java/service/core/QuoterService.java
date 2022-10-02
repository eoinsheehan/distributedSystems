package service.core;
import javax.jws.WebService;
import javax.jws.WebMethod;
@WebService
public interface QuoterService{
@WebMethod Quotation generateQuotation(ClientInfo info);
}
