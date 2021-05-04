/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curlp.capapresentacion;

import com.curlp.capadatos.CDUsuario;
import com.curlp.capalogica.CLUsuario;
import com.toedter.calendar.JDateChooser;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marian
 */
public class JFraUsuario extends javax.swing.JFrame {

    /**
     * Creates new form JFraUsuario
     */
    public JFraUsuario() throws SQLException{
        initComponents();
        poblarTablaUsuarios();
        encontrarCorrelativo();
        this.setLocationRelativeTo(null);  
    }
    
     private void limpiarTabla () { 
        DefaultTableModel dtm =  (DefaultTableModel) this.jTblUsuarios.getModel();
        
        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }
    
    private void limpiarControles(){
    }
    
    private void limpiarCamposRellenar(){
        this.jTFNombreUsuario.setText("");
        this.jTFPassword.setText("");
        this.jDCFechaCreacion.setDate(null);
        this.jDCFechaBaja.setDate(null);
        this.jTFIdEstado.setText("");
        this.jTFIdEmpleado.setText("");     
    }
    
     private boolean validarTFNombreUsuario(){
        boolean estado;
        estado = this.jTFNombreUsuario.getText().equals("");
        return estado;
    }
    private boolean validarTFPassword(){
        boolean estado;
        estado = this.jTFPassword.getText().equals("");
        return estado;
    }
    private boolean validarTFFechaCreacion(){
        boolean estado;
        estado = this.jDCFechaCreacion.getDate().equals("");
        return estado;
    }
    
    private boolean validarTFFechaBaja(){
        boolean estado;
        estado = this.jDCFechaBaja.getDate().equals("");
        return estado;
    }
    
     private void poblarTablaUsuarios () throws SQLException {
        limpiarTabla();
        CDUsuario cm = new CDUsuario();
        List<CLUsuario> myList = cm.obtenerListaUsuarios();
        DefaultTableModel temp = (DefaultTableModel) this.jTblUsuarios.getModel();
        
        myList.stream().map((CLUsuario cd)-> {
            Object[] fila = new Object[7];
            
            fila[0] = cd.getIdUsuario();
            fila[1] = cd.getNombreUsuario();
            fila[2] = cd.getPassword();
            fila[3] = cd.getFechaCreacion();
            fila[4] = cd.getFechaBaja();
            fila[5] = cd.getIdEstado();
            fila[6] = cd.getIdEmpleado();
            return fila;
        }).forEachOrdered(temp::addRow);
    }
    
     private void encontrarCorrelativo() throws SQLException{
        CDUsuario cdc = new CDUsuario();
        CLUsuario cl = new CLUsuario();        
        cl.setIdUsuario(cdc.autoIncrementarUsuario());
        this.jTFIdUsuario.setText(String.valueOf(cl.getIdUsuario()));
        
    }
    
    private void insertarUsuario(){
        if (validarTFNombreUsuario()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el nombre del usuario","SIMEC",1);
            this.jTFPassword.requestFocus();
        }else if (validarTFPassword()){
            JOptionPane.showMessageDialog(null, "Debe ingresar la contraseña del usuario ","SIMEC",1);
            this.jDCFechaCreacion.requestFocus();
        }else if (validarTFFechaCreacion()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la fecha de creacion del usuario ","SIMEC",1);
            this.jDCFechaBaja.requestFocus();
        }else if (validarTFFechaBaja()){
            JOptionPane.showMessageDialog(null, "Debe ingresar el fecha de baja del usuario","SIMEC",1);
            this.jDCFechaBaja.requestFocus();  
        }else{
            try {
                CDUsuario cdcm = new CDUsuario();
                CLUsuario cl = new CLUsuario();
                cl.setNombreUsuario(this.jTFNombreUsuario.getText().trim());
                cl.setPassword(this.jTFPassword.getText().trim());
                java.util.Date fecha = jDCFechaCreacion.getDate();
                SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                cl.setFechaCreacion(oDateFormat.format(fecha));
                //cl.setFechaCreacion(this.jTFFechaCreacion.getText().trim());
                java.util.Date fechaB = jDCFechaBaja.getDate();
                SimpleDateFormat oDateFormato = new SimpleDateFormat("yyyy-MM-dd");
                cl.setFechaBaja(oDateFormato.format(fechaB));
                //cl.setFechaBaja(this.jTFFechaBaja.getText().trim());
                cl.setIdEstado(Integer.valueOf(this.jTFIdEstado.getText().trim()));
                cl.setIdEmpleado(Integer.valueOf(this.jTFIdEmpleado.getText().trim()));
                
                cdcm.insertarUsuario(cl);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al almacenar el registro: " + e);
            }
        }
    }
    
