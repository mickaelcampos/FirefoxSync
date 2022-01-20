import java.security.Security;
import java.util.Scanner;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.util.ArrayList; 

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
        print("3. Autenticar cliente no servidor Sync");
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
                break;
            case "5":
                break;
            default:
                throw new AssertionError();
        }
    }

    public void registerUser() {
        String username = this.askUserName();

        String password = this.askPassword();

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
        return this.input.nextLine(); // TODO validar se já existe usuario com esse nome
    }

    private String askPassword() {
        System.out.println("Digite uma senha:");
        String password = this.input.nextLine(); // TODO validar senha fraca
        System.out.println("Confirme a senha:");
        String confirmPassword = this.input.nextLine();
        if (this.passwordsMatch(password, confirmPassword)) {
            return password;
        } else {
            print("Senhas não conferem!");
            return this.askPassword();
        }
    }

    private boolean passwordsMatch(String password, String toCompare) {
        return password.equals(toCompare);
    }
    
    private void registerData() {
        print("Digite o dado a ser inserido:");
        this.data.add(this.input.nextLine());
    }

    private void print(String s) {
        System.out.println(s);
    }
}
