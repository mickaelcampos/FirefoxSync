
import org.json.JSONArray;
import org.json.JSONObject;

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
        boolean success = StorageUtil.saveUser(username, hashedAuthToken);
        return success;
    }

    public boolean registerData(String encryptedData) {
        boolean success = StorageUtil.saveData(encryptedData);
        return success;
    }

    public String getData(String username, String authenticationToken) {
        SCRYPTUtil SCRYPTUtil = new SCRYPTUtil();
        String hashedAuthToken = SCRYPTUtil.getHashedAuthToken(authenticationToken);
        JSONObject user = StorageUtil.getUser(username);
        System.out.println("[SyncServer] user: " + user.toString());

        if (user != null) {
            String storageHashedAuthToken = (String) user.get("authorizationToken");
            if (storageHashedAuthToken.equals(hashedAuthToken)) {
                JSONArray encryptedData = (JSONArray) user.get("data");
                System.out.println("[SyncServer] encryptedData: " + encryptedData.toString());
                return (String) encryptedData.get(0);// TODO pegar todos
            }
            System.out.println("[SyncServer] storageHashedAuthToken !== hashedAuthToken");
            return null;
        }
        System.out.println("[SyncServer] usernull");
        return null;
    }
}
