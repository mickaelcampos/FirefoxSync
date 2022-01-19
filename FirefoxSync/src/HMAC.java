
import java.security.GeneralSecurityException;
import javax.crypto.SecretKey;
import org.bouncycastle.util.encoders.Hex;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;


/**
 *
 * @author Carla Este exemplo mostra o uso da classe HMac da BouncyCastle.
 * Fevereiro 2021.
 */
public final class HMAC {
    
    private static HMAC instance = null;
    SecretKey key = null; // TODO rever, usar uma master key???

    public static HMAC getInstance() {
        if (instance == null) {
            instance = new HMAC();
        }
        return instance;
    }
    
    private HMAC() {
        try {
            key = generateKey(); // TODO rever
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SecretKey generateKey()
            throws GeneralSecurityException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA512", "BC");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static byte[] calculateHmac(SecretKey key, byte[] data)
            throws GeneralSecurityException {
        Mac hmac = Mac.getInstance("HMacSHA512", "BC");
        hmac.init(key);
        return hmac.doFinal(data);
    }

    public String getDerivatedKey(String toDerive) {
        String derivatedKey = null;
        try {
            // SecretKey key = generateKey(); // TODO Gerar uma chave unica???
        
            System.out.println("[HMAC] Chave = " + Hex.toHexString(key.getEncoded()));

            byte[] macValue = calculateHmac(key, toDerive.getBytes());
            derivatedKey = Hex.toHexString(macValue);

            System.out.println("[HMAC] String = " + toDerive);
            System.out.println("[HMAC] HMAC calculado = " + derivatedKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return derivatedKey;
    }

}
