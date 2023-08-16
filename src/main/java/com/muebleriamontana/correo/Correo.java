package com.muebleriamontana.correo;

import com.muebleriamontana.controller.pantallas.Envio_ListarDetallesController;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Correo {
    private static String remitente = "joseloyola3101@gmail.com";
    private static String clave = "lpbodsqyypcepwfr";
    private Address[] destinatarios;
    private String asunto;

    private StringBuilder contenido;

    private Properties mProperties;
    private Session mSession;
    private MimeMessage mCorreo;
    public Correo() {
    }

    public Correo(Address[] destinatarios, String asunto, StringBuilder contenido) {
        this.destinatarios = destinatarios;
        this.asunto = asunto;
        this.contenido = contenido;
        crearCorreo();
        enviarCorreo();
    }

    public void setDestinatarios(Address[] destinatarios) {
        this.destinatarios = destinatarios;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public void setContenido(StringBuilder contenido) {
        this.contenido = contenido;
    }

    private void crearCorreo() {
        mProperties = new Properties();
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable", "true");
        mProperties.setProperty("mail.smtp.port", "587");
        mProperties.setProperty("mail.smtp.user", remitente);
        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.auth", "true");
        mSession = Session.getDefaultInstance(mProperties);
        try {
            mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress(remitente));
            mCorreo.setRecipients(Message.RecipientType.TO, destinatarios);
            mCorreo.setSubject(asunto);
//            mCorreo.setText(String.valueOf(contenido), "ISO-8859-1", "html");
            mCorreo.setText(String.valueOf(contenido), "utf-8", "html");
        } catch (AddressException ex) {
            Logger.getLogger(Envio_ListarDetallesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Envio_ListarDetallesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void enviarCorreo() {
        try {
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(remitente, clave);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();
//            JOptionPane.showMessageDialog(null, "Correo enviado");
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Envio_ListarDetallesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Envio_ListarDetallesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
