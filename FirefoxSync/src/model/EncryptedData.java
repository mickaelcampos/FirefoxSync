
package model;

/**
 *
 * @author m1k4
 */
public class EncryptedData {
    public String data;
    public String Nonce;

    public EncryptedData(String data, String Nonce) {
        this.data = data;
        this.Nonce = Nonce;
    }

}
