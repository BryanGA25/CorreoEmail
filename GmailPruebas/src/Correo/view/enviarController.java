package Correo.view;

import Correo.logica.Logica;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class enviarController extends BaseController implements Initializable {

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
        for (int i=0;i<destinatarios1.length;i++) {
            destinatarios1[i]=destinatarios.get(i);
        }
        Logica.getInstance().enviarCorreo(htmleditor.getHtmlText(),destinatarios1,asunto.getText());
    }

}
