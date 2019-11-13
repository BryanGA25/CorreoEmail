package Correo.model;

public class Cuenta {
    private String cuenta;
    private String password;

    public Cuenta(String cuenta, String password){
        this.cuenta = cuenta;
        this.password = password;
    }

    public String getCuenta() {
        return cuenta;
    }
    public void setCuenta(String mail) {
        this.cuenta = mail;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
