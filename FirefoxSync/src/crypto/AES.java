package crypto;


import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.GCMParameterSpec;
import model.EncryptedData;

import org.apache.commons.codec.binary.Hex;

public class AES {

    private static final int MAC_SIZE = 128; // in bits
    private String key = null;
    // AES-GCM parameters
    public static final int AES_KEY_SIZE = 128; // in bits
    public static final int GCM_NONCE_LENGTH = 12; // in bytes
    public static final int GCM_TAG_LENGTH = 16; // in bytes

    public AES(String key) {
        this.key = key;
    }

    public EncryptedData encrypt(String toEncrypt) throws Exception {

        byte[] K = AESUtils.toByteArray(key, AES_KEY_SIZE / 4);

        byte[] P = toEncrypt.getBytes();

        byte[] N = new byte[GCM_NONCE_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(N);
        
        Key key = new SecretKeySpec(K, "AES");

        Cipher in = Cipher.getInstance("AES/GCM/NoPadding", "BCFIPS");

        GCMParameterSpec​ gcmParameters = new GCMParameterSpec​(MAC_SIZE, N);
        in.init(Cipher.ENCRYPT_MODE, key, gcmParameters);

        byte[] enc = in.doFinal(P);
        String encryptedData = Hex.encodeHexString(enc);
        return new EncryptedData(encryptedData, Hex.encodeHexString(N));
    }

    public String decrypt(EncryptedData toDecrypt) throws Exception {
        
        byte[] key128bits = AESUtils.toByteArray(key, AES_KEY_SIZE / 4);

        byte[] K = key128bits;

        byte[] C = Hex.decodeHex(toDecrypt.data.toCharArray());

        byte[] N = Hex.decodeHex(toDecrypt.Nonce.toCharArray());
        Cipher out = Cipher.getInstance("AES/GCM/NoPadding", "BCFIPS");

        Key key = new SecretKeySpec(K, "AES");

        GCMParameterSpec​ gcmParameters = new GCMParameterSpec​(MAC_SIZE, N);
        out.init(Cipher.DECRYPT_MODE, key, gcmParameters);

        byte[] dec = out.doFinal(C);

        String decryptedData = AESUtils.toString(dec);
        return decryptedData;
    }

}
