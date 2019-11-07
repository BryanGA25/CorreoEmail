package Correo.model;

import javafx.collections.ObservableList;

import javax.mail.Message;
import javax.mail.MessagingException;

public class EmailsMensage {

    private Message mensaje;



    public String getAsunto(){

        String asunto="";
        try {
            asunto=mensaje.getSubject();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        return asunto;
    }

    public String getRemitente(){


        String remintente="";


        try {
            int remitentes=mensaje.getFrom().length;

            for(int i=0; i < remitentes;i++){

                remintente=mensaje.getFrom().toString();

            }

            return remintente;


        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;

    }


    public EmailsMensage(Message mensajes) {
        this.mensaje = mensajes;
    }
}
