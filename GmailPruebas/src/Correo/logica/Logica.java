package Correo.logica;

import Correo.model.Cuenta;
import Correo.model.EmailsMensage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.mail.*;
import java.util.ArrayList;
import java.util.Properties;

public class Logica  {

    private String user;
    private String contraseña;
    private static Logica INSTANCE = null;
    private Session session;
    private Store store;
    private Properties props;
    private ArrayList <Cuenta> cuentas=new ArrayList<>();
    private int cont=0;
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

    public void setCuenta(Cuenta cuenta){

        cuentas.add(cont,cuenta);
        cont++;

    }
    public ArrayList<Cuenta> getCuentas(){

        return cuentas;
    }

    public Folder getFolder(){
        try {
            return store.getDefaultFolder();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<EmailsMensage> getListaCorreos(String direccion){

        props = new Properties();
        props.put("mail.imap.ssl.checkserveridentity", "false");
        props.put ("mail.imaps.ssl.trust", "*");

        try {

            session = Session.getDefaultInstance(props, null);

            store = session.getStore("imaps");
            store.connect("smtp.gmail.com", cuentas.get(cont-1).getCuenta(), cuentas.get(cont-1).getPassword());

            Folder inbox = store.getFolder(direccion);
            inbox. open(1);

            Message[] messages = inbox.getMessages();
            listaCorreos.clear();
            for (Message mensaje:messages) {
                listaCorreos.add(new EmailsMensage(mensaje));
            }



        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listaCorreos;
    }


}


