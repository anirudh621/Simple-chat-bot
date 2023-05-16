import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FlatFileAuthentication implements Authentication {

    private static final String USERS_FILE = "storage/users.txt";
    private Map<String, User> users;

    public FlatFileAuthentication() {
        users = new HashMap<>();
        loadUsers();
    }

    @Override
    public User register(String username, String password) {
        if (users.containsKey(username)) {
            return null;
        }
        User newUser = new User(username, password);
        users.put(username, newUser);
        saveUsers();
        return newUser;
    }

    @Override
    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            user.setOnline(true);
            saveUsers();
            return user;
        }
        return null;
    }

    @Override
    public void logout(User user) {
        user.setOnline(false);
        saveUsers();
    }

    @Override
    public boolean changeUsername(User user, String newUsername) {
        if (users.containsKey(newUsername)) {
            return false;
        }
        users.remove(user.getUsername());
        user.setUsername(newUsername);
        users.put(newUsername, user);
        saveUsers();
        return true;
    }

    @Override
    public boolean changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        saveUsers();
        return true;
    }

    private void loadUsers() {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            return;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String username = parts[0];
                String password = parts[1];
                boolean online = Boolean.parseBoolean(parts[2]);
                User user = new User(username, password);
                user.setOnline(online);
                users.put(username, user);
            }
        } catch (Exception e) {
            System.out.println("Failed to load users.");
        }
    }

    private void saveUsers() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User user : users.values()) {
                pw.println(user.getUsername() + "," + user.getPassword() + "," + user.isOnline());
            }
        } catch (Exception e) {
            System.out.println("Failed to save users.");
        }
    }

    
}
