
import java.util.ArrayList;
import java.util.List;
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

    public boolean registerData(String encryptedData, String username, String authenticationToken) {
        SCRYPTUtil SCRYPTUtil = new SCRYPTUtil();
        String hashedAuthToken = SCRYPTUtil.getHashedAuthToken(authenticationToken);
        JSONObject user = StorageUtil.getUser(username);
        System.out.println("[SyncServer] user: " + user.toString());
        boolean success = false;
        if (user != null) {
            String storageHashedAuthToken = (String) user.get("authorizationToken");
            if (storageHashedAuthToken.equals(hashedAuthToken)) {
                success = StorageUtil.saveData(encryptedData, username);
            }
        }
        return success;
    }

    public List<Object> getData(String username, String authenticationToken) {
        SCRYPTUtil SCRYPTUtil = new SCRYPTUtil();
        String hashedAuthToken = SCRYPTUtil.getHashedAuthToken(authenticationToken);
        JSONObject user = StorageUtil.getUser(username);
        System.out.println("[SyncServer] user: " + user.toString());

        if (user != null) {
            String storageHashedAuthToken = (String) user.get("authorizationToken");
            if (storageHashedAuthToken.equals(hashedAuthToken)) { // login
                JSONArray data = (JSONArray) user.get("data");
                System.out.println("[SyncServer] data.toString(): " + data.toString());

                // percorre o data
                List<Object> encryptedData = data.toList();
                //String encryptedData = data.getString(0);// TODO pegar todos, no formato ["","",""...]
                System.out.println("[SyncServer] encryptedData: " + encryptedData);
                return encryptedData;
//return encryptedData;
            }
            System.out.println("[SyncServer] storageHashedAuthToken !== hashedAuthToken");
            return null;
        }
        System.out.println("[SyncServer] usernull");
        return null;
    }
}
