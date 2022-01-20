import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.SecretKey;


public final class PBKDF2 {

    private static PBKDF2 instance = null;

    public static PBKDF2 getInstance() {
        if (instance == null) {
            instance = new PBKDF2();
        }
        return instance;
    }

    private PBKDF2() {
    }

    /**
     * Gerar chave derivada da senha
     *
     * @param key
     * @param salt
     * @param iterations
     * @return
     */
    public static String generateDerivedKey(
            String password, String salt, Integer iterations) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterations, 128);
        SecretKeyFactory pbkdf2 = null;
        String derivedPass = null;
        try {
            pbkdf2 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512", "BC");
            SecretKey sk = pbkdf2.generateSecret(spec);
            derivedPass = Hex.encodeHexString(sk.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return derivedPass;
    }

    /*Usado para gerar o salt  */
    public String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Hex.encodeHexString(salt);
    }

    public String getDerivedKey(String password) {
        int it = 10000;
        String derivedKey = null;

        try {
            String salt = "123456"; // TODO usar um salt fixo?
            // String salt = this.getSalt();
            derivedKey = generateDerivedKey(password, salt, it);

            System.out.println("[PBKDF2] Chave derivada da senha = " + derivedKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return derivedKey;
    }

}
