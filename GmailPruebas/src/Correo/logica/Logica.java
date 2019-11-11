package Correo.logica;

import Correo.model.EmailTreeItem;
import Correo.model.EmailsMensage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

public class Logica  {

    private String user;
    private String contraseña;
    private static Logica INSTANCE = null;
    private Session session;
    private Store store;
    private Properties props;

    private ObservableList<EmailsMensage> listaCorreos;

    public Logica(){
        listaCorreos = FXCollections.observableArrayList();
    }

    public void logearse(String user, String contraseña){
        this.user=user;
        this.contraseña=contraseña;
    }

    public static Logica getInstance() {
        if(INSTANCE == null){
            INSTANCE = new Logica();
        }
        return INSTANCE;
    }

    public void  mostrarCorreo(WebView we1){




    }

    /* public TreeView getTreeView(){


     }

     public EmailTreeItem getFolders(EmailsMensage emailsMensage){
         EmailTreeItem treeItem=new EmailTreeItem(emailsMensage.getAddress),emailsMensage,null);
         Folder[] folders=emailsMensage.getStore().getDefaultFolder().list();
         getFolders(folders,treeItem,emailsMensage);

     }

     private void getFolders(Folder[] folders, EmailTreeItem treeItem, EmailTreeItem emailsMensage) {
         for (Folder folder:folders) {



         }
     }
 */
    public ObservableList<EmailsMensage> getListaCorreos(){

        props = new Properties();
        props.put("mail.imap.ssl.checkserveridentity", "false");
        props.put ("mail.imaps.ssl.trust", "*");

        try {

            session = Session.getDefaultInstance(props, null);

            store = session.getStore("imaps");
            store.connect("smtp.gmail.com", user, contraseña);

            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();

            for (Message mensaje:messages) {
                listaCorreos.add(new EmailsMensage(mensaje));
            }

            //inbox.close(true);
            //store.close();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listaCorreos;
    }


}


