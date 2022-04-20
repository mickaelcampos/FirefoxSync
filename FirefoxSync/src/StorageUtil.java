
import java.io.FileInputStream;
import java.io.FileOutputStream;
import model.EncryptedData;
import model.User;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class StorageUtil {

    static final int indentFactor = 2;
    static final String dataBasePath = System.getProperty("user.dir") + "/src/database.json";

    public static String getDataBase() {
        return StorageUtil.readFile(dataBasePath);
    }

    public static boolean saveUser(User user) {
        String database = StorageUtil.getDataBase();

        JSONArray databaseJSON = new JSONArray(database);

        for (Object obj : databaseJSON) {
            JSONObject userAsJSON = (JSONObject) obj;
            if (userAsJSON.get("name").equals(user.getName())) {
                return false;
            }
        }

        databaseJSON.put(new JSONObject()
                .put("name", user.getName())
                .put("authorizationToken", user.getScryptHash().getHash())
                .put("salt", user.getSalt())
                .put("saltSCRYPT", user.getScryptHash().getSalt())
        );
        StorageUtil.writeFile(dataBasePath, databaseJSON.toString(indentFactor));
        return true;
    }

    public static boolean saveData(EncryptedData data, String username) {
        String database = StorageUtil.getDataBase();

        JSONArray databaseJSON = new JSONArray(database);

        for (int i = 0; i < databaseJSON.length(); i++) {
            JSONObject userAsJSON = (JSONObject) databaseJSON.getJSONObject(i);
            if (userAsJSON.get("name").equals(username)) {
                userAsJSON.append("data",
                        new JSONObject()
                                .put("data", data.data)
                                .put("Nonce", data.Nonce)
                );
                StorageUtil.writeFile(dataBasePath, databaseJSON.toString(indentFactor));
                return true;
            }
        }
        return false;
    }

    public static JSONObject getUser(String username) {
        String database = StorageUtil.getDataBase();

        JSONArray databaseJSON = new JSONArray(database);

        for (Object user : databaseJSON) {
            JSONObject userAsJSON = (JSONObject) user;
            if (userAsJSON.get("name").equals(username)) {
                return userAsJSON;
            }
        }
        return null;
    }

    private static String readFile(String filePath) {
        String data = null;
        try {
            FileInputStream file = new FileInputStream(filePath);
            data = IOUtils.toString(file, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private static void writeFile(String filePath, String data) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            IOUtils.write(data, fos, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
