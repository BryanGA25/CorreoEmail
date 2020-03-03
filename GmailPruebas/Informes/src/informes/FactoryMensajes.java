package informes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FactoryMensajes {

    public static List<EmailsMensage> createListMensaje()
    {
        List<EmailsMensage> listaEmails = new ArrayList<>();
        listaEmails.add(new EmailsMensage("Prueba1","Bryan",new Date(),"Inbox"));
        listaEmails.add(new EmailsMensage("Prueba2","Bryan",new Date(),"Borrados"));

        return listaEmails;
    }



}
