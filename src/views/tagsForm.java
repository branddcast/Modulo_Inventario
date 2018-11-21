/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import entities.FindTag;
import static entities.FindTag.getPosition;
import static entities.FindTag.setPosition;
import static entities.Session.encodeUTF8;
import static entities.Session.isCaducado;
import static entities.Session.getToken;
import static entities.Session.isConectado;
import static java.awt.EventQueue.invokeLater;
import java.awt.Font;
import static java.awt.Font.PLAIN;
import java.awt.Image;
import java.awt.Toolkit;
import static java.lang.System.out;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;
import modulo.inventario.TextPrompt;
import modulo.inventario.config.DB;

/**
 *
 * @author BrandCast
 */
public class tagsForm extends javax.swing.JFrame {
    
    DB http;
    Gson gson;
    private int categoria_id;
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("src/logo.png"));


        return retValue;
    }
    
    TimerTask isCaducado = new TimerTask()
     {
         /**
          * Método al que Timer llamará cada segundo. Se encarga de avisar
          * a los observadores de este modelo.
          */
         public void run() 
         {
            if(isCaducado()){
                dispose();
            }
         }
     };
    
    
    TimerTask isConectado = new TimerTask()
     {
         /**
          * Método al que Timer llamará cada segundo. Se encarga de avisar
          * a los observadores de este modelo.
          */
         public void run() 
         {
            if(isConectado()== false){
                JOptionPane.showMessageDialog(null, "No hay conexión a internet");
            }
         }
     };

    /**
     * Creates new form tagsForm
     */
    
    public void deleteTag(int position) {
        String JSON = "", url = "", urlParameters = "";
        JsonParser parser = new JsonParser();
        http = new DB();
        
        //Especificamos la url para request
        url = "http://api.timsa.x10.mx/deleteTag.php";
            
        //Especificamos los parámetros 
        urlParameters = "id="+position+"&token="+new String(getToken());
        
        try {
                //JSON = gson.toJson(http.sendPost(url, urlParameters));
                JSON = http.sendPost(url, urlParameters);
            } catch (Exception ex) {
                showMessageDialog(null,"Ha ocurrido un error al enviar información. \nRevise su conexión a internet e intente de nuevo\nError: "+ex);
                getLogger(login.class.getName()).log(SEVERE, null, ex);
            }
            
            JsonElement elementObject = parser.parse(JSON);
            int estatus    = elementObject.getAsJsonObject().get("status").getAsInt();
            String mensaje = elementObject.getAsJsonObject().get("mensaje").getAsString();
            
            if(estatus == 3){
                    JOptionPane.showMessageDialog(null, "¡Sesión expirada! Ingrese credenciales otra vez.");
                    System.exit(0);
                }
            
            if(estatus == 1){
                showMessageDialog(null, mensaje);
                this.dispose();
            }else{
                showMessageDialog(null, mensaje);
            }
    }
    
    public tagsForm() {
        initComponents();
        this.setLocationRelativeTo(null);
        TextPrompt nombre = new TextPrompt("Categoría", nombre_txt);
        nombre.changeAlpha(0.5f);
        nombre.changeStyle(PLAIN);
        TextPrompt descripcion = new TextPrompt("Describir", descripcion_txt);
        descripcion.changeAlpha(0.5f);
        descripcion.changeStyle(PLAIN);
        
        Timer caducar = new Timer();
        caducar.scheduleAtFixedRate(isCaducado, 0, 1000);
        
        Timer conectar = new Timer();
        conectar.scheduleAtFixedRate(isConectado, 0, 1000);
        
        /*id_txt_2.setText(Integer.toString(this.categoria_id));
        id_txt.setText(Integer.toString(this.categoria_id));*/
         
        this.categoria_id = getPosition();
        
        if(this.categoria_id != 0){
            http = new DB();
        
            //Especificamos la url para request
            String url = "http://api.timsa.x10.mx/getTagById.php";
            
            //Especificamos los parámetros 
            String urlParameters = "id="+this.categoria_id+"&token="+new String(getToken());

            String JSON = "";
            JsonParser parser = new JsonParser();

            try {
                //JSON = gson.toJson(http.sendPost(url, urlParameters));
                JSON = http.sendPost(url, urlParameters);
            } catch (Exception ex) {
                showMessageDialog(null,"Ha ocurrido un error al cargar información. \nRevise su conexión a internet e intente de nuevo\nError: "+ex);
                getLogger(login.class.getName()).log(SEVERE, null, ex);
            }
            
            JsonElement elementObject = parser.parse(JSON);
            String name = elementObject.getAsJsonObject().get("nombre").getAsString();
            String desc = elementObject.getAsJsonObject().get("descripcion").getAsString();
            int status =  elementObject.getAsJsonObject().get("status").getAsInt();
            
            if(status == 3){
                    JOptionPane.showMessageDialog(null, "¡Sesión expirada! Ingrese credenciales otra vez.");
                    System.exit(0);
                }
            
            nombre_txt.setText(name);
            descripcion_txt.setText(desc);
            
            delete_btn.setVisible(true);
            delete_separator.setVisible(true);

        }else{
            delete_btn.setVisible(false);
            delete_separator.setVisible(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_contra = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        nombre_txt = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        delete_separator = new javax.swing.JSeparator();
        descripcion_txt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        delete_btn = new javax.swing.JButton();
        save_btn = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        id_txt_2 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbl_usuario = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Categoría Formulario");
        setIconImage(getIconImage());
        setMaximumSize(new java.awt.Dimension(480, 459));
        setMinimumSize(new java.awt.Dimension(480, 459));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(480, 459));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(0, 114, 146));
        jPanel5.setPreferredSize(new java.awt.Dimension(857, 25));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 456, 480, 10));

        jPanel4.setBackground(new java.awt.Color(0, 114, 146));
        jPanel4.setPreferredSize(new java.awt.Dimension(857, 25));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(477, -4, 10, 470));

        jPanel6.setBackground(new java.awt.Color(0, 114, 146));
        jPanel6.setPreferredSize(new java.awt.Dimension(857, 25));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -7, 480, 10));

        jPanel3.setBackground(new java.awt.Color(0, 114, 146));
        jPanel3.setPreferredSize(new java.awt.Dimension(857, 25));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-7, -4, 10, 470));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nombre_txt.setFont(new java.awt.Font("Raleway Light", 0, 14)); // NOI18N
        nombre_txt.setForeground(new java.awt.Color(153, 153, 153));
        nombre_txt.setBorder(null);
        jPanel1.add(nombre_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 230, 30));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_Tags_32px.png"))); // NOI18N
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 30, 30));

        jSeparator2.setForeground(new java.awt.Color(204, 102, 0));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 270, -1));

        jLabel2.setFont(new java.awt.Font("Raleway Medium", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 114, 146));
        jLabel2.setText("Nombre Categoría");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_descripcion_32px.png"))); // NOI18N
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 30, 30));

        delete_separator.setForeground(new java.awt.Color(204, 102, 0));
        jPanel1.add(delete_separator, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 330, 90, 20));

        descripcion_txt.setFont(new java.awt.Font("Raleway Light", 0, 14)); // NOI18N
        descripcion_txt.setForeground(new java.awt.Color(153, 153, 153));
        descripcion_txt.setBorder(null);
        jPanel1.add(descripcion_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 390, 30));

        jLabel5.setFont(new java.awt.Font("Raleway Medium", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 114, 146));
        jLabel5.setText("Descripción Categoría");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        delete_btn.setFont(new java.awt.Font("Raleway Light", 0, 14)); // NOI18N
        delete_btn.setText("Eliminar");
        delete_btn.setBorderPainted(false);
        delete_btn.setContentAreaFilled(false);
        delete_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        delete_btn.setFocusPainted(false);
        delete_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delete_btnMouseClicked(evt);
            }
        });
        jPanel1.add(delete_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 297, 90, 40));

        save_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/Save_ON.png"))); // NOI18N
        save_btn.setBorderPainted(false);
        save_btn.setContentAreaFilled(false);
        save_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        save_btn.setFocusPainted(false);
        save_btn.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/src/Save_OFF.png"))); // NOI18N
        save_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_btnActionPerformed(evt);
            }
        });
        jPanel1.add(save_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(333, 290, 120, -1));

        jSeparator4.setForeground(new java.awt.Color(204, 102, 0));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 430, -1));

        jSeparator5.setForeground(new java.awt.Color(204, 102, 0));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, 100, -1));

        id_txt_2.setFont(new java.awt.Font("Raleway Light", 0, 14)); // NOI18N
        id_txt_2.setForeground(new java.awt.Color(153, 153, 153));
        id_txt_2.setBorder(null);
        id_txt_2.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        id_txt_2.setEnabled(false);
        id_txt_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_txt_2ActionPerformed(evt);
            }
        });
        jPanel1.add(id_txt_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, 60, 30));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_id_32px.png"))); // NOI18N
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 30, 30));

        jLabel6.setFont(new java.awt.Font("Raleway Medium", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 114, 146));
        jLabel6.setText("Identificador");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 90, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 89, 480, 370));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_Multiply_32px.png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_Expand_Arrow_32px.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, -1, -1));

        lbl_usuario.setFont(new java.awt.Font("Raleway SemiBold", 0, 12)); // NOI18N
        lbl_usuario.setForeground(new java.awt.Color(211, 124, 1));
        lbl_usuario.setText("{ NOMBRE_USUARIO }");
        jPanel2.add(lbl_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel1.setFont(new java.awt.Font("Raleway Light", 0, 18)); // NOI18N
        jLabel1.setText("Categoría : Formulario");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 100));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        setPosition(0);
        this.dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void id_txt_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_txt_2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_txt_2ActionPerformed

    int xx, xy;
    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        // TODO add your handling code here:
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        
        this.setLocation(x-xx, y-xy);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void save_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_btnActionPerformed
        // TODO add your handling code here:
        
        String JSON = "", url = "", urlParameters = "";
        JsonParser parser = new JsonParser();
        http = new DB();
        String nombre = encodeUTF8(nombre_txt.getText());  
        String desc   = encodeUTF8(descripcion_txt.getText());  
        
        if(this.categoria_id != 0){
            //Especificamos la url para request
            url = "http://api.timsa.x10.mx/updateTag.php";
            
            //Especificamos los parámetros 
            urlParameters = "id="+this.categoria_id+"&nombre="+nombre+"&descripcion="+desc+"&token="+new String(getToken());

        }else if(this.categoria_id == 0){
            url = "http://api.timsa.x10.mx/saveTag.php";
            
            //Especificamos los parámetros 
            urlParameters = "nombre="+nombre+"&descripcion="+desc+"&token="+new String(getToken());
        }
        
        out.println("Parametros: "+urlParameters+" con categoria "+this.categoria_id);
        
        try {
                //JSON = gson.toJson(http.sendPost(url, urlParameters));
                JSON = http.sendPost(url, urlParameters);
            } catch (Exception ex) {
                showMessageDialog(null,"Ha ocurrido un error al enviar información. \nRevise su conexión a internet e intente de nuevo\nError: "+ex);
                getLogger(login.class.getName()).log(SEVERE, null, ex);
            }
            
            JsonElement elementObject = parser.parse(JSON);
            int estatus    = elementObject.getAsJsonObject().get("status").getAsInt();
            String mensaje = elementObject.getAsJsonObject().get("mensaje").getAsString();
            
            if(estatus == 3){
                    JOptionPane.showMessageDialog(null, "¡Sesión expirada! Ingrese credenciales otra vez.");
                    System.exit(0);
                }
            
            if(estatus == 1){
                showMessageDialog(null, mensaje);
                this.dispose();
            }else{
                showMessageDialog(null, mensaje);
            }
    }//GEN-LAST:event_save_btnActionPerformed

    private void delete_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_btnMouseClicked
        // TODO add your handling code here:
        
        int input = JOptionPane.showConfirmDialog(null, "¿Está seguro(a) que quiere eliminar la categoría? Una vez hecho esto, no habrá vuelta atrás");
        if(input == 0){
            deleteTag(getPosition());
        }else if(input == 1){
            JOptionPane.showMessageDialog(null, "¡Mucho cuidado la próxima vez!");
        }else{
            JOptionPane.showMessageDialog(null, "¡Mucho cuidado la próxima vez!");
        }
    }//GEN-LAST:event_delete_btnMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            getLogger(tagsForm.class.getName()).log(SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>
        
        /* Create and display the form */
        invokeLater(() -> {
            new tagsForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton delete_btn;
    private javax.swing.JSeparator delete_separator;
    private javax.swing.JTextField descripcion_txt;
    public static javax.swing.JTextField id_txt_2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    public static javax.swing.JLabel lbl_contra;
    public static javax.swing.JLabel lbl_usuario;
    private javax.swing.JTextField nombre_txt;
    private javax.swing.JButton save_btn;
    // End of variables declaration//GEN-END:variables

}
