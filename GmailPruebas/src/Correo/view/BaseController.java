package Correo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {

    private Stage stage;

    protected Stage getStage(){
        return stage;
    }

    protected BaseController cargarDialogo(String fxml, int anchura, int altura){
        try {
            //Cargar el FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = fxmlLoader.load();
            BaseController controller = fxmlLoader.getController();
            controller.cargarStage(root, "Cliente Corre", anchura, altura);
            return controller;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public void abrirDialogo(boolean wait){
        if(stage != null){
            if (wait) {
                stage.showAndWait();
            } else {
                stage.show();
            }
        }
        else{
            throw new IllegalStateException("Hay que llamar a cargarDialogo primero.");
        }

    }


    private void cargarStage(Parent root, String titulo, int anchura, int altura){
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(titulo);
        stage.setScene(new Scene(root, anchura, altura));
    }

}
