/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Base64;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author BrandCast
 */
public class Session {

    private static String usuario = "";
    private static String contrasenia = "";
    private static Date fecha = null;
    private static boolean sesion = false;
    private static byte[] token = null;
    private static int caducidad_min = 59;
    
    private static String dirWeb = "api.timsa.x10.mx";
    private static int puerto = 80;
    
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final Charset ISO = Charset.forName("ISO-8859-1");
    
    
    public static String encodeUTF8(String str){
        String string = "";
        try {
            string = new String(str.getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return string;
    }
    
    public static boolean isConectado(){
        
     try {
            Socket s = new Socket(dirWeb, puerto);
            if(s.isConnected()){
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return false;
    }

    /**
     * @return the usuario
     */
    public static String getUsuario() {
        return usuario;
    }

    /**
     * @param usr the usuario to set
     */
    public static void setUsuario(String usr) {
        usuario = usr;
    }

    /**
     * @return the contraseña
     */
    public static String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contra the contraseña to set
     */
    public static void setContrasenia(String contra) {
        contrasenia = contra;
    }

    /**
     * @return the fecha
     */
    public static Date getFecha() {
        return fecha;
    }

    /**
     * @param fec the fecha to set
     */
    public static void setFecha(Date fec) {
        fecha = fec;
    }

    /**
     * @return the sesion
     */
    public static boolean isSesion() {
        return sesion;
    }

    /**
     * @param sesion_ the sesion to set
     */
    public static void setSesion(boolean sesion_) {
        sesion = sesion_;
    }
    

    /**
     * @return the token
     */
    public static byte[] getToken() {
        
       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       String cadena = usuario+"."+contrasenia+"."+dateFormat.format(fecha)+"."+caducidad_min+".";
       byte[] encodedBytes = Base64.getEncoder().encode(cadena.getBytes());
        
       //String token = new String(encodedBytes);
       token = encodedBytes;
       return encodedBytes;
    }
    
    public static boolean isCaducado(){
        byte[] encodedBytes = Base64.getDecoder().decode(getToken());
        
        String cadena = new String(encodedBytes);
        
        int desde = 0;
        
        String array[] = new String[4];
        int cont = 0;
                
        
        for(int i = 0; i<cadena.length(); i++){
            char caracter = cadena.charAt(i);
            if(caracter == '.'){
                array[cont] = cadena.substring(desde,i);
                desde = i+1;
                cont++;
            }
        }
        
        if(!array[0].equals(usuario)){
            return true;
        }else if(!array[1].equals(contrasenia)){
            return true;
        }
        
        //Fecha actual del sistema
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        
        int fecha_[] = new int[4];
       
        
        try {
            fecha_ = getDiferencia(dateFormat.parse(array[2]), dateFormat.format(date));
        } catch (ParseException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //System.out.println("Hay "+fecha_[0]+" dias, "+fecha_[1]+" horas, "+fecha_[2]+" minutos y "+fecha_[3]+" segundos de diferencia");
        
        //sCadena.substring(5,10);
        
        if(fecha_[0] == 0){
            if(fecha_[1] == 0){
                if(fecha_[2] < Integer.parseInt(array[3])){
                    return false;
                }else{
                    return true;
                }
            }else{
                return true;
            }
        }else{
            return true;
        }
    }
    
    public static int[] getDiferencia(Date fecha_inicial, String fecha_final){
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int diferencia_[] = new int[4];
        
        int diferencia = 0;
        try {
            diferencia = (int) ((dateFormat.parse(fecha_final).getTime()-fecha_inicial.getTime())/1000);
        } catch (ParseException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }

 

        if(diferencia>86400) {

            diferencia_[0]=(int)Math.floor(diferencia/86400);

            diferencia=diferencia-(diferencia_[0]*86400);

        }

        if(diferencia>3600) {

            diferencia_[1]=(int)Math.floor(diferencia/3600);

            diferencia=diferencia-(diferencia_[1]*3600);

        }

        if(diferencia>60) {

            diferencia_[2]=(int)Math.floor(diferencia/60);

            diferencia_[3]=diferencia-(diferencia_[2]*60);

        }

        return diferencia_;
    }


    
}
