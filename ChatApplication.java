import java.util.Scanner;
import java.io.File;
public class ChatApplication {

    public static void main(String[] args) {
        Authentication auth = new FlatFileAuthentication();
        ChatStorage chatStorage = new FlatFileChatStorage();

        Scanner scanner = new Scanner(System.in);
        
        File usersFile = new File("storage/users.txt");
        if (usersFile.exists()) {
            usersFile.delete();
        }
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            if (choice == 1) {
                System.out.print("Enter a username: ");
                String username = scanner.nextLine();
                System.out.print("Enter a password: ");
                String password = scanner.nextLine();

                if (auth.register(username, password) != null) {
                    System.out.println("Registration successful!");
                } else {
                    System.out.println("Registration failed. Username already exists.");
                }
            } else if (choice == 2) {
                System.out.print("Enter your username: ");
                String username = scanner.nextLine();
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();

                User user = auth.login(username, password);

                if (user != null) {
                    System.out.println("Login successful!");

                    while (true) {
                        System.out.println("1. Create a room");
                        System.out.println("2. Join a room");
                        System.out.println("3. Logout");
                        System.out.print("Enter your choice: ");

                        int loggedInChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline left-over

                        if (loggedInChoice == 1) {
                            System.out.print("Enter room name: ");
                            String roomName = scanner.nextLine();
                            chatStorage.createRoom(roomName);
                            System.out.println("Room created and joined.");
                        } else if (loggedInChoice == 2) {
                            System.out.print("Enter room name: ");
                            String roomName = scanner.nextLine();
                            ChatRoom chatRoom = chatStorage.getRoom(roomName);

                            if (chatRoom != null) {
                                System.out.println("Joined room: " + roomName);

                                while (true) {
                                    System.out.print("Type a message (type '/help' for a list of available commands): ");
                                    String message = scanner.nextLine();

                                    if (message.equals("/leave")) {
                                        System.out.println("Left room: " + roomName);
                                        break;
                                    } else if (message.equals("/history")) {
                                        System.out.println("Chat history:");
                                        for (String msg : chatRoom.getHistory()) {
                                            System.out.println(msg);
                                        }
                                    } else if (message.equals("/list")) {
                                        System.out.println("Current users in room " + roomName + ":");
                                        for (String usernameInRoom : chatStorage.listUsers(roomName)) {
                                            System.out.println("- " + usernameInRoom);
                                        }
                                     } else if (message.equals("/help")) {
                                        System.out.println("Available commands:");
                                        System.out.println("/history - see chat history");
                                        System.out.println("/list - list current users");
                                        System.out.println("/leave - leave the room");
                                        System.out.println("/help - show available commands");
                                    } else {
                                        chatRoom.sendMessage(user, message);
                                    }
                                }
                            } else {
                                System.out.println("Room not found.");
                            }
                        } else if (loggedInChoice == 3) {
                            System.out.println("Logged out.");
                            break;
                        }else {
                            System.out.println("Invalid choice.");
                        }
                    }
                } else {
                    System.out.println("Login failed. Invalid username or password.");
                }
            } else {
                System.out.println("Invalid choice.");
            }

        }
        
    }
    
}
