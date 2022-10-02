package service.core;
import javax.jws.WebService;
import javax.jws.WebMethod;
import java.util.List;
@WebService
public interface BrokerService {
@WebMethod List<Quotation> getQuotations(ClientInfo info);
}

