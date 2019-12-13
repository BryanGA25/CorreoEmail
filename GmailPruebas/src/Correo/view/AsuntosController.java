package Correo.view;

import Correo.logica.Logica;
import Correo.model.EmailsMensage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AsuntosController extends  BaseController implements Initializable {

    ObservableList<EmailsMensage> listaCorreos;

    @FXML
    private ComboBox<String> correos;

    @FXML
    private Button visualizar;

    @FXML
    void visualizar(ActionEvent event) {


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información correo");
        alert.setHeaderText("Asunto");
        alert.setContentText(listaCorreos.get(correos.getSelectionModel().getSelectedIndex()).getAsunto());
        alert.show();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listaCorreos=Logica.getInstance().getListaCorreos("INBOX");

        for (int i=0;i<listaCorreos.size();i++){
            String remitente=listaCorreos.get(i).getRemitente();
            String fecha= String.valueOf(listaCorreos.get(i).getFecha());
            correos.getItems().add(remitente+","+fecha);
        }

    }
}
