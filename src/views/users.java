/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import static entities.FindUser.setPosition;
import static entities.Session.encodeUTF8;
import static entities.Session.isCaducado;
import static entities.Session.isConectado;
import static entities.Session.getToken;
import java.awt.Color;
import static java.awt.EventQueue.invokeLater;
import java.awt.Font;
import static java.awt.Font.PLAIN;
import java.awt.Image;
import java.awt.Toolkit;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.lang.Integer.parseInt;
import java.util.Timer;
import java.util.TimerTask;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTable;
import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import modulo.inventario.TextPrompt;
import modulo.inventario.config.DB;
import views.usersForm;
import static views.usersForm.id_txt_2;

/**
 *
 * @author BrandCast
 */
public final class users extends javax.swing.JFrame {
    
    DB http;
    Gson gson;
    private String usuario_id;
    
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
    
    /*TimerTask timerTask = new TimerTask()
     {
         /**
          * Método al que Timer llamará cada segundo. Se encarga de avisar
          * a los observadores de este modelo.
          */
         /*public void run() 
         {
            SwingUtilities.updateComponentTreeUI(jpanel_tagsList);
            jpanel_tagsList.validate();
         }
     };*/
    
    public void limpiarTabla(DefaultTableModel modelo){
        int filas=modelo.getRowCount();
            for (int i = 0;filas>i; i++) {
                modelo.removeRow(0);
            }
    }
    
    public void allUsers(){
        //Especificamos la url para request
            String url = "http://api.timsa.x10.mx/getUsers.php";


            //Especificamos los parámetros 
            String urlParameters = "token="+new String(getToken());

            String JSON_ = "";
            JsonParser parser = new JsonParser();
            

            try {
                //JSON = gson.toJson(http.sendPost(url, urlParameters));
                JSON_ = http.sendPost(url,urlParameters);
            } catch (Exception ex) {
                showMessageDialog(null,"Ha ocurrido un error al cargar información. \nRevise su conexión a internet e intente de nuevo\nError: "+ex);
                getLogger(login.class.getName()).log(SEVERE, null, ex);
            }
            

            JsonElement arrayElement = parser.parse(JSON_);
            /*java.lang.reflect.Type type = new TypeToken<List<Tags[]>>() {}.getType();
            List<Tags[]> categorias = gson.fromJson(JSON, type);*/


            DefaultTableModel modelo=(DefaultTableModel) tags_table.getModel();
            tags_table.getTableHeader().setFont(new Font("Raleway", 0, 14)); 
            tags_table.getTableHeader().setForeground(new Color(255,255,255));
            tags_table.getTableHeader().setBackground(new Color(0, 114, 146));
            
            modelo.setColumnIdentifiers(new String [] {"#", "Id", "Usuario", "Nombre", "Email", "Teléfono", "Dirección", "Rol"});
            
            TableColumnModel columnModel = tags_table.getColumnModel();
            
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(1).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(200);
            columnModel.getColumn(4).setPreferredWidth(200);
            columnModel.getColumn(5).setPreferredWidth(100);
            columnModel.getColumn(6).setPreferredWidth(250);
            columnModel.getColumn(7).setPreferredWidth(100);
            
            limpiarTabla(modelo);

            //Tags categoria[] = new Tags[arrayElement.getAsJsonArray().size()];

            for(int i = 0; i<arrayElement.getAsJsonArray().size(); i++) {
                //categoria[i] = null; 
                int id          = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("id").getAsInt();
                String usuario   = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("usuario").getAsString();
                String nombre   = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("nombre").getAsString();
                String email   = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("email").getAsString();
                String telefono     = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("telefono").getAsString();
                String direccion   = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("direccion").getAsString();
                String rol   = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("tipo_usuario").getAsString();
                int existencia  = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("existencia").getAsInt();
                int status      = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("status").getAsInt();
                
                if(status == 3){
                    JOptionPane.showMessageDialog(null, "¡Sesión expirada! Ingrese credenciales otra vez.");
                    System.exit(0);
                }
                
                if(status == 2){
                    JOptionPane.showMessageDialog(null, "No hay registros de Categorías.");
                }

                //categoria[i] = new Tags(id, nombre, desc, existencia);

                //categoria[i].setId(id);                 //System.out.println(tag.getId());
                //categoria[i].setCategoria(nombre);      //System.out.println(tag.getCategoria());
                //categoria[i].setDescripcion(desc);      //System.out.println(tag.getDescripcion());
                //categoria[i].setExistencia(existencia); //System.out.println(tag.getExistencia());

                modelo.addRow(new Object[]{" "+(i+1), " "+id, " "+usuario, " "+nombre, " "+email, " "+telefono, " "+direccion, " "+rol});
                tags_table.setRowHeight(i,30);
            }
    }
    
