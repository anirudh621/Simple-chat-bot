public interface Authentication {
    User register(String username, String password);
    User login(String username, String password);
    void logout(User user);
    boolean changeUsername(User user, String newUsername);
    boolean changePassword(User user, String newPassword);

}
