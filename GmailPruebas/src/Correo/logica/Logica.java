package Correo.logica;

import Correo.model.EmailsMensage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    public WebView mostrarCorreo(WebView we1){

        WebView web=we1;



        return web;
    }

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


