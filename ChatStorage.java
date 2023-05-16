import java.util.List;

public interface ChatStorage {
    ChatRoom createRoom(String roomName);
    ChatRoom joinRoom(String roomName, User user);
    boolean leaveRoom(String roomName, User user);
    boolean addMessage(String roomName, String message);
    List<String> getMessages(String roomName);
    List<String> listUsers(String roomName);
    ChatRoom getRoom(String roomName);

}
