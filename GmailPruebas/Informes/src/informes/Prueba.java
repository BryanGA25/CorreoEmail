package informes;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.HashMap;
import java.util.Map;

public class Prueba {

    public static void main(String[] args) {
      new Prueba();
    }
    public Prueba(){
        try {
            JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(FactoryMensajes.createListMensaje(),false); //lista sería la colección a mostrar. Típicamente saldría de la lógica de nuestra aplicación
            Map<String,Object> parametros = new HashMap<>(); //En este caso no hay parámetros, aunque podría haberlos
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("CorreoInformes.jasper"), parametros, jr);
            JasperExportManager.exportReportToPdfFile(print, "informes/primerinforme.pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
