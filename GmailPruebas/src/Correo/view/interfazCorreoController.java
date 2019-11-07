package Correo.view;

import Correo.logica.Logica;
import Correo.model.EmailsMensage;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import javax.mail.Message;
import java.net.URL;
import java.util.ResourceBundle;

public class interfazCorreoController implements Initializable {

        ObservableList<EmailsMensage> listaCorreos;

        @FXML
        private TableView<EmailsMensage> tablaCorreos;


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

                cargarTabla();


        }

        private void cargarTabla() {
                listaCorreos= new Logica().getInstance().getListaCorreos();
                tablaCorreos.setItems(listaCorreos);

        }
}
