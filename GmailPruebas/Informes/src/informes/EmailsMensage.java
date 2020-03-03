package informes;

import java.util.Arrays;
import java.util.Date;

public class EmailsMensage {

    private String asunto;
    private String remitente;
    private String contenido;
    private Date fecha;
    private String carpeta;

    public EmailsMensage(String asunto, String remitente, Date fecha,String carpeta) {
        this.asunto = asunto;
        this.remitente = remitente;
        this.fecha = fecha;
        this.carpeta=carpeta;

    }
    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(String carpeta) {
        this.carpeta = carpeta;
    }
}


