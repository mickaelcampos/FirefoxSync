package model;

/**
 *
 * @author m1k4
 */
public class DerivedKey {

    public String derivedKey;
    public String salt;

    public DerivedKey(String derivedKey, String salt) {
        this.derivedKey = derivedKey;
        this.salt = salt;
    }

    public String getDerivedKey() {
        return derivedKey;
    }

    public String getSalt() {
        return salt;
    }
}
