import java.io.*;
import java.util.*;

public class FlatFileChatStorage implements ChatStorage {
    private static final String ROOMS_FOLDER = "storage/rooms";
    private Map<String, ChatRoom> chatRooms;

    public FlatFileChatStorage() {
        chatRooms = loadChatRooms();
    }

    @Override
    public ChatRoom createRoom(String roomName) {
        if (chatRooms.containsKey(roomName)) {
            return null;
        }
        ChatRoom newRoom = new ChatRoom(roomName);
        chatRooms.put(roomName, newRoom);
        saveChatRooms();
        return newRoom;
    }

    @Override
    public ChatRoom joinRoom(String roomName, User user) {
        ChatRoom chatRoom = chatRooms.get(roomName);
        if (chatRoom != null) {
            chatRoom.getUsers().add(user);
            saveChatRooms();
        }
        return chatRoom;
    }

    @Override
    public boolean leaveRoom(String roomName, User user) {
        ChatRoom chatRoom = chatRooms.get(roomName);
        if (chatRoom != null) {
            chatRoom.getUsers().remove(user);
            saveChatRooms();
            return true;
        }
        return false;
    }

    @Override
    public boolean addMessage(String roomName, String message) {
        try {
            FileWriter writer = new FileWriter(ROOMS_FOLDER + "/" + roomName + ".txt", true);
            writer.write(message + "\n");
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public List<String> getMessages(String roomName) {
        List<String> messages = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(ROOMS_FOLDER + "/" + roomName + ".txt"));
            while (scanner.hasNextLine()) {
                messages.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            // Do nothing, just return empty list
        }
        return messages;
    }

    @Override
    public List<String> listUsers(String roomName) {
        ChatRoom chatRoom = chatRooms.get(roomName);
        if (chatRoom != null) {
            List<String> usernames = new ArrayList<>();
            for (User user : chatRoom.getUsers()) {
                usernames.add(user.getUsername());
            }
            return usernames;
        }
        return new ArrayList<>();
    }

    private Map<String, ChatRoom> loadChatRooms() {
        Map<String, ChatRoom> loadedChatRooms = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File("storage/chat_rooms.txt"));
            while (scanner.hasNextLine()) {
                String roomName = scanner.nextLine();
                loadedChatRooms.put(roomName, new ChatRoom(roomName));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            // Do nothing, just return empty map
        }
        return loadedChatRooms;
    }

    private void saveChatRooms() {
        try {
            FileWriter writer = new FileWriter("storage/chat_rooms.txt");
            for (String roomName : chatRooms.keySet()) {
                writer.write(roomName + "\n");
            }
            writer.close();
        } catch (IOException e) {
            // Do nothing
        }
    }

    @Override
    public ChatRoom getRoom(String roomName) {
        return chatRooms.get(roomName);
    }

    
}
