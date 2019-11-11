package Correo.model;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;

import javax.mail.Folder;

public class EmailTreeItem extends TreeItem<String> {

    private String nombre;
    private EmailsMensage emailAccount;
    private Folder folder;

    public EmailTreeItem(String nombre, EmailsMensage emailAccount, Folder folder) {
        super();
        this.nombre = nombre;
        this.emailAccount = emailAccount;
        this.folder = folder;
    }

   /* public String getAddress(){

        emailAccount.
    }
    *
    */
}
