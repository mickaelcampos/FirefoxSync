
import crypto.HMAC;
import crypto.PBKDF2;
import crypto.AES;
import java.security.Security;
import java.util.Scanner;
import java.util.ArrayList;
import model.DerivedKey;
import model.EncryptedData;
import model.User;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

public class Client {

    private Scanner input = new Scanner(System.in);

    private ArrayList<String> data = new ArrayList<String>();

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleFipsProvider());
        new Client().mainMenu();
    }

    public void mainMenu() {
        print("1. Cadastrar dados do cliente");
        print("2. Cadastrar contas de clientes no servidor Sync");
        print("3. Armazenar dados do cliente no servidor Sync");
        print("4. Obter dados e exibir na tela;");

        String opt = this.input.nextLine();
        this.handleWithOption(opt);
    }

    public void handleWithOption(String option) {
        switch (option) {
            case "1":
                this.registerData();
                this.mainMenu();
                return;
            case "2":
                this.registerUser();
                this.mainMenu();
                break;
            case "3":
                this.registerDataOnSyncServer();
                this.mainMenu();
                break;
            case "4":
                this.getDataAndShow();
                this.mainMenu();
                break;
            default:
                print("Selecione uma das opções da lista:");
                this.mainMenu();
        }
    }

    private void registerUser() {
        String username = this.askUserName();

        String password = this.askNewPassword();

        DerivedKey derivedKey = PBKDF2.getInstance().getDerivedKey(password);

        String key = derivedKey.getDerivedKey();
        String salt = derivedKey.getSalt();

        String hashedKeyDerivated = HMAC.getInstance().getDerivatedKey(key, salt);

        String authenticationToken = hashedKeyDerivated;
        
        User user = new User(username, salt);

        boolean isSignUpSuccess = SyncServer.getInstance().signUp(user, authenticationToken);

        if (isSignUpSuccess) {
            print("Usuário registrado com sucesso!");
        } else {
            print("Erro ao cadastrar usuário!");
        }

    }

    private String askUserName() {
        print("Digite o nome do seu usuário:");
        return this.input.nextLine();
    }

    private String askNewPassword() {
        print("Digite uma senha:");
        String password = this.input.nextLine();
        print("Confirme a senha:");
        String confirmPassword = this.input.nextLine();
        if (this.passwordsMatch(password, confirmPassword)) {
            return password;
        } else {
            print("Senhas não conferem!");
            return this.askNewPassword();
        }
    }

    private String askPassword() {
        print("Digite sua senha:");
        return this.input.nextLine();
    }

    private boolean passwordsMatch(String password, String toCompare) {
        return password.equals(toCompare);
    }

    private void registerData() {
        print("Digite o dado a ser inserido:");
        this.data.add(this.input.nextLine());
    }

    private void registerDataOnSyncServer() {

        String username = this.askUserName();

        String password = this.askPassword();

        String salt = SyncServer.getInstance().getSalt(username);

        DerivedKey derivedKey = PBKDF2.getInstance().getDerivedKey(password, salt);
        
        String key = derivedKey.getDerivedKey();
        
        String hashedKeyDerivated = HMAC.getInstance().getDerivatedKey(key, salt);

        String authenticationToken = hashedKeyDerivated;
        
        String encryptionKey = hashedKeyDerivated;

        AES aes = new AES(encryptionKey);
        try {
            EncryptedData encryptedData = aes.encrypt(this.data.toString());
            boolean isRegistered = SyncServer.getInstance().registerData(encryptedData, username, authenticationToken);
            if (isRegistered) {
                this.data.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDataAndShow() {
        String username = this.askUserName();

        String password = this.askPassword();
        
        String salt = SyncServer.getInstance().getSalt(username);

        DerivedKey derivedKey = PBKDF2.getInstance().getDerivedKey(password, salt);
        
        String key = derivedKey.getDerivedKey();

        String hashedKeyDerivated = HMAC.getInstance().getDerivatedKey(key, salt);

        String authenticationToken = hashedKeyDerivated;

        ArrayList<EncryptedData> encryptedDataList = SyncServer.getInstance().getData(username, authenticationToken);
        
        String encryptionKey = hashedKeyDerivated;

        AES aes = new AES(encryptionKey);
        try {
            String decryptedData = null;
            for (EncryptedData encryptedData : encryptedDataList) {
                decryptedData = aes.decrypt(encryptedData);
                formatAndPrintDecrypt(decryptedData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void formatAndPrintDecrypt(String toFormat) {
        String formatted = toFormat.replace("[", "").replace("]", "");
        String[] splitted = formatted.split(",");
        for (String string : splitted) {
            print("dado decifrado: " + string.trim());
        }
    }

    private void print(String s) {
        System.out.println(s);
    }
}
