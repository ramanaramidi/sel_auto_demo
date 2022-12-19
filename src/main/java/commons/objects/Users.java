package commons.objects;

public class Users {

    private String userName;
    private String password;
    private String ntCode;
    private String user;
    private String isContractor;

    public String getIsServiceAccount() {
        return isServiceAccount;
    }

    public void setIsServiceAccount(String isServiceAccount) {
        this.isServiceAccount = isServiceAccount;
    }

    private String isServiceAccount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNtCode() {
        return ntCode;
    }

    public void setNtCode(String ntCode) {
        this.ntCode = ntCode;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setIsContractor(String user){
        this.isContractor = user;
    }

    public String getIsContractor(){
        return isContractor;
    }


}
