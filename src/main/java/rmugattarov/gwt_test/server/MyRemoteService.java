package rmugattarov.gwt_test.server;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Created by rmugattarov on 21.01.2016.
 */
@RemoteServiceRelativePath("myService")
public interface MyRemoteService extends RemoteService {
    String sayHello();
}
