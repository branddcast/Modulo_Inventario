/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import static entities.FindUser.getPosition;
import static entities.FindUser.setPosition;
import static entities.Session.encodeUTF8;
import static entities.Session.isCaducado;
import static entities.Session.getToken;
import static entities.Session.isConectado;
import static java.awt.Font.PLAIN;
import java.awt.Image;
import java.awt.Toolkit;
import static java.lang.System.out;
import java.util.Timer;
import java.util.TimerTask;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import modulo.inventario.TextPrompt;
import modulo.inventario.config.DB;

/**
 *
 * @author BrandCast
 */
public class usersForm extends javax.swing.JFrame {

    DB http;
    Gson gson;
    private int usuario_id;
    
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
     * @param position
     */
    
    public void deleteUser(int position) {
        String JSON = "", url = "", urlParameters = "";
        JsonParser parser = new JsonParser();
        http = new DB();
        
        //Especificamos la url para request
        url = "http://api.timsa.x10.mx/deleteUser.php";
            
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
    /**
     * Creates new form usersForm
     */
    public usersForm() {
        initComponents();
        this.setLocationRelativeTo(null);
        TextPrompt usuario_ph = new TextPrompt("Usuario", usuario_txt);
        usuario_ph.changeAlpha(0.5f);
        usuario_ph.changeStyle(PLAIN);
        
        TextPrompt nombre_ph = new TextPrompt("Nombre", nombre_txt);
        nombre_ph.changeAlpha(0.5f);
        nombre_ph.changeStyle(PLAIN);
        
        TextPrompt ap_paterno_ph = new TextPrompt("Apellido Paterno", ap_paterno_txt);
        ap_paterno_ph.changeAlpha(0.5f);
        ap_paterno_ph.changeStyle(PLAIN);
        
        TextPrompt ap_materno_ph = new TextPrompt("Apellido Materno", ap_materno_txt);
        ap_materno_ph.changeAlpha(0.5f);
        ap_materno_ph.changeStyle(PLAIN);
        
        TextPrompt email_ph = new TextPrompt("Email", email_txt);
        email_ph.changeAlpha(0.5f);
        email_ph.changeStyle(PLAIN);
        
        TextPrompt telefono_ph = new TextPrompt("Teléfono", telefono_txt);
        telefono_ph.changeAlpha(0.5f);
        telefono_ph.changeStyle(PLAIN);
        
        TextPrompt direccion_ph = new TextPrompt("Dirección", direccion_txt);
        direccion_ph.changeAlpha(0.5f);
        direccion_ph.changeStyle(PLAIN);
        
        TextPrompt contra_ph = new TextPrompt("Contraseña Actual", contra_txt);
        contra_ph.changeAlpha(0.5f);
        contra_ph.changeStyle(PLAIN);
        
        TextPrompt nueva_contra_ph = new TextPrompt("Nueva Contraseña", nueva_contra_txt);
        nueva_contra_ph.changeAlpha(0.5f);
        nueva_contra_ph.changeStyle(PLAIN);
        
        TextPrompt repetir_contra_ph = new TextPrompt("Repetir Contraseña", repetir_contra_txt);
        repetir_contra_ph.changeAlpha(0.5f);
        repetir_contra_ph.changeStyle(PLAIN);
        
        Timer caducar = new Timer();
        caducar.scheduleAtFixedRate(isCaducado, 0, 1000);
        
        Timer conectar = new Timer();
        conectar.scheduleAtFixedRate(isConectado, 0, 1000);
        
        /*id_txt_2.setText(Integer.toString(this.proveedor_id));
        id_txt.setText(Integer.toString(this.proveedor_id));*/
         
        this.usuario_id = getPosition();
        
        if(this.usuario_id != 0){
            http = new DB();
            usuario_txt.setEnabled(false);
        
            //Especificamos la url para request
            String url = "http://api.timsa.x10.mx/getUserById.php";
            
            //Especificamos los parámetros 
            String urlParameters = "id="+this.usuario_id+"&token="+new String(getToken());

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
            String usuario   = elementObject.getAsJsonObject().get("usuario").getAsString();
            String nombre    = elementObject.getAsJsonObject().get("nombre").getAsString();
            String ap_paterno    = elementObject.getAsJsonObject().get("ap_paterno").getAsString();
            String ap_materno    = elementObject.getAsJsonObject().get("ap_materno").getAsString();
            String email     = elementObject.getAsJsonObject().get("email").getAsString();
            String telefono  = elementObject.getAsJsonObject().get("telefono").getAsString();
            String direccion = elementObject.getAsJsonObject().get("direccion").getAsString();
            String rol       = elementObject.getAsJsonObject().get("tipo_usuario").getAsString();
            int status       =  elementObject.getAsJsonObject().get("status").getAsInt();
            
            if(status == 3){
                    JOptionPane.showMessageDialog(null, "¡Sesión expirada! Ingrese credenciales otra vez.");
                    System.exit(0);
                }
            
            usuario_txt.setText(usuario);
            nombre_txt.setText(nombre);
            ap_paterno_txt.setText(ap_paterno);
            ap_materno_txt.setText(ap_materno);
            email_txt.setText(email);
            telefono_txt.setText(telefono);
            direccion_txt.setText(direccion);
            tipo_usuario_select.setSelectedIndex(0);
            
            
            delete_btn.setVisible(true);
            delete_separator.setVisible(true);

        }else{
            contra_txt.setVisible(false);
            contra_icon.setVisible(false);
            contra_separador.setVisible(false);
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

        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbl_usuario = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        usuario_txt = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        nombre_txt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        id_txt_2 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        ap_paterno_txt = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        ap_materno_txt = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        telefono_txt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        delete_separator = new javax.swing.JSeparator();
        delete_btn = new javax.swing.JButton();
        save_btn = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel25 = new javax.swing.JLabel();
        email_txt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel26 = new javax.swing.JLabel();
        direccion_txt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tipo_usuario_select = new javax.swing.JComboBox<>();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel27 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel28 = new javax.swing.JLabel();
        contra_separador = new javax.swing.JSeparator();
        contra_icon = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        repetir_contra_txt = new javax.swing.JPasswordField();
        contra_txt = new javax.swing.JPasswordField();
        nueva_contra_txt = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setMaximumSize(new java.awt.Dimension(700, 589));
        setMinimumSize(new java.awt.Dimension(700, 589));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(700, 589));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(0, 114, 146));
        jPanel5.setPreferredSize(new java.awt.Dimension(857, 25));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 586, 700, 20));

        jPanel3.setBackground(new java.awt.Color(0, 114, 146));
        jPanel3.setPreferredSize(new java.awt.Dimension(857, 25));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-7, -4, 10, 590));

        jPanel6.setBackground(new java.awt.Color(0, 114, 146));
        jPanel6.setPreferredSize(new java.awt.Dimension(857, 25));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -7, 700, 10));

        jPanel4.setBackground(new java.awt.Color(0, 114, 146));
        jPanel4.setPreferredSize(new java.awt.Dimension(857, 25));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(697, -4, 10, 590));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_Multiply_32px.png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_Expand_Arrow_32px.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 0, -1, -1));

        lbl_usuario.setFont(new java.awt.Font("Raleway SemiBold", 0, 12)); // NOI18N
        lbl_usuario.setForeground(new java.awt.Color(211, 124, 1));
        lbl_usuario.setText("{ NOMBRE_USUARIO }");
        jPanel2.add(lbl_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel1.setFont(new java.awt.Font("Raleway Light", 0, 18)); // NOI18N
        jLabel1.setText("Usuario : Formulario");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 100));

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

        usuario_txt.setFont(new java.awt.Font("Raleway Light", 0, 14)); // NOI18N
        usuario_txt.setForeground(new java.awt.Color(153, 153, 153));
        usuario_txt.setBorder(null);
        jPanel1.add(usuario_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 110, 30));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_Users_32px.png"))); // NOI18N
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 30, 30));

        jSeparator2.setForeground(new java.awt.Color(204, 102, 0));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 150, -1));

        jLabel2.setFont(new java.awt.Font("Raleway Medium", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 114, 146));
        jLabel2.setText("Usuario");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, -1, -1));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_descripcion_32px.png"))); // NOI18N
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 30, 30));

        nombre_txt.setFont(new java.awt.Font("Raleway Light", 0, 14)); // NOI18N
        nombre_txt.setForeground(new java.awt.Color(153, 153, 153));
        nombre_txt.setBorder(null);
        jPanel1.add(nombre_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 150, 30));

        jLabel5.setFont(new java.awt.Font("Raleway Medium", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 114, 146));
        jLabel5.setText("Datos Personales");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        jSeparator4.setForeground(new java.awt.Color(204, 102, 0));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 190, -1));

        jSeparator5.setForeground(new java.awt.Color(204, 102, 0));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 130, 100, -1));

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
        jPanel1.add(id_txt_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, 60, 30));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_id_32px.png"))); // NOI18N
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, 30, 30));

        jLabel6.setFont(new java.awt.Font("Raleway Medium", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 114, 146));
        jLabel6.setText("Teléfono");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 70, 90, -1));

        jSeparator6.setForeground(new java.awt.Color(204, 102, 0));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 220, 190, -1));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_descripcion_32px.png"))); // NOI18N
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, 30, 30));

        ap_paterno_txt.setFont(new java.awt.Font("Raleway Light", 0, 14)); // NOI18N
        ap_paterno_txt.setForeground(new java.awt.Color(153, 153, 153));
        ap_paterno_txt.setBorder(null);
        jPanel1.add(ap_paterno_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 150, 30));

        jSeparator7.setForeground(new java.awt.Color(204, 102, 0));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 220, 190, -1));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_descripcion_32px.png"))); // NOI18N
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 190, 30, 30));

        ap_materno_txt.setFont(new java.awt.Font("Raleway Light", 0, 14)); // NOI18N
        ap_materno_txt.setForeground(new java.awt.Color(153, 153, 153));
        ap_materno_txt.setBorder(null);
        jPanel1.add(ap_materno_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 190, 150, 30));

        jSeparator8.setForeground(new java.awt.Color(204, 102, 0));
        jPanel1.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 130, 190, -1));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_Tel_32px.png"))); // NOI18N
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, 30, 30));

        telefono_txt.setFont(new java.awt.Font("Raleway Light", 0, 14)); // NOI18N
        telefono_txt.setForeground(new java.awt.Color(153, 153, 153));
        telefono_txt.setBorder(null);
        jPanel1.add(telefono_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 100, 150, 30));

        jLabel7.setFont(new java.awt.Font("Raleway Medium", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 114, 146));
        jLabel7.setText("Identificador");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 90, -1));

        delete_separator.setForeground(new java.awt.Color(204, 102, 0));
        jPanel1.add(delete_separator, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 480, 90, 20));

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
        delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btnActionPerformed(evt);
            }
        });
        jPanel1.add(delete_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 440, 90, 50));

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
        jPanel1.add(save_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 440, 120, -1));

        jSeparator9.setForeground(new java.awt.Color(204, 102, 0));
        jPanel1.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 270, -1));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_Email_32px.png"))); // NOI18N
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 30, 30));

        email_txt.setFont(new java.awt.Font("Raleway Light", 0, 14)); // NOI18N
        email_txt.setForeground(new java.awt.Color(153, 153, 153));
        email_txt.setBorder(null);
        jPanel1.add(email_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 230, 30));

        jLabel8.setFont(new java.awt.Font("Raleway Medium", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 114, 146));
        jLabel8.setText("Email");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        jSeparator10.setForeground(new java.awt.Color(204, 102, 0));
        jPanel1.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 310, 330, -1));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_Direccion_32px.png"))); // NOI18N
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 280, -1, 30));

        direccion_txt.setFont(new java.awt.Font("Raleway Light", 0, 14)); // NOI18N
        direccion_txt.setForeground(new java.awt.Color(153, 153, 153));
        direccion_txt.setBorder(null);
        jPanel1.add(direccion_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 280, 290, 30));

        jLabel9.setFont(new java.awt.Font("Raleway Medium", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 114, 146));
        jLabel9.setText("Dirección");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 250, 160, -1));

        tipo_usuario_select.setFont(new java.awt.Font("Raleway", 0, 12)); // NOI18N
        tipo_usuario_select.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador" }));
        tipo_usuario_select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipo_usuario_selectActionPerformed(evt);
            }
        });
        jPanel1.add(tipo_usuario_select, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 190, 30));

        jSeparator11.setForeground(new java.awt.Color(204, 102, 0));
        jPanel1.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 400, 190, -1));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_id_32px.png"))); // NOI18N
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 370, 30, 30));

        jSeparator12.setForeground(new java.awt.Color(204, 102, 0));
        jPanel1.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 400, 190, -1));

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_id_32px.png"))); // NOI18N
        jPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 370, 30, 30));

        contra_separador.setForeground(new java.awt.Color(204, 102, 0));
        jPanel1.add(contra_separador, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 190, -1));

        contra_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_id_32px.png"))); // NOI18N
        jPanel1.add(contra_icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 30, 30));

        jLabel12.setFont(new java.awt.Font("Raleway Medium", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 114, 146));
        jLabel12.setText("Contraseña");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, -1));

        repetir_contra_txt.setFont(new java.awt.Font("Raleway", 0, 14)); // NOI18N
        repetir_contra_txt.setForeground(new java.awt.Color(153, 153, 153));
        repetir_contra_txt.setBorder(null);
        jPanel1.add(repetir_contra_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 370, 150, 30));

        contra_txt.setFont(new java.awt.Font("Raleway", 0, 14)); // NOI18N
        contra_txt.setForeground(new java.awt.Color(153, 153, 153));
        contra_txt.setBorder(null);
        jPanel1.add(contra_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, 150, 30));

        nueva_contra_txt.setFont(new java.awt.Font("Raleway", 0, 14)); // NOI18N
        nueva_contra_txt.setForeground(new java.awt.Color(153, 153, 153));
        nueva_contra_txt.setBorder(null);
        jPanel1.add(nueva_contra_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 370, 150, 30));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/user.png"))); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 100, 100));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 89, 700, 500));

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

    private void delete_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_btnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_delete_btnMouseClicked

    private void delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btnActionPerformed
        // TODO add your handling code here:

        int input = JOptionPane.showConfirmDialog(null, "¿Está seguro(a) que quiere eliminar el usuario? Una vez hecho esto, no habrá vuelta atrás");
        if(input == 0){
            deleteUser(getPosition());
        }else if(input == 1){
            JOptionPane.showMessageDialog(null, "¡Mucho cuidado la próxima vez!");
        }else{
            JOptionPane.showMessageDialog(null, "¡Mucho cuidado la próxima vez!");
        }
    }//GEN-LAST:event_delete_btnActionPerformed

    private void save_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_btnActionPerformed
        // TODO add your handling code here:
        String JSON = "", url = "", urlParameters = "";
        JsonParser parser = new JsonParser();
        http = new DB();

        String usuario  = encodeUTF8(usuario_txt.getText());
        String nombre   = encodeUTF8(nombre_txt.getText());
        String ap_paterno   = encodeUTF8(ap_paterno_txt.getText());
        String ap_materno   = encodeUTF8(ap_materno_txt.getText());
        String email   = encodeUTF8(email_txt.getText());
        String telefono = encodeUTF8(telefono_txt.getText());
        String direccion   = encodeUTF8(direccion_txt.getText());
        int rol = tipo_usuario_select.getSelectedIndex();

        if(this.usuario_id != 0){
            if(!contra_txt.getText().equals("")){
                if(!nueva_contra_txt.getText().equals("") && !repetir_contra_txt.getText().equals("")){
                    if(nueva_contra_txt.getText().equals(repetir_contra_txt.getText())){
                        //Especificamos la url para request
                        url = "http://api.timsa.x10.mx/updateUser.php";

                        //Especificamos los parámetros
                        urlParameters = "id="+this.usuario_id+"&usuario="+usuario+"&password="+contra_txt.getText()+"&nombre="+nombre+"&ap_paterno="+ap_paterno+"&ap_materno="+ap_materno+"&email="+email+"&telefono="+telefono+"&direccion="+direccion+"&nueva_password="+nueva_contra_txt.getText()+"&repetir_password="+repetir_contra_txt.getText()+"&token="+new String(getToken());

                        try {
                            //JSON = gson.toJson(http.sendPost(url, urlParameters));
                            JSON = http.sendPost(url, urlParameters);
                        } catch (Exception ex) {
                            showMessageDialog(null,"Ha ocurrido un error al enviar información. \nRevise su conexión a internet e intente de nuevo\nError: "+ex);
                            getLogger(login.class.getName()).log(SEVERE, null, ex);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                    }
                }else if(nueva_contra_txt.getText().equals("") || repetir_contra_txt.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Si cambiará la contraseña, debe llenar: \n'Nueva Contraseña' y 'Repetir Contraseña'");
                }else{
                    //Especificamos la url para request
                    url = "http://api.timsa.x10.mx/updateUser.php";

                    //Especificamos los parámetros
                    urlParameters = "id="+this.usuario_id+"&usuario="+usuario+"&password="+contra_txt.getText()+"&nombre="+nombre+"&ap_paterno="+ap_paterno+"&ap_materno="+ap_materno+"&email="+email+"&telefono="+telefono+"&direccion="+direccion+"&token="+new String(getToken());
                    
                    try {
                        //JSON = gson.toJson(http.sendPost(url, urlParameters));
                        JSON = http.sendPost(url, urlParameters);
                    } catch (Exception ex) {
                        showMessageDialog(null,"Ha ocurrido un error al enviar información. \nRevise su conexión a internet e intente de nuevo\nError: "+ex);
                        getLogger(login.class.getName()).log(SEVERE, null, ex);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "Escriba la contraseña para confirmar \nel proceso de actualización");
            }
        }else if(this.usuario_id == 0){
            
            if(!nueva_contra_txt.getText().equals("")){
                if(!repetir_contra_txt.getText().equals("")){
                    if(nueva_contra_txt.getText().equals(repetir_contra_txt.getText())){
                        url = "http://api.timsa.x10.mx/saveUser.php";

                        //Especificamos los parámetros
                        urlParameters = "usuario="+usuario+"&nombre="+nombre+"&ap_paterno="+ap_paterno+"&ap_materno="+ap_materno+"&email="+email+"&telefono="+telefono+"&direccion="+direccion+"&nueva_password="+nueva_contra_txt.getText()+"&repetir_password="+repetir_contra_txt.getText()+"&token="+new String(getToken());

                        try {
                            //JSON = gson.toJson(http.sendPost(url, urlParameters));
                            JSON = http.sendPost(url, urlParameters);
                        } catch (Exception ex) {
                            showMessageDialog(null,"Ha ocurrido un error al enviar información. \nRevise su conexión a internet e intente de nuevo\nError: "+ex);
                            getLogger(login.class.getName()).log(SEVERE, null, ex);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "¡Debe repetir la contraseña!");
                }
            }else{
                JOptionPane.showMessageDialog(null, "¡Campo 'Contraseña' no debe estar vacío!");
            }
        }

        out.println("Parametros: "+urlParameters+" con categoria "+this.usuario_id);

        

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
    int xx, xy;
    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x-xx, y-xy);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        // TODO add your handling code here:
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_jPanel1MousePressed

    private void tipo_usuario_selectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipo_usuario_selectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipo_usuario_selectActionPerformed

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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(usersForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(usersForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(usersForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(usersForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new usersForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ap_materno_txt;
    private javax.swing.JTextField ap_paterno_txt;
    private javax.swing.JLabel contra_icon;
    private javax.swing.JSeparator contra_separador;
    private javax.swing.JPasswordField contra_txt;
    private javax.swing.JButton delete_btn;
    private javax.swing.JSeparator delete_separator;
    private javax.swing.JTextField direccion_txt;
    private javax.swing.JTextField email_txt;
    public static javax.swing.JTextField id_txt_2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    public static javax.swing.JLabel lbl_usuario;
    private javax.swing.JTextField nombre_txt;
    private javax.swing.JPasswordField nueva_contra_txt;
    private javax.swing.JPasswordField repetir_contra_txt;
    private javax.swing.JButton save_btn;
    private javax.swing.JTextField telefono_txt;
    private javax.swing.JComboBox<String> tipo_usuario_select;
    private javax.swing.JTextField usuario_txt;
    // End of variables declaration//GEN-END:variables
}
