
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.GCMParameterSpec;

import org.apache.commons.codec.binary.Hex;

public class AES {

    private static final int MAC_SIZE = 128; // in bits

    // AES-GCM parameters
    public static final int AES_KEY_SIZE = 128; // in bits
    public static final int GCM_NONCE_LENGTH = 12; // in bytes
    public static final int GCM_TAG_LENGTH = 16; // in bytes

    // TODO parametros com metodos do Utils
    public String encrypt(String toEncrypt) throws Exception {
        System.out.println("[AES] INPUT: " + toEncrypt); // [batata]
        String pK = "feffe9928665731c6d6a8f9467308308feffe9928665731c6d6a8f9467308308";
        byte[] K = Hex.decodeHex(pK.toCharArray());

        System.out.println("[AES] toEncrypt.getBytes(): " + toEncrypt.getBytes());

        byte[] P = toEncrypt.getBytes();

        String pN;
        pN = "cafebabefacedbaddecaf888";
        byte[] N = Hex.decodeHex(pN.toCharArray());

        Key key;

        Cipher in = Cipher.getInstance("AES/GCM/NoPadding", "BC");

        key = new SecretKeySpec(K, "AES");

        GCMParameterSpec​ gcmParameters = new GCMParameterSpec​(MAC_SIZE, N);
        in.init(Cipher.ENCRYPT_MODE, key, gcmParameters);

        byte[] enc = in.doFinal(P);
        String encryptedData = Hex.encodeHexString(enc);
        System.out.println("[AES] encryptedData: " + encryptedData); // d07e92a100a61abf08a2e0695feff3c4da2650986222158d
        return encryptedData;
    }

    public String decrypt(String toDecrypt) throws Exception {
        System.out.println("[AES] OUTPUT: " + toDecrypt);

        String pK = "feffe9928665731c6d6a8f9467308308feffe9928665731c6d6a8f9467308308";
        byte[] K = Hex.decodeHex(pK.toCharArray());

        byte[] C = Hex.decodeHex(toDecrypt.toCharArray());

        String pN;
        pN = "cafebabefacedbaddecaf888";
        byte[] N = Hex.decodeHex(pN.toCharArray());

        Key key;

        Cipher out = Cipher.getInstance("AES/GCM/NoPadding", "BC");

        key = new SecretKeySpec(K, "AES");

        GCMParameterSpec​ gcmParameters = new GCMParameterSpec​(MAC_SIZE, N);
        out.init(Cipher.DECRYPT_MODE, key, gcmParameters);

        byte[] dec = out.doFinal(C);

        String decryptedData = AESUtils.toString(dec);
        System.out.println("[AES] decryptedData: " + decryptedData);
        return decryptedData;
    }

}
