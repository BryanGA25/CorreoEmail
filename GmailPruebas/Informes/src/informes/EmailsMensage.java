package informes;

import java.util.Arrays;
import java.util.Date;

public class EmailsMensage {

    private String asunto;
    private String remitente;
    private String contenido;
    private Date fecha;


    public EmailsMensage(String asunto, String remitente, Date fecha,String contenido) {
        this.asunto = asunto;
        this.remitente = remitente;
        this.fecha = fecha;
        this.contenido = contenido;

    }
    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
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
}


