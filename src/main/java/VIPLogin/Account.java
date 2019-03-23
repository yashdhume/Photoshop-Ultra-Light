package VIPLogin;

import java.io.Serializable;

public class Account implements Serializable {
    private String username;
    private String password;
    private boolean isVip;

    public Account(String username, String password, boolean isVip) {
        this.username = username;
        this.password = password;
        this.isVip = isVip;
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
            retVal= ptr.getUsername().equals(this.getUsername());
        }
       return retVal;
    }

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
        isVip = vip;
    }
}
