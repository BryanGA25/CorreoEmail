package Correo.model;

import javafx.collections.ObservableList;

import javax.mail.Message;
import javax.mail.MessagingException;

public class EmailsMensage {

    private Message mensaje;
    private String asunto;
    private String remitente;



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

                remitente=mensaje.getFrom().toString();

            }

            return remitente;


        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;

    }


    public EmailsMensage(Message mensajes) {
        this.mensaje = mensajes;
    }
}
