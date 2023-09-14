package core;

public class User {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private Account account;


    public User(String username, String password, String fullName, String email, Account account) {
        setUsername(username);
        setPassword(password);
        setFullName(fullName);
        setEmail(email);
        this.account = account;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
/*         if (username.contains(" ")) {  // må adde username already taken også.
            throw new IllegalArgumentException();
        } */
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
/*         if (password.length() < 8 || !password.matches("[a-zA-Z0-9!@#\\$%^&*()\\-_=+\\[\\]{}|;:\"',.<>/?~]+")) { //passord må være minst 8 tegn
            throw new IllegalArgumentException();
        } */
        this.password = password;
    }


    public String getFullName() {
        return fullName;
    }


    public void setFullName(String fullName) {
/*         if (!fullName.matches("[a-zA-Z] + ' '")) { // fullt navn må inneholde mellomrom og kan kun inneholde bokstaver
            throw new IllegalArgumentException();
        } */
        this.fullName = fullName;
    }


    public String getEmail() {
        return email;
    }

    private boolean validSymbols(String stringToCheck) {
        return stringToCheck.matches("^[a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]+(\\.[a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]+)*$");
        
    }

    public void setEmail(String email) {
/*         if (!email.contains("@") || validSymbols(email.substring(0, email.indexOf("@")))) { //email må inneholde "@", og kan ikke inneholde andre symboler enn de oppgitt.
            throw new IllegalArgumentException();
        } */
        this.email = email;
    }


    public Account getAccount() {
        return account;
    }


    public void setAccount(Account account) {
        this.account = account; 
    }

    

    

    
}
