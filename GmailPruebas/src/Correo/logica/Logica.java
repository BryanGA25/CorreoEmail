package Correo.logica;

import Correo.model.Cuenta;
import Correo.model.EmailTreeItem;
import Correo.model.EmailsMensage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.util.MimeMessageParser;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.time.chrono.MinguoChronology;
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
    private EmailTreeItem treeItem;

    public Logica(){
        listaCorreos = FXCollections.observableArrayList();
        treeItem= new EmailTreeItem("", null, null);
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
    public void rootcreate(Cuenta cuenta){
        Folder f = loadMail(cuenta);
        treeItem.getChildren().add(crearTreeView(cuenta, f));

    }
    private Folder loadMail(Cuenta mailAccount){
        try {
            props = new Properties();
            props.put("mail.imap.ssl.checkserveridentity", "false");
            props.put ("mail.imaps.ssl.trust", "*");
            Session emailSesion = Session.getDefaultInstance(props, null);
            store = emailSesion.getStore("imaps");
            store.connect("smtp.gmail.com", mailAccount.getCuenta(), mailAccount.getPassword());
            return store.getDefaultFolder();
        }catch(MessagingException e){
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<EmailsMensage> getListaCorreos(String direccion){
   /* if(folder!=null && folder.getType()=3){

    }*/
        try {
            props = new Properties();
            props.put("mail.imap.ssl.checkserveridentity", "false");
            props.put ("mail.imaps.ssl.trust", "*");
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
    public TreeItem crearTreeView(Cuenta cuenta,Folder folder) {
        EmailTreeItem emailroot=null;
            try {
                emailroot = new EmailTreeItem(cuenta.getCuenta(), cuenta, Logica.getInstance().getFolder());
                getFolders(((EmailTreeItem)emailroot).getFolder().list(), (EmailTreeItem)emailroot,cuenta);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

        return emailroot;

    }
    private void getFolders(Folder[] folders,EmailTreeItem objeto,Cuenta cuenta) {
        for (Folder folder : folders) {
            EmailTreeItem emailTreeItem = new EmailTreeItem(folder.getName(),cuenta  , folder);
            objeto.getChildren().add(emailTreeItem);
            try {
                if(folder.getType() == Folder.HOLDS_FOLDERS){
                    getFolders(folder.list(), emailTreeItem,cuenta);
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public  EmailTreeItem getTree(){
        return treeItem;
    }

    public MimeMessageParser getMimeMessageParser (Message mensaje){

        MimeMessage msg=null;
        try {
            msg=new MimeMessage((MimeMessage) mensaje);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        MimeMessageParser mimeMessageParser=new MimeMessageParser(msg);
        try {
            mimeMessageParser.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mimeMessageParser;
    }

}


