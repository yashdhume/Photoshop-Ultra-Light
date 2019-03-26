package VIPLogin;

import java.io.Serializable;

public class Account implements Serializable {
    private String username;
    private String password;
    private boolean isVip;
    private boolean isLoggedIn;



    public Account(String username, String password, boolean isVip, boolean isLoggedIn) {
        this.username = username;
        this.password = password;
        this.isVip = isVip;
        this.isLoggedIn = isLoggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        boolean retVal = false;
        if(obj instanceof Account){
            Account ptr =(Account) obj;
            retVal = ptr.getUsername().equals(this.getUsername());
        }
        return retVal;
    }

    public boolean isLoggedIn() { return isLoggedIn; }

    public void setLoggedIn(boolean loggedIn) { isLoggedIn = loggedIn; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        this.isVip = vip;
    }
}
