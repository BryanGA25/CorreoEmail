package Correo.model;

import javafx.scene.control.TreeItem;

import javax.mail.Folder;

public class EmailTreeItem extends TreeItem<String> {

    private String nombre;
    private Cuenta emailAccount;
    private  Folder folder;

    public EmailTreeItem(String nombre, Cuenta   emailAccount, Folder folder) {
        super(nombre);
        this.nombre = nombre;
        this.emailAccount = emailAccount;
        this.folder = folder;
    }


    public Folder getFolder() {
        return folder;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Cuenta getEmailAccount() {
        return emailAccount;
    }

    public void setEmailAccount(Cuenta emailAccount) {
        this.emailAccount = emailAccount;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }


}
