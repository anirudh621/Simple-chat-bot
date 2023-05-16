import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    private String name;
    private List<User> users;
    private List<String> messages = new ArrayList<>();

    public ChatRoom(String name) {
        this.name = name;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void sendMessage(User sender, String message) {
        String formattedMessage = String.format("%s: %s", sender.getUsername(), message);
        messages.add(formattedMessage); // add the formatted message to the list
    }

    public List<String> getMessages() {
        return messages;
    }

    public List<String> getHistory() {
        return messages;
    }

    
}
