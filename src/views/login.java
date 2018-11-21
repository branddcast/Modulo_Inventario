/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.util.logging.Level;
import java.util.logging.Logger;
import modulo.inventario.config.DB;
import static entities.Session.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import entities.Session;
import static java.awt.EventQueue.invokeLater;
import java.awt.Font;
import static java.awt.Font.PLAIN;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.lang.System.exit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;
import modulo.inventario.TextPrompt;
import static views.home.lbl_contra;
import static views.home.lbl_date;
import static views.home.lbl_usuario;

/**
 *
 * @author BrandCast
 */
public final class login extends javax.swing.JFrame {
    DB http;
    Gson gson;
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("src/logo.png"));


        return retValue;
    }
    
    public void getAccess(){
        log_login.setText("Obteniendo respuesta... ¡Por favor, espere!");
        //Especificamos la url para request
        String url = "http://api.timsa.x10.mx/login.php";
        
        
        if(!user_txt.getText().equals("") && !password_txt.getText().equals("")){
            int status = 0;
            String name = "", pass = "";
            
            while(status == 0){
                //Especificamos los parámetros 
                String urlParameters = "user="+user_txt.getText()+"&password="+password_txt.getText();

                String JSON = "";
                JsonParser parser = new JsonParser();

                try {
                    //JSON = gson.toJson(http.sendPost(url, urlParameters));
                    JSON = http.sendPost(url, urlParameters);
                } catch (Exception ex) {
                    log_login.setText("Error en la conexión");
                    showMessageDialog(null, "Ha ocurrido un error. \nRevise su conexión a internet e intente de nuevo\nError: "+ex);
                    getLogger(login.class.getName()).log(SEVERE, null, ex);
                }


                JsonElement elementObject = parser.parse(JSON);
                name = elementObject.getAsJsonObject().get("user").getAsString();
                pass = elementObject.getAsJsonObject().get("password").getAsString();
                status =  elementObject.getAsJsonObject().get("status").getAsInt();
                
                if(status != 0){
                    break;
                }
            }

            /*
             * Status values
             * 1 = Acceso Aceptado
             * 2 = Acceso Denegado
             */
            if(status == 2){
                showMessageDialog(null,"¡Acceso Denegado! Ingrese credenciales correctamente");
            }else{
                log_login.setText("Acceso permitido.");
                Session.setUsuario(user_txt.getText());
                Session.setContrasenia(password_txt.getText());
                //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                Session.setFecha(date);
                Session.setSesion(true);
                System.out.println(new String(getToken()));
                home home = new home();
                home.setVisible(true);
                lbl_usuario.setText(name);
                lbl_contra.setText(pass);
                this.hide();
            }
        }else{
            showMessageDialog(null,"¡Oops! Parece que se te olvidó llenar un campo");
        }
    }
    
    TimerTask timerTask = new TimerTask()
     {
         /**
          * Método al que Timer llamará cada segundo. Se encarga de avisar
          * a los observadores de este modelo.
          */
         public void run() 
         {
            Date hoy = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy  hh:mm a");
            lbl_date.setText(sdf.format(hoy));
         }
     };

    /**
     * Creates new form login
     */
    public login() {
        initComponents();
        this.setLocationRelativeTo(null);
        TextPrompt user = new TextPrompt("Nombre de Usuario", user_txt);
        user.changeAlpha(0.5f);
        user.changeStyle(PLAIN);
        TextPrompt password = new TextPrompt("Contraseña", password_txt);
        password.changeAlpha(0.5f);
        password.changeStyle(PLAIN);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        
        http = new DB();  
        
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpIngreso = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        user_txt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        separador_user = new javax.swing.JSeparator();
        separador_pass = new javax.swing.JSeparator();
        password_txt = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        log_login = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Módulo Inventario");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(getIconImage());
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpIngreso.setBackground(new java.awt.Color(255, 255, 255));
        jpIngreso.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jpIngresoMouseDragged(evt);
            }
        });
        jpIngreso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jpIngresoMousePressed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_Multiply_32px.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Raleway Light", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 104, 146));
        jLabel2.setText("Contraseña");

        jLabel3.setFont(new java.awt.Font("Raleway Light", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 104, 146));
        jLabel3.setText("Usuario");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_User_96px_2.png"))); // NOI18N

        user_txt.setFont(new java.awt.Font("Raleway Light", 0, 14)); // NOI18N
        user_txt.setForeground(new java.awt.Color(153, 153, 153));
        user_txt.setBorder(null);
        user_txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                user_txtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                user_txtFocusLost(evt);
            }
        });
        user_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                user_txtKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Raleway Medium", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 104, 146));
        jLabel5.setText("Inicio de Sesión");

        separador_user.setForeground(new java.awt.Color(0, 51, 255));

        separador_pass.setForeground(new java.awt.Color(0, 51, 255));

        password_txt.setFont(new java.awt.Font("Raleway Light", 0, 14)); // NOI18N
        password_txt.setForeground(new java.awt.Color(153, 153, 153));
        password_txt.setBorder(null);
        password_txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                password_txtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                password_txtFocusLost(evt);
            }
        });
        password_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                password_txtKeyPressed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_customer_32px_1.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_Key_32px.png"))); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/Enter_OFF.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/src/Enter_ON.png"))); // NOI18N
        jButton1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/src/Enter_ON.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        log_login.setFont(new java.awt.Font("Raleway Light", 0, 11)); // NOI18N

        javax.swing.GroupLayout jpIngresoLayout = new javax.swing.GroupLayout(jpIngreso);
        jpIngreso.setLayout(jpIngresoLayout);
        jpIngresoLayout.setHorizontalGroup(
            jpIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpIngresoLayout.createSequentialGroup()
                .addGap(278, 278, 278)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpIngresoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpIngresoLayout.createSequentialGroup()
                        .addGroup(jpIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addGroup(jpIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jpIngresoLayout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(password_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(separador_pass, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jpIngresoLayout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(user_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(separador_user, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(54, 54, 54))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpIngresoLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(82, 82, 82))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpIngresoLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(85, 85, 85))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpIngresoLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(104, 104, 104))))
            .addGroup(jpIngresoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(log_login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpIngresoLayout.setVerticalGroup(
            jpIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpIngresoLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(2, 2, 2)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(37, 37, 37)
                .addComponent(jLabel3)
                .addGap(8, 8, 8)
                .addGroup(jpIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(user_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(separador_user, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addGap(8, 8, 8)
                .addGroup(jpIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(password_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(separador_pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(log_login, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jpIngreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 310, 480));

        lbl_date.setFont(new java.awt.Font("Raleway Light", 0, 12)); // NOI18N
        lbl_date.setForeground(new java.awt.Color(255, 255, 255));
        lbl_date.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 450, 160, 20));

        jLabel9.setFont(new java.awt.Font("Segoe UI Light", 1, 48)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Inventario");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 290, 40));

        jLabel10.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Módulo");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 140, 40));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/fondo_login.jpg"))); // NOI18N
        jLabel8.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel8MouseDragged(evt);
            }
        });
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel8MousePressed(evt);
            }
        });
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 480));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    int xx, xy;
    private void jLabel8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MousePressed
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_jLabel8MousePressed

    private void jLabel8MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        
        this.setLocation(x-xx, y-xy);
    }//GEN-LAST:event_jLabel8MouseDragged

    private void jpIngresoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpIngresoMousePressed

    }//GEN-LAST:event_jpIngresoMousePressed

    private void jpIngresoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpIngresoMouseDragged

    }//GEN-LAST:event_jpIngresoMouseDragged

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        exit(0);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        getAccess();
        //System.out.println(name+" y "+pass);
        //JOptionPane.showMessageDialog(null,"Usuario: "+name+", Contraseña: "+pass);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void password_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_password_txtKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==VK_ENTER){
            log_login.setText("Obteniendo respuesta... ¡Por favor, espere!");
            getAccess();
        }
    }//GEN-LAST:event_password_txtKeyPressed

    private void user_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_user_txtKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==VK_ENTER){
            log_login.setText("Obteniendo respuesta... ¡Por favor, espere!");
            getAccess();
        }
    }//GEN-LAST:event_user_txtKeyPressed

    private void user_txtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_user_txtFocusGained
        // TODO add your handling code here:
        separador_user.setForeground(new java.awt.Color(211, 124, 1));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_customer_32px_1_ON.png")));
    }//GEN-LAST:event_user_txtFocusGained

    private void user_txtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_user_txtFocusLost
        // TODO add your handling code here:
        separador_user.setForeground(new java.awt.Color(0,51,255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_customer_32px_1.png")));
    }//GEN-LAST:event_user_txtFocusLost

    private void password_txtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_password_txtFocusGained
        // TODO add your handling code here:
        separador_pass.setForeground(new java.awt.Color(211, 124, 1));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_Key_32px_ON.png")));
    }//GEN-LAST:event_password_txtFocusGained

    private void password_txtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_password_txtFocusLost
        // TODO add your handling code here:
        separador_pass.setForeground(new java.awt.Color(0,51,255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_Key_32px.png")));
    }//GEN-LAST:event_password_txtFocusLost

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
                if ("Windows".equals(info.getName())) {
                    setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            getLogger(login.class.getName()).log(SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        invokeLater(() -> {
            new login().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jpIngreso;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel log_login;
    private javax.swing.JPasswordField password_txt;
    private javax.swing.JSeparator separador_pass;
    private javax.swing.JSeparator separador_user;
    private javax.swing.JTextField user_txt;
    // End of variables declaration//GEN-END:variables
}
