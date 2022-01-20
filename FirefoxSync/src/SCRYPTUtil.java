import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Hex;


public class SCRYPTUtil {

    /*Usado para gerar o salt  */
    public byte[] getSalt() throws NoSuchAlgorithmException { // TODO comparar todos os metodos para criar salt
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        //SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
    
    public String getHashedAuthToken(String authenticationToken) {
        byte[] derivedKeyFromScrypt = null;

        try {
            // TODO fixar esse salt???
            byte[] salt = this.getSalt(); // 128 bits - 16 bytes

            int costParameter = 2048; // exemplo: 2048 (afeta uso de memória e CPU)

            int blocksize = 8; // exemplo: 8

            int parallelizationParam = 1; // exemplo: 1

            derivedKeyFromScrypt = SCRYPT.bcSCRYPT(authenticationToken.toCharArray(), salt, costParameter,
                blocksize, parallelizationParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("[SCRYPTUtil] hashedAuthToken: " + Hex.encodeHexString(derivedKeyFromScrypt));
        return Hex.encodeHexString(derivedKeyFromScrypt);
    }
}
