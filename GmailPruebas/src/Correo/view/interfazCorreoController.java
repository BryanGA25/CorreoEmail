package Correo.view;

import Correo.logica.Logica;
import Correo.model.Cuenta;

import Correo.model.EmailTreeItem;
import Correo.model.EmailsMensage;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.docgene.help.JavaHelpFactory;
import org.docgene.help.gui.jfx.JFXHelpContentViewer;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.net.URL;
import java.util.*;


public class interfazCorreoController extends BaseController implements Initializable {

    ObservableList<EmailsMensage> listaCorreos;

    ArrayList<Cuenta> cuentas = new ArrayList<>();
    TreeItem raiz;

    private MimeMessage mime;

    private JFXHelpContentViewer viewer;

    @FXML
    private Button ayuda;
    @FXML
    ComboBox<String> temas;
    @FXML
    private TreeView<String> TreeView;

    @FXML
    private TableView<EmailsMensage> tablaCorreos;

    @FXML
    private WebView vistaEmail;

    public void logearse() {

        cargarDialogo("login.fxml", 600, 450).abrirDialogo(true);

        cuentas = Logica.getInstance().getCuentas();
        raiz = Logica.getInstance().getTree();
        for(Object child: raiz.getChildren()){
            expandTreeView((TreeItem<String>) child);
        }
        TreeView.setRoot(raiz);


        TreeView.setShowRoot(false);
        cargarTabla();

    }

    public void menuAlarmas(){
        cargarDialogo("alarmas.fxml",600,450).abrirDialogo(true);
    }

    public void enviar() {

        BaseController controller = cargarDialogo("correo.fxml", 800, 600);
        ((enviarController) controller).sender((EmailTreeItem)TreeView.getSelectionModel().getSelectedItem());
        controller.abrirDialogo(true);

    }
    @FXML
    void generarInformeAgrupado(ActionEvent event) {

        List<EmailsMensage> listaEmails= new ArrayList<>();

        for(int i=1; i<TreeView.getExpandedItemCount();i++){
            if (TreeView.getTreeItem(i).getValue().equalsIgnoreCase("INBOX")) {
                listaEmails.addAll(Logica.getInstance().getListaCorreos(TreeView.getTreeItem(i).getValue()));
            } else {
                if (!TreeView.getTreeItem(i).getValue().equalsIgnoreCase("[Gmail]")) {
                    String carpeta = "[Gmail]/" + TreeView.getTreeItem(i).getValue();
                    listaEmails.addAll(Logica.getInstance().getListaCorreos(carpeta));
                }
            }
        }




        if (!listaEmails.isEmpty()){
            try {

                JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(listaEmails); //lista sería la colección a mostrar. Típicamente saldría de la lógica de nuestra aplicación
                Map<String,Object> parametros = new HashMap<>(); //En este caso no hay parámetros, aunque podría haberlos
                JasperPrint print = JasperFillManager.fillReport(String.valueOf(new File("jasper/InformeAgrupado.jasper")), parametros, jr);
                JasperExportManager.exportReportToPdfFile(print, "InformeAgrupado.pdf");
            } catch (JRException e) {
                e.printStackTrace();
            }
        }else {
            Alert alerta= new Alert(Alert.AlertType.WARNING);
            alerta.setContentText("No hay correos para mostrar");
            alerta.showAndWait();
        }



    //cargarDialogo("informesCuenta.fxml",800,600).abrirDialogo(true);

    }

    @FXML
    void generarInformeMultiple(ActionEvent event) {
        List<EmailsMensage> listaEmails = new ArrayList<>();
        for (EmailsMensage e:tablaCorreos.getItems()) {
            listaEmails.add(e);
        }
        if (!listaEmails.isEmpty()){
            try {

                JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(listaEmails); //lista sería la colección a mostrar. Típicamente saldría de la lógica de nuestra aplicación
                Map<String,Object> parametros = new HashMap<>(); //En este caso no hay parámetros, aunque podría haberlos
                JasperPrint print = JasperFillManager.fillReport(String.valueOf(new File("jasper/CorreoInformes.jasper")), parametros, jr);
                JasperExportManager.exportReportToPdfFile(print, "InformeMultiple.pdf");
            } catch (JRException e) {
                e.printStackTrace();
            }
        }else {
            Alert alerta= new Alert(Alert.AlertType.WARNING);
            alerta.setContentText("No hay datos en la tabla para mostrar");
            alerta.showAndWait();
        }
    }

