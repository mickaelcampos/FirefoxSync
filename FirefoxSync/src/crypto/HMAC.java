package crypto;


import java.security.GeneralSecurityException;
import javax.crypto.SecretKey;
import org.bouncycastle.util.encoders.Hex;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class HMAC {

    private static HMAC instance = null;

    public static HMAC getInstance() {
        if (instance == null) {
            instance = new HMAC();
        }
        return instance;
    }

    private HMAC() {
    }

    public SecretKey generateKey()
            throws GeneralSecurityException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA512", "BCFIPS");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static byte[] calculateHmac(SecretKey key, byte[] data)
            throws GeneralSecurityException {
        Mac hmac = Mac.getInstance("HMacSHA512", "BCFIPS");
        hmac.init(key);
        return hmac.doFinal(data);
    }

    public String getDerivatedKey(String toDerive, String salt) {
        String derivatedKey = null;
        try {
            SecretKey sk = new SecretKeySpec(Hex.decode(salt), "HmacSHA512");
            byte[] macValue = calculateHmac(sk, toDerive.getBytes());
            derivatedKey = Hex.toHexString(macValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return derivatedKey;
    }

}
