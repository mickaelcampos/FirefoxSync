

package model;

/**
 *
 * @author m1k4
 */
public class ScryptHash {
    public String hash;
    public String salt;

    public ScryptHash(String hash, String salt) {
        this.hash = hash;
        this.salt = salt;
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }
    
    
}
