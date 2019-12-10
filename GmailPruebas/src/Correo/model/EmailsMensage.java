package Correo.model;
import javafx.collections.ObservableList;
import org.apache.commons.mail.util.MimeMessageParser;

import javax.mail.*;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;

public class EmailsMensage{

    private Message mensaje;
    private String asunto;
    private String remitente;
    private Date fecha;


    public String getContenido(){
        MimeMessageParser parser = new MimeMessageParser((MimeMessage) mensaje      );
        try {
            parser.parse();
            if(parser.getHtmlContent() != null){
                return parser.getHtmlContent();
            }
            else{
                return parser.getPlainContent();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String[] getDestinatarios(){
        String[] destinatarios;
        try {
            destinatarios = new String[mensaje.getAllRecipients().length];
            for(int i=0; i<mensaje.getAllRecipients().length;i++){
                destinatarios[i] = mensaje.getAllRecipients()[i].toString();
            }
            return destinatarios;
        } catch (MessagingException e) {
            e.printStackTrace();

        }
        return null;
    }

    public Date getFecha(){

        try {
            fecha=mensaje.getSentDate();

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return fecha;


    }

    public boolean leido(){

        try {
            return mensaje.isSet(Flags.Flag.SEEN);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return true;
    }

    public String getAsunto(){

        asunto="";
        try {
            asunto=mensaje.getSubject();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        return asunto;
    }



    public String getRemitente(){


        remitente="";


        try {
            int remitentes=mensaje.getFrom().length;

            for(int i=0; i < remitentes;i++){

                remitente= Arrays.toString(mensaje.getFrom());

            }

            return remitente;


        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;

    }

    public Message getMensaje(){
        return mensaje;
    }

    public EmailsMensage(Message mensajes) {
        this.mensaje = mensajes;
    }
}


