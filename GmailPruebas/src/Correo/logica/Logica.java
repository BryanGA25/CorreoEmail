package Correo.logica;

import Correo.model.Cuenta;
import Correo.model.EmailTreeItem;
import Correo.model.EmailsMensage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.util.MimeMessageParser;
import paqueteComponente.Reloj;
import paqueteComponente.Tarea;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.chrono.MinguoChronology;
import java.util.ArrayList;
import java.util.Properties;

public class Logica  {

    private String user;
    private String contraseña;
    private static Logica INSTANCE = null;
    private Session session=null;
    private Store store=null;
    private Properties props=new Properties();
    private ArrayList <Cuenta> cuentas=new ArrayList<>();
    private int cont=0;
        private ObservableList<EmailsMensage> listaCorreos;
    private EmailTreeItem treeItem;
    private ObservableList<Tarea> listaTareas;

    public Logica(){
        listaCorreos = FXCollections.observableArrayList();
        treeItem= new EmailTreeItem("", null, null);
        listaTareas=FXCollections.observableArrayList();
    }
    public void addTarea(Tarea tarea){
        listaTareas.add(tarea);
    }

    public ObservableList<Tarea> getListaTareas(){
        return listaTareas;
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
    public Folder loadMail(Cuenta mailAccount){
        try {
            props = new Properties();
            props.put("mail.imap.ssl.checkserveridentity", "false");
            props.put ("mail.imaps.ssl.trust", "*");
            propsEviar(mailAccount.getCuenta(), mailAccount.getPassword());
            store = session.getStore("imaps");
            store.connect("smtp.gmail.com", mailAccount.getCuenta(), mailAccount.getPassword());
            return store.getDefaultFolder();
        }catch(MessagingException e){
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<EmailsMensage> getListaCorreos(String direccion) {


        try {
            props = new Properties();
            props.put("mail.imap.ssl.checkserveridentity", "false");
            props.put("mail.imaps.ssl.trust", "*");
            propsEviar(cuentas.get(cont - 1).getCuenta(), cuentas.get(cont - 1).getPassword());
            store = session.getStore("imaps");
            store.connect("smtp.gmail.com", cuentas.get(cont - 1).getCuenta(), cuentas.get(cont - 1).getPassword());

            Folder inbox = store.getFolder(direccion);
            inbox.open(1);

            Message[] messages = inbox.getMessages();
            listaCorreos.clear();
            for (Message mensaje : messages) {
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
    public void getFolders(Folder[] folders,EmailTreeItem objeto,Cuenta cuenta) {
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

    public void enviarCorreo(String texto, String[] destinatario, String asunto, Cuenta cuenta){

        String host="smtp.gmail.com";
        final String usuario=cuenta.getCuenta();//change accordingly
        final String contraseña=cuenta.getPassword();//change accordingly


        //Compose the message
        propsEviar(usuario,contraseña);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usuario));
            for(int i=0;i<destinatario.length;i++) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario[i]));
            }
            message.setSubject(asunto);
            message.setText(texto);

            //send the message
            Transport.send(message);

            System.out.println("message sent successfully...");

            props.clear();

        } catch (MessagingException e) {e.printStackTrace();}
    }



    private  void propsEviar(String usuario, String contraseña){

        //Get the session object

        props.put("mail.smtp.user","username");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.EnableSSL.enable","true");

        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario,contraseña);
            }
        });

    }

}