    private void guardar () throws SQLException{
        insertarUsuario();
        poblarTablaUsuarios();
        this.limpiarCamposRellenar();
    }
    
     private void editarUsuario() throws SQLException{
        CDUsuario cdcm = new CDUsuario();
        CLUsuario cl = new CLUsuario();
        
        try {
                cl.setIdUsuario(Integer.parseInt(this.jTFIdUsuario.getText().trim()));
                cl.setNombreUsuario(this.jTFNombreUsuario.getText().trim());
                cl.setPassword(this.jTFPassword.getText().trim());
                java.util.Date fecha = jDCFechaCreacion.getDate();
                SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                cl.setFechaCreacion(oDateFormat.format(fecha));
                //cl.setFechaBaja(this.jTFFechaBaja.getText().trim());
                java.util.Date fechaB = jDCFechaBaja.getDate();
                SimpleDateFormat oDateFormato = new SimpleDateFormat("yyyy-MM-dd");
                cl.setFechaBaja(oDateFormato.format(fechaB));
                cl.setIdEstado(Integer.valueOf(this.jTFIdEstado.getText().trim()));
                cl.setIdEmpleado(Integer.valueOf(this.jTFIdEmpleado.getText().trim()));
                cdcm.actualizarUsuario(cl);
                JOptionPane.showMessageDialog(null, "Actualizado exitosamente ", "SIMEC",1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al actualizar el registro: " + e);
        }
    }
     
      private void filaSelecionada (){
        if (this.jTblUsuarios.getSelectedRow() != -1){
            this.jTFIdUsuario.setText(String.valueOf(this.jTblUsuarios.getValueAt(this.jTblUsuarios.getSelectedRow(), 0)));
            this.jTFNombreUsuario.setText(String.valueOf(this.jTblUsuarios.getValueAt(this.jTblUsuarios.getSelectedRow(), 1)));
            this.jTFPassword.setText(String.valueOf(this.jTblUsuarios.getValueAt(this.jTblUsuarios.getSelectedRow(), 2)));
            this.jDCFechaCreacion.setDateFormatString(String.valueOf(this.jTblUsuarios.getValueAt(this.jTblUsuarios.getSelectedRow(), 3)));
            this.jDCFechaBaja.setDateFormatString(String.valueOf(this.jTblUsuarios.getValueAt(this.jTblUsuarios.getSelectedRow(), 4)));
            this.jTFIdEstado.setText(String.valueOf(this.jTblUsuarios.getValueAt(this.jTblUsuarios.getSelectedRow(), 5)));
            this.jTFIdEmpleado.setText(String.valueOf(this.jTblUsuarios.getValueAt(this.jTblUsuarios.getSelectedRow(), 6)));
            
        }
    }

    public JFraUsuario(JButton Regresar1, JButton jBtnEditar, JButton jBtnEliminar, JButton jBtnIngresar, JButton jBtnLimpiar, JDateChooser jDCFechaBaja, JDateChooser jDCFechaCreacion, JLabel jLabel1, JLabel jLabel11, JLabel jLabel12, JLabel jLabel13, JLabel jLabel14, JLabel jLabel3, JLabel jLabel4, JLabel jLabel5, JLabel jLabel6, JLabel jLabel7, JLabel jLblCerrar, JPanel jPanel1, JPanel jPanel3, JPanel jPanel4, JScrollPane jScrollPane1, JTextField jTFIdEmpleado, JTextField jTFIdEstado, JTextField jTFIdUsuario, JTextField jTFNombreUsuario, JTextField jTFPassword, JTable jTblUsuarios) {
        this.Regresar1 = Regresar1;
        this.jBtnEditar = jBtnEditar;
        this.jBtnEliminar = jBtnEliminar;
        this.jBtnIngresar = jBtnIngresar;
        this.jBtnLimpiar = jBtnLimpiar;
        this.jDCFechaBaja = jDCFechaBaja;
        this.jDCFechaCreacion = jDCFechaCreacion;
        this.jLabel1 = jLabel1;
        this.jLabel11 = jLabel11;
        this.jLabel12 = jLabel12;
        this.jLabel13 = jLabel13;
        this.jLabel14 = jLabel14;
        this.jLabel3 = jLabel3;
        this.jLabel4 = jLabel4;
        this.jLabel5 = jLabel5;
        this.jLabel6 = jLabel6;
        this.jLabel7 = jLabel7;
        this.jLblCerrar = jLblCerrar;
        this.jPanel1 = jPanel1;
        this.jPanel3 = jPanel3;
        this.jPanel4 = jPanel4;
        this.jScrollPane1 = jScrollPane1;
        this.jTFIdEmpleado = jTFIdEmpleado;
        this.jTFIdEstado = jTFIdEstado;
        this.jTFIdUsuario = jTFIdUsuario;
        this.jTFNombreUsuario = jTFNombreUsuario;
        this.jTFPassword = jTFPassword;
        this.jTblUsuarios = jTblUsuarios;
    }
    private void editar () throws SQLException {
        editarUsuario();
        poblarTablaUsuarios();
        this.limpiarCamposRellenar();
    }

    
    private void eliminarUsuario(){
    try {
            CDUsuario cdc = new CDUsuario();
            CLUsuario cl = new CLUsuario();
            cl.setIdUsuario(Integer.parseInt(this.jTFIdUsuario.getText().trim()));
            cdc.eliminarUsuario(cl);
            
            JOptionPane.showMessageDialog(null, "Eliminado exitosamente ", "Control",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error al eliminar registro" + ex);
            this.jTFNombreUsuario.requestFocus();

        }
    }
    
    
    
    private void eliminar () throws SQLException{
        int resp = JOptionPane.showConfirmDialog(null, "Seguro que quiere eliminar","control",
                                                 JOptionPane.YES_NO_OPTION);       
        if (resp == JOptionPane.YES_OPTION) {
            try {
                eliminarUsuario();
                poblarTablaUsuarios();
                limpiarCamposRellenar();
                limpiarControles();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "error " + ex);
            }
        }
    }
      
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblUsuarios = new javax.swing.JTable();
        jBtnIngresar = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jBtnLimpiar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        Regresar1 = new javax.swing.JButton();
        jLblCerrar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTFNombreUsuario = new javax.swing.JTextField();
        jTFPassword = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTFIdEstado = new javax.swing.JTextField();
        jTFIdEmpleado = new javax.swing.JTextField();
        jTFIdUsuario = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jDCFechaBaja = new com.toedter.calendar.JDateChooser();
        jDCFechaCreacion = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 204));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(79, 198, 203));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Usuario", "Nombre Usuario", "Password", "Fecha Creacion", "Fecha Baja", "Estado", "ID empleado"
            }
        ));
        jTblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblUsuarios);

        jBtnIngresar.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jBtnIngresar.setText("Ingresar");
        jBtnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnIngresarActionPerformed(evt);
            }
        });

        jBtnEditar.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jBtnEditar.setText("Editar");
        jBtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarActionPerformed(evt);
            }
        });

        jBtnEliminar.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jBtnEliminar.setText("Eliminar");
        jBtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarActionPerformed(evt);
            }
        });

        jBtnLimpiar.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jBtnLimpiar.setText("Limpiar");
        jBtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jBtnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(137, 137, 137)
                        .addComponent(jBtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(143, 143, 143)
                        .addComponent(jBtnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(79, 198, 203));

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        jLabel7.setText("USUARIOS");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel14MousePressed(evt);
            }
        });

        Regresar1.setBackground(new java.awt.Color(79, 198, 203));
        Regresar1.setFont(new java.awt.Font("DejaVu Math TeX Gyre", 1, 12)); // NOI18N
        Regresar1.setText("< Regresar");
        Regresar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Regresar1.setBorderPainted(false);
        Regresar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Regresar1MouseClicked(evt);
            }
        });
        Regresar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Regresar1ActionPerformed(evt);
            }
        });

        jLblCerrar.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLblCerrar.setForeground(new java.awt.Color(255, 255, 255));
        jLblCerrar.setText("X");
        jLblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLblCerrarMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("S I S T E M A    C L I N I C O    S I M E C");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(Regresar1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLblCerrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Regresar1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLblCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(79, 204, 146));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Ingreso de un nuevo Usuario"));

        jLabel3.setText("Nombre del usuario");

        jLabel4.setText("Password");

        jLabel5.setText("Fecha Creacion");

        jTFNombreUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFNombreUsuarioActionPerformed(evt);
            }
        });

        jTFPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFPasswordActionPerformed(evt);
            }
        });

        jLabel6.setText("Fecha baja");

        jLabel11.setText("ID empleado");

        jLabel13.setText("ID Estado");

        jTFIdEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFIdEstadoActionPerformed(evt);
            }
        });

        jTFIdEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFIdEmpleadoActionPerformed(evt);
            }
        });

        jLabel12.setText("ID Usuario");

        jDCFechaBaja.setDateFormatString("yyy- MM-dd");

        jDCFechaCreacion.setDateFormatString("yyy- MM-dd");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel12))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTFIdUsuario)
                    .addComponent(jDCFechaCreacion, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDCFechaBaja, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addComponent(jTFNombreUsuario))
                .addGap(46, 46, 46)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTFPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(jTFIdEstado))
                .addGap(63, 63, 63)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTFIdEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTFPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jTFIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jLabel5)
                        .addComponent(jTFIdEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(jTFIdEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addComponent(jDCFechaBaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDCFechaCreacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MousePressed
        this.dispose();
    }//GEN-LAST:event_jLabel14MousePressed

    private void Regresar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Regresar1MouseClicked
        
        try {
            JFraPrincipal principal = new JFraPrincipal();
            principal.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(JFraUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
           
  
        this.dispose();
    }//GEN-LAST:event_Regresar1MouseClicked

    private void jLblCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLblCerrarMouseClicked
         this.dispose();
    }//GEN-LAST:event_jLblCerrarMouseClicked

    private void jBtnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnIngresarActionPerformed
        try {
            guardar();
        } catch (SQLException ex) {
            Logger.getLogger(JFraConsultaMedica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnIngresarActionPerformed

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
       try {
            editar();
        } catch (SQLException ex) {
            Logger.getLogger(JFraConsultaMedica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnEditarActionPerformed

    private void jBtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarActionPerformed
        try {
            eliminar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR" +   ex );
        }
    }//GEN-LAST:event_jBtnEliminarActionPerformed

    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
      this.limpiarCamposRellenar();
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    private void jTFNombreUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFNombreUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFNombreUsuarioActionPerformed

    private void jTFPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFPasswordActionPerformed

    private void jTFIdEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFIdEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFIdEstadoActionPerformed

    private void jTFIdEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFIdEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFIdEmpleadoActionPerformed

    private void Regresar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Regresar1ActionPerformed
        try {
            JFraPrincipal principal = new JFraPrincipal();
            principal.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(JFraUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        this.dispose();
    }//GEN-LAST:event_Regresar1ActionPerformed

    private void jTblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblUsuariosMouseClicked
       this.filaSelecionada();
    }//GEN-LAST:event_jTblUsuariosMouseClicked

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
            java.util.logging.Logger.getLogger(JFraUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFraUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFraUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFraUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               try {
                    new JFraUsuario().setVisible(true);
                } catch (Exception ex){
                    Logger.getLogger(JFraUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Regresar1;
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnIngresar;
    private javax.swing.JButton jBtnLimpiar;
    private com.toedter.calendar.JDateChooser jDCFechaBaja;
    private com.toedter.calendar.JDateChooser jDCFechaCreacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLblCerrar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFIdEmpleado;
    private javax.swing.JTextField jTFIdEstado;
    private javax.swing.JTextField jTFIdUsuario;
    private javax.swing.JTextField jTFNombreUsuario;
    private javax.swing.JTextField jTFPassword;
    private javax.swing.JTable jTblUsuarios;
    // End of variables declaration//GEN-END:variables
}