    public void actualizarTabla(int var){
        
        if(var == 1){
            allUsers();
        }else{
            buscarUser();
        }
         
     }
    
    
    public void buscarUser(){
        
        //Especificamos la url para request
            String url = "http://api.timsa.x10.mx/searchUsers.php";

            String value = encodeUTF8(buscar_txt.getText());  
            
            //Especificamos los parámetros 
            String urlParameters = "q="+value+"&token="+new String(getToken());

            String JSON_ = "";
            JsonParser parser = new JsonParser();
            

            try {
                //JSON = gson.toJson(http.sendPost(url, urlParameters));
                JSON_ = http.sendPost(url, urlParameters);
            } catch (Exception ex) {
                showMessageDialog(null,"Ha ocurrido un error al cargar información. \nRevise su conexión a internet e intente de nuevo\nError: "+ex);
                getLogger(login.class.getName()).log(SEVERE, null, ex);
            }
            

            JsonElement arrayElement = parser.parse(JSON_);
            /*java.lang.reflect.Type type = new TypeToken<List<Tags[]>>() {}.getType();
            List<Tags[]> categorias = gson.fromJson(JSON, type);*/


            DefaultTableModel modelo=(DefaultTableModel) tags_table.getModel();
            tags_table.getTableHeader().setFont(new Font("Raleway", 0, 14)); 
            tags_table.getTableHeader().setForeground(new Color(255,255,255));
            tags_table.getTableHeader().setBackground(new Color(0, 114, 146));
            
            modelo.setColumnIdentifiers(new String [] {"#", "Id", "Usuario", "Nombre", "Email", "Teléfono", "Dirección", "Rol"});
            
            TableColumnModel columnModel = tags_table.getColumnModel();
            
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(1).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(200);
            columnModel.getColumn(4).setPreferredWidth(200);
            columnModel.getColumn(5).setPreferredWidth(100);
            columnModel.getColumn(6).setPreferredWidth(250);
            columnModel.getColumn(7).setPreferredWidth(100);
            
            
            limpiarTabla(modelo);

            //Tags categoria[] = new Tags[arrayElement.getAsJsonArray().size()];

            for(int i = 0; i<arrayElement.getAsJsonArray().size(); i++) {
                //categoria[i] = null; 
                int id          = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("id").getAsInt();
                String usuario   = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("usuario").getAsString();
                String nombre   = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("nombre").getAsString();
                String email   = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("email").getAsString();
                String telefono     = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("telefono").getAsString();
                String direccion   = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("direccion").getAsString();
                String rol   = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("tipo_usuario").getAsString();
                int existencia  = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("existencia").getAsInt();
                int status      = arrayElement.getAsJsonArray().get(i).getAsJsonObject().get("status").getAsInt();
                
                if(status == 3){
                    JOptionPane.showMessageDialog(null, "¡Sesión expirada! Ingrese credenciales otra vez.");
                    System.exit(0);
                }
                
                if(status == 2){
                    JOptionPane.showMessageDialog(null, "No existe esa categoría");
                    break;
                }

                //categoria[i] = new Tags(id, nombre, desc, existencia);

                //categoria[i].setId(id);                 //System.out.println(tag.getId());
                //categoria[i].setCategoria(nombre);      //System.out.println(tag.getCategoria());
                //categoria[i].setDescripcion(desc);      //System.out.println(tag.getDescripcion());
                //categoria[i].setExistencia(existencia); //System.out.println(tag.getExistencia());

                modelo.addRow(new Object[]{" "+(i+1), " "+id, " "+usuario, " "+nombre, " "+email, " "+telefono, " "+direccion, " "+rol});
                tags_table.setRowHeight(i,30);
            }
    }

