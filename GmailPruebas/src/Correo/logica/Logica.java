package Correo.logica;

import Correo.model.EmailsMensage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

public class Logica  {

    private static Logica INSTANCE = null;

    private ObservableList<EmailsMensage> listaCorreos;

    public Logica(){
        listaCorreos = FXCollections.observableArrayList();
    }

    public ObservableList<EmailsMensage> getListaCorreos(){

        Properties props = new Properties();
        props.put("mail.imap.ssl.checkserveridentity", "false");
        props.put ("mail.imaps.ssl.trust", "*");

        try {

            Session session = Session.getDefaultInstance(props, null);

            Store store = session.getStore("imaps");
            store.connect("smtp.gmail.com", "bryangallegoclases@gmail.com", "250698tineo");

            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();

            for (Message mensaje:messages) {
                listaCorreos.add(new EmailsMensage(mensaje));
            }

            inbox.close(true);
            store.close();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


}


