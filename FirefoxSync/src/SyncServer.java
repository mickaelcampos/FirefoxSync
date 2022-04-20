
import crypto.SCRYPTFIPS;
import java.util.ArrayList;
import model.EncryptedData;
import model.ScryptHash;
import model.User;
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

    public boolean signUp(User user, String authenticationToken) {
        SCRYPTFIPS SCRYPTUtil = new SCRYPTFIPS();
        ScryptHash scryptHash = SCRYPTUtil.getHashedAuthToken(authenticationToken);

        user.setScryptHash(scryptHash);
        boolean success = StorageUtil.saveUser(user);
        return success;
    }

    public boolean registerData(EncryptedData encryptedData, String username, String authenticationToken) {
        SCRYPTFIPS SCRYPTUtil = new SCRYPTFIPS();

        String saltSCRYPT = getSaltSCRYPT(username);
        ScryptHash scryptHash = SCRYPTUtil.getHashedAuthToken(authenticationToken, saltSCRYPT);
        String hashedAuthToken = scryptHash.getHash();

        JSONObject user = StorageUtil.getUser(username);
        boolean success = false;
        if (user != null) {
            String storageHashedAuthToken = (String) user.get("authorizationToken");
            if (storageHashedAuthToken.equals(hashedAuthToken)) {
                success = StorageUtil.saveData(encryptedData, username);
            }
        }
        return success;
    }

    public ArrayList<EncryptedData> getData(String username, String authenticationToken) {
        SCRYPTFIPS SCRYPTUtil = new SCRYPTFIPS();

        String saltSCRYPT = getSaltSCRYPT(username);
        ScryptHash scryptHash = SCRYPTUtil.getHashedAuthToken(authenticationToken, saltSCRYPT);
        String hashedAuthToken = scryptHash.getHash();

        JSONObject user = StorageUtil.getUser(username);

        if (user != null) {
            String storageHashedAuthToken = (String) user.get("authorizationToken");
            if (storageHashedAuthToken.equals(hashedAuthToken)) {
                JSONArray data = (JSONArray) user.get("data");

                ArrayList<EncryptedData> encryptedDataList = new ArrayList();

                for (Object obj : data) {
                    JSONObject objAsJson = (JSONObject) obj;
                    encryptedDataList.add(new EncryptedData(
                            objAsJson.getString("data"),
                            objAsJson.getString("Nonce")
                    ));
                }
                return encryptedDataList;
            }
            return null;
        }
        return null;
    }

    public String getSalt(String username) {
        JSONObject user = StorageUtil.getUser(username);
        if (user != null) {
            String salt = user.getString("salt");
            return salt;
        }
        return null;
    }

    public String getSaltSCRYPT(String username) {
        JSONObject user = StorageUtil.getUser(username);
        if (user != null) {
            String saltSCRYPT = user.getString("saltSCRYPT");
            return saltSCRYPT;
        }
        return null;
    }
}
