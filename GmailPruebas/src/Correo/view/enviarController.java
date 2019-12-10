package Correo.view;

import Correo.logica.Logica;
import Correo.model.Cuenta;
import Correo.model.EmailTreeItem;
import Correo.model.EmailsMensage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.web.HTMLEditor;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class enviarController extends BaseController implements Initializable {


    private Cuenta cuenta;
    private ArrayList<String>destinatarios;

    @FXML
    private TextField destinatario;

    @FXML
    private TextField asunto;

    @FXML
    private HTMLEditor htmleditor;

    public void añadirDestinatario(){
        destinatarios.add(destinatario.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        destinatarios=new ArrayList<>();


    }

    public void enviarCorreo(){
        int longitud=destinatarios.size();
        String[] destinatarios1=new String[longitud];

            for (int i = 0; i < destinatarios1.length; i++) {
                destinatarios1[i] = destinatarios.get(i);
            }

        Logica.getInstance().enviarCorreo(htmleditor.getHtmlText(),destinatarios1,asunto.getText(),cuenta);
            getStage().close();
    }

    public void reenviar(EmailsMensage mail) {

        String contenido = "---------- Mensaje reenviado ----------" +
                "<br>De: " + mail.getRemitente() +
                "<br>Fecha: " + mail.getFecha()  +
                "<br>Asunto: " + mail.getAsunto()  +
                "<br>Para: " + mail.getDestinatarios()[0] +
                "<br>" + mail.getContenido();
        destinatario.setText(mail.getDestinatarios()[0]);
        htmleditor.setHtmlText(contenido);
        asunto.setText("FW: " + mail.getAsunto());
        getStage().close();
    }

    public void responder(EmailsMensage mail) {

        destinatario.setText(mail.getDestinatarios()[0]);
        asunto.setText("Respuesta: " + mail.getAsunto());
        getStage().close();
    }

    public void sender(EmailTreeItem cuenta) {

        this.cuenta=cuenta.getEmailAccount();


    }
}
