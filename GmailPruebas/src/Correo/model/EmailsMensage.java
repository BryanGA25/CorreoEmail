package Correo.model;
import javafx.collections.ObservableList;

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


