package crypto;


import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.SecretKey;
import model.DerivedKey;

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
    private static String generateDerivedKey(
            String password, String salt, Integer iterations) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterations, 128);
        SecretKeyFactory pbkdf2 = null;
        String derivedPass = null;
        try {
            pbkdf2 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512", "BCFIPS");
            SecretKey sk = pbkdf2.generateSecret(spec);
            derivedPass = Hex.encodeHexString(sk.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return derivedPass;
    }

    /*Usado para gerar o salt  */
    private String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Hex.encodeHexString(salt);
    }

    public DerivedKey getDerivedKey(String password) {
        int it = 10000;
        String derivedKey = null;
        String salt = null;
        try {
            salt = this.getSalt();
            derivedKey = generateDerivedKey(password, salt, it);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new DerivedKey(derivedKey, salt);
    }

    public DerivedKey getDerivedKey(String password, String salt) {
        int it = 10000;
        String derivedKey = null;
        try {
            if (salt == null) {
                salt = this.getSalt();
            }
            derivedKey = generateDerivedKey(password, salt, it);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DerivedKey(derivedKey, salt);
    }

}
