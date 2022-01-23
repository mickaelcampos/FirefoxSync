
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class StorageUtil {

    static final int indentFactor = 2;
    static final String dataBasePath = System.getProperty("user.dir") + "/src/database.json";

    public static String getDataBase() {
        return StorageUtil.readFile(dataBasePath); // TODO pode falhar se database.json nao existir, criar neste caso
    }

    public static boolean saveUser(String username, String authToken) {
        String database = StorageUtil.getDataBase();

        JSONArray databaseJSON = new JSONArray(database);

        for (Object user : databaseJSON) {
            JSONObject userAsJSON = (JSONObject) user;
            if (userAsJSON.get("name").equals(username)) {
                return false;
            }
        }

        databaseJSON.put(new JSONObject()
                .put("name", username)
                .put("authorizationToken", authToken)
        );
        StorageUtil.writeFile(dataBasePath, databaseJSON.toString(indentFactor));
        // TODO armazenar hashedAuthToken/username em arquivo usando criptografia autenticada? Rever necessidade
        return true;
    }

    public static boolean saveData(String data) {
        String database = StorageUtil.getDataBase();

        JSONArray databaseJSON = new JSONArray(database);

        for (int i = 0; i < databaseJSON.length(); i++) {
            JSONObject userAsJSON = (JSONObject) databaseJSON.getJSONObject(i);
            if (userAsJSON.get("name").equals("mickael")) { // TODO parametrizar o username
                userAsJSON.append("data", data);
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
