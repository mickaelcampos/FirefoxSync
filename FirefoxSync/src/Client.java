
import java.security.Security;
import java.util.Scanner;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.util.ArrayList;
import java.util.List;

public class Client {

    private Scanner input = new Scanner(System.in);

    private ArrayList<String> data = new ArrayList<String>();

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        new Client().mainMenu();
    }

    public void mainMenu() {
        print("1. Cadastrar dados do cliente");
        print("2. Cadastrar contas de clientes no servidor Sync");
        // print("3. Autenticar cliente no servidor Sync");
        print("4. Armazenar dados do cliente no servidor Sync");
        print("5. Obter dados e exibir na tela;");

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
                break;
            case "4":
                this.registerDataOnSyncServer();
                this.mainMenu();
                break;
            case "5":
                this.getDataAndShow();
                this.mainMenu();
                break;
            default:
                throw new AssertionError();
        }
    }

    private void registerUser() {
        String username = this.askUserName();

        String password = this.askNewPassword();

        String derivedKey = PBKDF2.getInstance().getDerivedKey(password);

        String hashedKeyDerivated = HMAC.getInstance().getDerivatedKey(derivedKey);

        String authenticationToken = hashedKeyDerivated;

        boolean isSignUpSuccess = SyncServer.getInstance().signUp(username, authenticationToken);

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
        String password = this.input.nextLine(); // TODO validar senha fraca
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
        
        String derivedKey = PBKDF2.getInstance().getDerivedKey(password);

        String hashedKeyDerivated = HMAC.getInstance().getDerivatedKey(derivedKey);

        String authenticationToken = hashedKeyDerivated;

        AES aes = new AES();
        try {
            print("[Client] this.data.toString(): " + this.data.toString());
            print("[Client] this.data.toString() LENGTH: " + this.data.toString().length());
            String encryptedData = aes.encrypt(this.data.toString());
            boolean isRegistered = SyncServer.getInstance().registerData(encryptedData, username, authenticationToken);
            if (isRegistered) {
                this.data.clear();
            }
            print(this.data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDataAndShow() {
        String username = this.askUserName();

        String password = this.askPassword();

        String derivedKey = PBKDF2.getInstance().getDerivedKey(password);

        String hashedKeyDerivated = HMAC.getInstance().getDerivatedKey(derivedKey);

        String authenticationToken = hashedKeyDerivated;

        List<Object> encryptedDataList = SyncServer.getInstance().getData(username, authenticationToken); // TODO enviar username/hashedtoken
        print("[Client] encryptedData: " + encryptedDataList);

        AES aes = new AES();
        try {
            String decryptedData = null;
            for (Object encryptedData : encryptedDataList) {
                decryptedData = aes.decrypt(encryptedData.toString());
                formatAndPrintDecrypt(decryptedData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void formatAndPrintDecrypt(String toFormat) {
        String formatted = toFormat.replace("[", "").replace("]", ""); //  batata, cachorro
        String[] splitted = formatted.split(","); // ["batata", "cachorro"]
        for (String string : splitted) {
            print("[Client] data: " + string);
        }
    }

    private void print(String s) {
        System.out.println(s);
    }
}
