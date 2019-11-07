package Correo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Launcher extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/interfazCorreo.fxml"));
        stage.setTitle("Pantalla Correo");
        stage.setScene(new Scene(root, 750, 750));
        stage.show();
    }

        public static void main(String[] args){
            launch();
        }

}