public class AuthManager {
    private AuthManager() {
    }

    private static AuthManager instance = null;
    // private String loggedInUserId = null;
    // private boolean isAdmin = false;

    public static AuthManager getInstance() {
        if (instance == null)
            instance = new AuthManager();
        return instance;
    }

    public void login(String username, String password) {
        System.out.println("Logging in as " + username);
    }

    public void logout() {
        System.out.println("Logging out");

        // loggedInUserId = null;
        // isAdmin = false;
    }

    public void changePassword(String oldPassword, String newPassword) {
        System.out.println("Changing password");
    }

    public void resetPassword(String username) {
        System.out.println("Resetting password");
    }

    public void changeUsername(String oldUsername, String newUsername) {
        System.out.println("Changing username");
    }
}