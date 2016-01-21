package rmugattarov.gwt_test.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Created by rmugattarov on 21.01.2016.
 */
public class MyRemoteServiceServlet extends RemoteServiceServlet implements MyRemoteService {
    public String sayHello() {
        return "Hello!";
    }
}
