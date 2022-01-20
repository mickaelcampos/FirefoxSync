
public final class SyncServer {

    private static SyncServer instance = null;

    public static SyncServer getInstance() {
        if (instance == null) {
            instance = new SyncServer();
        }
        return instance;
    }

    public boolean signUp(String username, String authenticationToken) {
        SCRYPTUtil SCRYPTUtil = new SCRYPTUtil();
        String hashedAuthToken = SCRYPTUtil.getHashedAuthToken(authenticationToken);
        boolean success = StorageUtil.saveUser(username, authenticationToken);
        return success;
    }
}
