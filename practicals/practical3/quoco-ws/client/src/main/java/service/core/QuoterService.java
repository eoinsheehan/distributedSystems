package service.core;
import javax.jws.WebService;
import service.core.Quotation;
import javax.jws.WebMethod;
@WebService
public interface QuoterService {
@WebMethod Quotation generateQuotation(ClientInfo info);
}