    @FXML
    void generarInformeUnico(ActionEvent event) {
        if (tablaCorreos.getSelectionModel().isEmpty()!=true){
            try {
                List<EmailsMensage> listaEmails = new ArrayList<>();
                listaEmails.add(tablaCorreos.getSelectionModel().getSelectedItem());
                JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(listaEmails); //lista sería la colección a mostrar. Típicamente saldría de la lógica de nuestra aplicación
                Map<String,Object> parametros = new HashMap<>(); //En este caso no hay parámetros, aunque podría haberlos
                JasperPrint print = JasperFillManager.fillReport(String.valueOf(new File("jasper/CorreoInformes.jasper")), parametros, jr);
                JasperExportManager.exportReportToPdfFile(print, "InformeUnico.pdf");
            } catch (JRException e) {
                e.printStackTrace();
            }
        }else {
            Alert alerta= new Alert(Alert.AlertType.WARNING);
            alerta.setContentText("No hay un correo seleccionado");
            alerta.showAndWait();
        }

    }

    public void reenviar(ActionEvent event) {


        EmailsMensage mensaje = tablaCorreos.getSelectionModel().getSelectedItem();
        BaseController controller = cargarDialogo("correo.fxml", 800, 600);
        ((enviarController) controller).sender((EmailTreeItem)TreeView.getSelectionModel().getSelectedItem());
        ((enviarController) controller).reenviar(mensaje);
        controller.abrirDialogo(true);


    }

    public void responder(ActionEvent event) {

        EmailsMensage mensaje = tablaCorreos.getSelectionModel().getSelectedItem();
        BaseController controller = cargarDialogo("correo.fxml", 800, 600);
        ((enviarController)  controller).sender((EmailTreeItem)TreeView.getSelectionModel().getSelectedItem());
        ((enviarController)   controller).responder(mensaje);
        controller.abrirDialogo(true);
    }

    private void cargarTabla() {

        listaCorreos = new Logica().getInstance().getListaCorreos("INBOX");
        tablaCorreos.setItems(listaCorreos);

    }

    @FXML
    private void verAsuntos(){

        cargarDialogo("asuntos.fxml", 600, 600).abrirDialogo(true);

    }

    private void initializeHelp(Stage stage)
    {
        try {
            URL url = new File("outAyudaApp/articles.zip").toURI().toURL();
            JavaHelpFactory factory = new JavaHelpFactory(url);
            factory.create();
            viewer = new JFXHelpContentViewer();
            factory.install(viewer);
            viewer.getHelpWindow(stage, "Help Content", 600, 700);
        }catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    private void iniciarAyuda(){
        viewer.showHelpDialog(ayuda);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeHelp(getStage());

        temas.setPromptText("Estilos");
        temas.getItems().addAll(Application.STYLESHEET_MODENA, Application.STYLESHEET_CASPIAN);
        temas.getSelectionModel().select(Application.getUserAgentStylesheet());
        temas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                Application.setUserAgentStylesheet(newValue);
            }
        });

        tablaCorreos.setRowFactory(new Callback<TableView<EmailsMensage>, TableRow<EmailsMensage>>() {
            @Override
            public TableRow<EmailsMensage> call(TableView<EmailsMensage> emailsMensageTableView) {
                return new TableRow<EmailsMensage>() {
                    @Override
                    protected void updateItem(EmailsMensage emailsMensage, boolean b) {
                        super.updateItem(emailsMensage, b);
                        if (emailsMensage != null) {
                            if (!emailsMensage.leido()) {
                                setStyle("-fx-font-weight:bold");
                            } else {
                                setStyle("");
                            }
                        }
                    }


                };


            }
        });



            tablaCorreos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmailsMensage>() {
                @Override
                public void changed(ObservableValue<? extends EmailsMensage> observableValue, EmailsMensage emailsMensage, EmailsMensage t1) {

                    String mensaje = t1.getContenido();
                    WebEngine web= vistaEmail.getEngine();
                    web.loadContent(mensaje);
                }


            });


            TreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
                @Override
                public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> stringTreeItem, TreeItem<String> t1) {


                    if (t1.getValue().equalsIgnoreCase("INBOX")) {
                        Logica.getInstance().getListaCorreos(t1.getValue());
                    } else {
                        String carpeta = "[Gmail]/" + t1.getValue();
                        Logica.getInstance().getListaCorreos(carpeta);
                    }



                }

            });

        }


    private void expandTreeView(TreeItem<?> item){
        if(item != null && !item.isLeaf()){
            item.setExpanded(true);
            for(TreeItem<?> child:item.getChildren()){
                expandTreeView(child);
            }
        }
    }
    }









