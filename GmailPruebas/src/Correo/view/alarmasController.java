package Correo.view;

import Correo.logica.Logica;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import paqueteComponente.Tarea;

import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;


public class alarmasController extends BaseController implements  Initializable{
    @FXML
    private Button crearAlarma;

    @FXML
    private TextField horaAlarma;

    @FXML
    private DatePicker diaAlarma;

    @FXML
    private TableView<Tarea> tablaAlarmas;

    @FXML
    private TextField mensaje;

    @FXML
    private Button borrarAlarma;

    private ObservableList<Tarea> listaTareas;

    @FXML
    private  void añadirTarea(){

        String alarma=horaAlarma.getText();

        Date date=Date.from(diaAlarma.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        int hora=Integer.parseInt(alarma.substring(0,2));
        int minutos=Integer.parseInt(alarma.substring(3,5));
        int segundos=Integer.parseInt(alarma.substring(6,8));
        LocalTime localTime=LocalTime.of(hora,minutos,segundos);


        Tarea tarea=new Tarea(mensaje.getText(),localTime,date);
        Logica.getInstance().addTarea(tarea);

        diaAlarma.getEditor().setText("");
        horaAlarma.setText("");
        mensaje.setText("");
        cargarTabla();
    }

    public void cargarTabla(){

        listaTareas=Logica.getInstance().getListaTareas();
        tablaAlarmas.setItems(listaTareas);
    }

    public void borrarAlarma(){
        listaTareas.remove(listaTareas.get(tablaAlarmas.getSelectionModel().getFocusedIndex()));
        Logica.getInstance().borrarTarea(tablaAlarmas.getSelectionModel().getSelectedIndex());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarTabla();

    }
}
