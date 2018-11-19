import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class TestEndpoint {
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("onMessage");
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose");
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("onOpen");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("onError");
    }
}
