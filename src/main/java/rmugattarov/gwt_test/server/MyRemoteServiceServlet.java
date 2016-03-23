package rmugattarov.gwt_test.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import rmugattarov.gwt_test.shared.MyRemoteService;

import javax.servlet.annotation.WebServlet;

/**
 * Created by rmugattarov on 21.01.2016.
 */
@WebServlet("/myService")
public class MyRemoteServiceServlet extends RemoteServiceServlet implements MyRemoteService {
    public String sayHello() {
        return "Hello!";
    }
}
