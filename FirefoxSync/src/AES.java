
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
    public String encrypt(String toEncrypt)
            throws Exception {
        System.out.println("[AES] INPUT: " + toEncrypt);
        // Test Case 15 from McGrew/Viega
        //  chave (K)
        String pK = "feffe9928665731c6d6a8f9467308308feffe9928665731c6d6a8f9467308308";
        byte[] K = Hex.decodeHex(pK.toCharArray());

        //  texto plano (P)
        System.out.println("[AES] toEncrypt.getBytes(): " + toEncrypt.getBytes());
        // String toEncryptHex = Hex.encodeHexString(toEncrypt.getBytes());
        // byte[] P = Hex.decodeHex(toEncryptHex.toCharArray());

        byte[] P = toEncrypt.getBytes();

        //  nonce (IV)
        String pN;
        pN = "cafebabefacedbaddecaf888";
        byte[] N = Hex.decodeHex(pN.toCharArray());

        //  tag (T)
        String T = "b094dac5d93471bdec1a502270e3cc6c";

        Key key;

        Cipher in = Cipher.getInstance("AES/GCM/NoPadding", "BC");

        key = new SecretKeySpec(K, "AES");

        GCMParameterSpec​ gcmParameters = new GCMParameterSpec​(MAC_SIZE, N);
        in.init(Cipher.ENCRYPT_MODE, key, gcmParameters);
        // in.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(N));

        byte[] enc = in.doFinal(P);

        // String encryptedData = org.bouncycastle.util.encoders.Hex.toHexString(enc);
        String encryptedData = Hex.encodeHexString(enc);
        System.out.println("[AES] encryptedData: " + encryptedData);
        return encryptedData;
    }

    public String decrypt(String toDecrypt)
            throws Exception {
        System.out.println("[AES] OUTPUT: " + toDecrypt);
        // Test Case 15 from McGrew/Viega
        //  chave (K)
        String pK = "feffe9928665731c6d6a8f9467308308feffe9928665731c6d6a8f9467308308";
        byte[] K = Hex.decodeHex(pK.toCharArray());

        //  texto cifrado (C)
        // byte[] C = org.apache.commons.codec.binary.Hex.decodeHex(toDecrypt.toCharArray());
        // byte[] C = toDecrypt.getBytes();
        // String test = new String(toDecrypt.getBytes(), "ASCII");
        // System.out.println("[AES] test: " + test);
        // byte[] C = Hex.decodeHex(toDecrypt.toCharArray()); // FUNCIONA COM BUG
        byte[] C = Hex.decodeHex(toDecrypt.toCharArray());

        //  nonce (IV)
        String pN;
        pN = "cafebabefacedbaddecaf888";
        byte[] N = Hex.decodeHex(pN.toCharArray());

        //  tag (T)
        String T = "b094dac5d93471bdec1a502270e3cc6c";

        Key key;

        Cipher out = Cipher.getInstance("AES/GCM/NoPadding", "BC");

        key = new SecretKeySpec(K, "AES");

        GCMParameterSpec​ gcmParameters = new GCMParameterSpec​(MAC_SIZE, N);
        out.init(Cipher.ENCRYPT_MODE, key, gcmParameters);
        // out.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(N));

        byte[] dec = out.doFinal(C);

        // String encryptedData = org.bouncycastle.util.encoders.Hex.toHexString(enc);
        // String decryptedData = org.apache.commons.codec.binary.Hex.decodeHex(dec);
        // String decryptedData = Hex.encodeHex(dec).toString();
        String decryptedData = AESUtils.toString(dec);
        System.out.println("[AES] decryptedData: " + decryptedData);
        return decryptedData;
    }

}
