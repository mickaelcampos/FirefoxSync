package crypto;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import model.ScryptHash;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.fips.Scrypt;
import org.bouncycastle.util.Strings;
import org.bouncycastle.crypto.KDFCalculator;

public class SCRYPTFIPS {

    public static final int costParameter = 2048;
    public static final int blocksize = 8;
    public static final int parallelizationParam = 1;

    public static byte[] useScryptKDF(char[] password,
            byte[] salt, int costParameter, int blocksize, int parallelizationParam) {

        KDFCalculator<Scrypt.Parameters> calculator
                = new Scrypt.KDFFactory()
                        .createKDFCalculator(
                                Scrypt.ALGORITHM.using(salt, costParameter, blocksize, parallelizationParam,
                                        Strings.toUTF8ByteArray(password)));
        byte[] output = new byte[32];
        calculator.generateBytes(output);
        return output;
    }

    /*Usado para gerar o salt  */
    public String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Hex.encodeHexString(salt);
    }

    public ScryptHash getHashedAuthToken(String authenticationToken) {
        byte[] derivedKeyFromScrypt = null;
        String salt = null;

        try {

            salt = this.getSalt();

            derivedKeyFromScrypt = SCRYPTFIPS.useScryptKDF(authenticationToken.toCharArray(),
                    salt.getBytes(),
                    costParameter,
                    blocksize,
                    parallelizationParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String derivedKey = Hex.encodeHexString(derivedKeyFromScrypt);
        return new ScryptHash(derivedKey, salt);
    }

    public ScryptHash getHashedAuthToken(String authenticationToken, String salt) {
        byte[] derivedKeyFromScrypt = null;

        try {

            if (salt == null) {
                salt = this.getSalt();
            }

            derivedKeyFromScrypt = SCRYPTFIPS.useScryptKDF(authenticationToken.toCharArray(),
                    salt.getBytes(),
                    costParameter,
                    blocksize,
                    parallelizationParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String derivedKey = Hex.encodeHexString(derivedKeyFromScrypt);
        return new ScryptHash(derivedKey, salt);
    }

}
