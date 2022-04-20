package model;

/**
 *
 * @author m1k4
 */
public class User {

    public String name;
    public ScryptHash scryptHash;
    public String[] data;
    public String salt;

     public User(String name, String salt) {
        this.name = name;
        this.salt = salt;
    }

    public void setScryptHash(ScryptHash scryptHash) {
        this.scryptHash = scryptHash;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public ScryptHash getScryptHash() {
        return scryptHash;
    }

    public String getSalt() {
        return salt;
    }

}
