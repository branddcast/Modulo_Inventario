/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author BrandCast
 */
public class example {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String cadena = "branddcast.estaesmicontraseña.30-10-1996 08:15:25.30.";
        
        System.out.println("size = "+cadena.length());
        
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
        
        System.out.println(array[0]);
        System.out.println(array[1]);
        System.out.println(array[2]);
        System.out.println(array[3]);
    }
    
}