    /**
     * Creates new form users
     */
    public users() {
        initComponents();
        this.setLocationRelativeTo(null);
        TextPrompt user = new TextPrompt("Buscar Usuario", buscar_txt);
        user.changeAlpha(0.5f);
        user.changeStyle(PLAIN);
        http = new DB();
        actualizarTabla(1);
        
        Timer caducar = new Timer();
        caducar.scheduleAtFixedRate(isCaducado, 0, 1000);
        
        Timer conectar = new Timer();
        conectar.scheduleAtFixedRate(isConectado, 0, 1000);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        rSPanelsSlider1 = new rojerusan.RSPanelsSlider();
        jpanel_tagsList = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        buscar_txt = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tags_table = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbl_usuario = new javax.swing.JLabel();
        add_btn = new javax.swing.JButton();
        delete_separator = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setMaximumSize(new java.awt.Dimension(932, 513));
        setMinimumSize(new java.awt.Dimension(932, 513));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(932, 513));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(0, 114, 146));
        jPanel4.setPreferredSize(new java.awt.Dimension(857, 25));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(929, -4, 20, 520));

        jPanel3.setBackground(new java.awt.Color(0, 114, 146));
        jPanel3.setPreferredSize(new java.awt.Dimension(857, 25));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-7, -4, 10, 520));

        jPanel5.setBackground(new java.awt.Color(0, 114, 146));
        jPanel5.setPreferredSize(new java.awt.Dimension(857, 25));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 940, 10));

        jPanel1.setBackground(new java.awt.Color(0, 114, 146));
        jPanel1.setPreferredSize(new java.awt.Dimension(857, 25));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -7, 940, 10));

        rSPanelsSlider1.setBackground(new java.awt.Color(255, 255, 255));

        jpanel_tagsList.setBackground(new java.awt.Color(255, 255, 255));
        jpanel_tagsList.setName("jpanel_tagsList"); // NOI18N

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_Search_32px.png"))); // NOI18N

        buscar_txt.setFont(new java.awt.Font("Raleway Light", 0, 14)); // NOI18N
        buscar_txt.setForeground(new java.awt.Color(153, 153, 153));
        buscar_txt.setBorder(null);
        buscar_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buscar_txtKeyPressed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(204, 102, 0));

        tags_table.setFont(new java.awt.Font("Raleway", 0, 12)); // NOI18N
        tags_table.setModel(new javax.swing.table.DefaultTableModel(

        ));
        tags_table.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tags_table.setGridColor(new java.awt.Color(204, 204, 204));
        tags_table.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tags_table.setSelectionForeground(new java.awt.Color(51, 51, 51));
        tags_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tags_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tags_table);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_refresh_32px.png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpanel_tagsListLayout = new javax.swing.GroupLayout(jpanel_tagsList);
        jpanel_tagsList.setLayout(jpanel_tagsListLayout);
        jpanel_tagsListLayout.setHorizontalGroup(
            jpanel_tagsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanel_tagsListLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jpanel_tagsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 890, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpanel_tagsListLayout.createSequentialGroup()
                        .addGroup(jpanel_tagsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpanel_tagsListLayout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(buscar_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(198, 198, 198)
                        .addComponent(jLabel2)))
                .addGap(20, 20, 20))
        );
        jpanel_tagsListLayout.setVerticalGroup(
            jpanel_tagsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel_tagsListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanel_tagsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscar_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(247, Short.MAX_VALUE))
        );

        rSPanelsSlider1.add(jpanel_tagsList, "card2");

        getContentPane().add(rSPanelsSlider1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 97, 930, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_Multiply_32px.png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 0, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_Expand_Arrow_32px.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 0, -1, -1));

        lbl_usuario.setFont(new java.awt.Font("Raleway SemiBold", 0, 12)); // NOI18N
        lbl_usuario.setForeground(new java.awt.Color(211, 124, 1));
        lbl_usuario.setText("{ NOMBRE_USUARIO }");
        jPanel2.add(lbl_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        add_btn.setFont(new java.awt.Font("Raleway Light", 0, 14)); // NOI18N
        add_btn.setText("Agregar");
        add_btn.setBorderPainted(false);
        add_btn.setContentAreaFilled(false);
        add_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add_btn.setFocusPainted(false);
        add_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_btnActionPerformed(evt);
            }
        });
        jPanel2.add(add_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 30, 120, 50));

        delete_separator.setForeground(new java.awt.Color(204, 102, 0));
        jPanel2.add(delete_separator, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 70, 100, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8_add_32px.png"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 40, 40, 30));

        jLabel5.setFont(new java.awt.Font("Raleway Light", 0, 18)); // NOI18N
        jLabel5.setText("Usuarios : Listado");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 100));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buscar_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscar_txtKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==VK_ENTER){
            buscarUser();
        }
    }//GEN-LAST:event_buscar_txtKeyPressed

    private void tags_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tags_tableMouseClicked
        // TODO add your handling code here:
        JTable source = (JTable)evt.getSource();
        int row = source.rowAtPoint( evt.getPoint() );
        int column = source.columnAtPoint( evt.getPoint() );
        String s=source.getModel().getValueAt(row, 1)+"";
        setPosition(parseInt(s.trim()));

        usersForm userForm = new usersForm();
        id_txt_2.setText(s);
        usersForm.lbl_usuario.setText(lbl_usuario.getText());
        userForm.setVisible(true);
    }//GEN-LAST:event_tags_tableMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        actualizarTabla(1);
        actualizarTabla(1);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        this.dispose();
        home home = new home();
        home.lbl_usuario.setText(lbl_usuario.getText());
        home.show();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void add_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_btnActionPerformed
        // TODO add your handling code here:

        usersForm form = new usersForm();
        usersForm.lbl_usuario.setText(lbl_usuario.getText());
        form.setVisible(true);
    }//GEN-LAST:event_add_btnActionPerformed
    int xx, xy;
    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x-xx, y-xy);
    }//GEN-LAST:event_jPanel2MouseDragged

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
        // TODO add your handling code here:
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_jPanel2MousePressed

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
            java.util.logging.Logger.getLogger(users.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(users.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(users.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(users.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new users().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_btn;
    private javax.swing.JTextField buscar_txt;
    private javax.swing.JSeparator delete_separator;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel jpanel_tagsList;
    public static javax.swing.JLabel lbl_usuario;
    private rojerusan.RSPanelsSlider rSPanelsSlider1;
    private javax.swing.JTable tags_table;
    // End of variables declaration//GEN-END:variables
}
