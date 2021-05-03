/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curlp.capapresentacion;


import com.curlp.capadatos.CDEmpleado;
import com.curlp.capalogica.CLEmpleado;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
public class JfraEmpleados extends javax.swing.JFrame {

    /**
     * Creates new form JfraEmpleados
     */
    public JfraEmpleados() throws SQLException{
        initComponents();
        poblarTablaEmpleados();
        encontrarCorrelativo();
        this.setLocationRelativeTo(null);        ;
    }
    
     private void limpiarTabla () { 
        DefaultTableModel dtm =  (DefaultTableModel) this.jTblEmpleados.getModel();
        
        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }
    
    private void limpiarControles(){
    }
    
    private void limpiarCamposRellenar(){
        this.jTFPrimerNombre.setText("");
        this.jTFSegundoNombre.setText("");
        this.jTFPrimerApellido.setText("");
        this.jTFSegundoApellido.setText("");
        this.jTFDireccion.setText("");
        this.jTFTelefonoCelular.setText("");
        this.jTFIdCargo.setText("");
        this.jTFIdEstado.setText("");
        
    }
    
    private boolean validarTFPrimerNombre(){
        boolean estado;
        estado = this.jTFPrimerNombre.getText().equals("");
        return estado;
    }
    private boolean validarTFPrimerApellido(){
        boolean estado;
        estado = this.jTFPrimerApellido.getText().equals("");
        return estado;
    }
    private boolean validarTFDireccion(){
        boolean estado;
        estado = this.jTFDireccion.getText().equals("");
        return estado;
    }
    
    private boolean validarTFTelefonoCelular(){
        boolean estado;
        estado = this.jTFTelefonoCelular.getText().equals("");
        return estado;
    }
 
    
    private void poblarTablaEmpleados () throws SQLException {
        limpiarTabla();
        CDEmpleado cm = new CDEmpleado();
        List<CLEmpleado> myList = cm.obtenerListaEmpleados();
        DefaultTableModel temp = (DefaultTableModel) this.jTblEmpleados.getModel();
        
        myList.stream().map((CLEmpleado cd)-> {
            Object[] fila = new Object[9];
            fila[0] = cd.getIdEmpleado();
            fila[1] = cd.getPrimerNombre();
            fila[2] = cd.getSegundoNombre();
            fila[3] = cd.getPrimerApellido();
            fila[4] = cd.getSegundoApellido();
            fila[5] = cd.getDireccion();
            fila[6] = cd.getTelefonoCelular();
            fila[7] = cd.getIdCargo();
            fila[8] = cd.getIdEstado();
            return fila;
        }).forEachOrdered(temp::addRow);
    }
    
    private void busquedaFiltrada(String idEmpleado) throws SQLException{
   limpiarTabla();
        CDEmpleado cdcm = new CDEmpleado();
        List<CLEmpleado> miList = cdcm.obtenerListaEmpleado(idEmpleado);
        DefaultTableModel dtm = (DefaultTableModel) this.jTblEmpleados.getModel();

        miList.stream().map((CLEmpleado cd) -> {
            Object[] fila = new Object[19];
            fila[0] = cd.getIdEmpleado();
            fila[1] = cd.getPrimerNombre();
            fila[2] = cd.getSegundoNombre();
            fila[3] = cd.getPrimerApellido();
            fila[4] = cd.getSegundoApellido();
            fila[5] = cd.getDireccion();
            fila[6] = cd.getTelefonoCelular();
            fila[7] = cd.getIdCargo();
            fila[8] = cd.getIdEstado();
            return fila;
        }).forEachOrdered(dtm::addRow);
        
    }
    private void insertarEmpleado(){
        if (validarTFPrimerNombre()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el primer nombre","SIMEC",1);
            this.jTFPrimerApellido.requestFocus();
        }else if (validarTFPrimerApellido()){
            JOptionPane.showMessageDialog(null, "Debe ingresar el primer apellido ","SIMEC",1);
            this.jTFDireccion.requestFocus();
        }else if (validarTFDireccion()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la direccion","SIMEC",1);
            this.jTFTelefonoCelular.requestFocus();
        }else if (validarTFTelefonoCelular()){
            JOptionPane.showMessageDialog(null, "Debe ingresar el telefono celular ","SIMEC",1);
            this.jTFTelefonoCelular.requestFocus();  
        }else{
            try {
                CDEmpleado cdcm = new CDEmpleado();
                CLEmpleado cl = new CLEmpleado();
                cl.setPrimerNombre(this.jTFPrimerNombre.getText().trim());
                cl.setSegundoNombre(this.jTFSegundoNombre.getText().trim());
                cl.setPrimerApellido(this.jTFPrimerApellido.getText().trim());
                cl.setSegundoApellido(this.jTFSegundoApellido.getText().trim());
                cl.setDireccion(this.jTFDireccion.getText().trim());
                cl.setTelefonoCelular(this.jTFTelefonoCelular.getText().trim());
                cl.setIdCargo(Integer.valueOf(this.jTFIdCargo.getText().trim()));
                cl.setIdEstado(Integer.valueOf(this.jTFIdEstado.getText().trim()));
                
                cdcm.insertarEmpleado(cl);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al almacenar el registro: " + e);
            }
        }
    }
    
    
    
     private void guardar () throws SQLException{
        insertarEmpleado();
        poblarTablaEmpleados();
        this.limpiarCamposRellenar();
    }
     
     private void editarEmpleado () throws SQLException{
        CDEmpleado cdcm = new CDEmpleado();
        CLEmpleado cl = new CLEmpleado();
        
        try {
                cl.setIdEmpleado(Integer.parseInt(this.jTFIdEmpleado.getText().trim()));
                cl.setPrimerNombre(this.jTFPrimerNombre.getText().trim());
                cl.setSegundoNombre(this.jTFSegundoNombre.getText().trim());
                cl.setPrimerApellido(this.jTFPrimerApellido.getText().trim());
                cl.setSegundoApellido(this.jTFSegundoApellido.getText().trim());
                cl.setDireccion(this.jTFDireccion.getText().trim());
                cl.setTelefonoCelular(this.jTFTelefonoCelular.getText().trim());
                cl.setIdCargo(Integer.valueOf(this.jTFIdCargo.getText().trim()));
                cl.setIdEstado(Integer.valueOf(this.jTFIdEstado.getText().trim()));
                cdcm.actualizarEmpleado(cl);
                JOptionPane.showMessageDialog(null, "Actualizado exitosamente ", "SIMEC",1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al actualizar el registro: " + e);
        }
    }
     
      private void filaSelecionada (){
        if (this.jTblEmpleados.getSelectedRow() != -1){
            this.jTFIdEmpleado.setText(String.valueOf(this.jTblEmpleados.getValueAt(this.jTblEmpleados.getSelectedRow(), 0)));
            this.jTFPrimerNombre.setText(String.valueOf(this.jTblEmpleados.getValueAt(this.jTblEmpleados.getSelectedRow(), 1)));
            this.jTFSegundoNombre.setText(String.valueOf(this.jTblEmpleados.getValueAt(this.jTblEmpleados.getSelectedRow(), 2)));
            this.jTFPrimerApellido.setText(String.valueOf(this.jTblEmpleados.getValueAt(this.jTblEmpleados.getSelectedRow(), 3)));
            this.jTFSegundoApellido.setText(String.valueOf(this.jTblEmpleados.getValueAt(this.jTblEmpleados.getSelectedRow(), 4)));
            this.jTFDireccion.setText(String.valueOf(this.jTblEmpleados.getValueAt(this.jTblEmpleados.getSelectedRow(), 5)));
            this.jTFTelefonoCelular.setText(String.valueOf(this.jTblEmpleados.getValueAt(this.jTblEmpleados.getSelectedRow(), 6)));
            this.jTFIdCargo.setText(String.valueOf(this.jTblEmpleados.getValueAt(this.jTblEmpleados.getSelectedRow(), 7)));
            this.jTFIdEstado.setText(String.valueOf(this.jTblEmpleados.getValueAt(this.jTblEmpleados.getSelectedRow(), 8)));
        }
    }
    
    private void eliminarEmpleado(){

    try {
            CDEmpleado cdc = new CDEmpleado();
            CLEmpleado cl = new CLEmpleado();
            cl.setIdEmpleado(Integer.parseInt(this.jTFIdEmpleado.getText().trim()));
            cdc.eliminarEmpleado(cl);
            
            JOptionPane.showMessageDialog(null, "Eliminado exitosamente ", "Control",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error al eliminar registro" + ex);
            this.jTFPrimerNombre.requestFocus();

        }

    }
    
    
    
    private void eliminar () throws SQLException{
        int resp = JOptionPane.showConfirmDialog(null, "Seguro que quiere eliminar","control",
                                                 JOptionPane.YES_NO_OPTION);       
        if (resp == JOptionPane.YES_OPTION) {
            try {
                eliminarEmpleado();
                poblarTablaEmpleados();
                limpiarCamposRellenar();
                limpiarControles();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "error " + ex);
            }
        }
    }
    
    private void editar () throws SQLException {
        editarEmpleado();
        poblarTablaEmpleados();
        this.limpiarCamposRellenar();
    }
    
    private void encontrarCorrelativo() throws SQLException{
        CDEmpleado cdc = new CDEmpleado();
        CLEmpleado cl = new CLEmpleado();        
        cl.setIdEmpleado(cdc.autoIncrementarEmpleado());
        this.jTFIdEmpleado.setText(String.valueOf(cl.getIdEmpleado()));
        
    }

    public JfraEmpleados(JButton Regresar1, JButton jBtnEditar, JButton jBtnEliminarEmpleados, JButton jBtnIngresarEmpleado, JButton jBtnLimpiar, JLabel jLabel1, JLabel jLabel11, JLabel jLabel12, JLabel jLabel13, JLabel jLabel14, JLabel jLabel3, JLabel jLabel4, JLabel jLabel5, JLabel jLabel6, JLabel jLabel7, JLabel jLabel8, JLabel jLabel9, JLabel jLbCerrar, JPanel jPanel1, JPanel jPanel3, JPanel jPanel4, JScrollPane jScrollPane1, JTextField jTFDireccion, JTextField jTFIdCargo, JTextField jTFIdEmpleado, JTextField jTFIdEstado, JTextField jTFPrimerApellido, JTextField jTFPrimerNombre, JTextField jTFSegundoApellido, JTextField jTFSegundoNombre, JTextField jTFTelefonoCelular, JTable jTblEmpleados) {
        this.Regresar1 = Regresar1;
        this.jBtnEditar = jBtnEditar;
        this.jBtnEliminarEmpleados = jBtnEliminarEmpleados;
        this.jBtnIngresarEmpleado = jBtnIngresarEmpleado;
        this.jBtnLimpiar = jBtnLimpiar;
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
        this.jLabel8 = jLabel8;
        this.jLabel9 = jLabel9;
        this.jLbCerrar = jLbCerrar;
        this.jPanel1 = jPanel1;
        this.jPanel3 = jPanel3;
        this.jPanel4 = jPanel4;
        this.jScrollPane1 = jScrollPane1;
        this.jTFDireccion = jTFDireccion;
        this.jTFIdCargo = jTFIdCargo;
        this.jTFIdEmpleado = jTFIdEmpleado;
        this.jTFIdEstado = jTFIdEstado;
        this.jTFPrimerApellido = jTFPrimerApellido;
        this.jTFPrimerNombre = jTFPrimerNombre;
        this.jTFSegundoApellido = jTFSegundoApellido;
        this.jTFSegundoNombre = jTFSegundoNombre;
        this.jTFTelefonoCelular = jTFTelefonoCelular;
        this.jTblEmpleados = jTblEmpleados;
    }

    

   

   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTFPrimerNombre = new javax.swing.JTextField();
        jTFSegundoNombre = new javax.swing.JTextField();
        jTFPrimerApellido = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTFSegundoApellido = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTFDireccion = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTFTelefonoCelular = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTFIdEmpleado = new javax.swing.JTextField();
        jTFIdCargo = new javax.swing.JTextField();
        jTFIdEstado = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblEmpleados = new javax.swing.JTable();
        jBtnEditar = new javax.swing.JButton();
        jBtnLimpiar = new javax.swing.JButton();
        jBtnEliminarEmpleados = new javax.swing.JButton();
        jBtnIngresarEmpleado = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        Regresar1 = new javax.swing.JButton();
        jLbCerrar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 204));
        setUndecorated(true);

        jPanel3.setBackground(new java.awt.Color(79, 204, 146));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setPreferredSize(new java.awt.Dimension(447, 328));

        jLabel3.setText("Primer Nombre");

        jLabel4.setText("Segundo Nombre");

        jLabel5.setText("Primer Apellido");

        jTFPrimerNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFPrimerNombreActionPerformed(evt);
            }
        });

        jTFSegundoNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFSegundoNombreActionPerformed(evt);
            }
        });

        jTFPrimerApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFPrimerApellidoActionPerformed(evt);
            }
        });

        jLabel6.setText("Segundo Apellido");

        jTFSegundoApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFSegundoApellidoActionPerformed(evt);
            }
        });

        jLabel9.setText("Telefono Celular");

        jTFDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFDireccionActionPerformed(evt);
            }
        });

        jLabel12.setText("Direccion");

        jTFTelefonoCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFTelefonoCelularActionPerformed(evt);
            }
        });

        jLabel11.setText("Cargo");

        jLabel13.setText("Estado");

        jLabel8.setText("ID empleado");

        jTFIdEmpleado.setEditable(false);
        jTFIdEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFIdEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFIdEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFPrimerNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTFSegundoNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                    .addComponent(jTFTelefonoCelular))
                .addGap(43, 43, 43)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFPrimerApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFIdCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(81, 81, 81)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFSegundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFIdEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTFIdEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFPrimerNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTFSegundoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jTFPrimerApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFSegundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTFDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jTFIdEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(jTFIdCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFTelefonoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(188, 188, 188))
        );

        jPanel1.setBackground(new java.awt.Color(79, 198, 203));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdEmpleado", "Primer Nombre", "Segundo Nombre", "Primer Apellidol", "Segundo Apellido", "Direccion", "Telefono Celular", "Id Cargo", "Id Estado"
            }
        ));
        jTblEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblEmpleadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblEmpleados);

        jBtnEditar.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jBtnEditar.setText("Editar");
        jBtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarActionPerformed(evt);
            }
        });

        jBtnLimpiar.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jBtnLimpiar.setText("Limpiar");
        jBtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLimpiarActionPerformed(evt);
            }
        });

        jBtnEliminarEmpleados.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jBtnEliminarEmpleados.setText("Eliminar");
        jBtnEliminarEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarEmpleadosActionPerformed(evt);
            }
        });

        jBtnIngresarEmpleado.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jBtnIngresarEmpleado.setText("Ingresar");
        jBtnIngresarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnIngresarEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jBtnIngresarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113)
                .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnEliminarEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108)
                .addComponent(jBtnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEliminarEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnIngresarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 18, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(79, 198, 203));

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        jLabel7.setText("EMPLEADOS");

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

        jLbCerrar.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLbCerrar.setForeground(new java.awt.Color(255, 255, 255));
        jLbCerrar.setText("X");
        jLbCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbCerrarMouseClicked(evt);
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
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(Regresar1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(jLabel1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                .addComponent(jLbCerrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(Regresar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLbCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1218, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnIngresarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnIngresarEmpleadoActionPerformed
        try {
            guardar();
        } catch (SQLException ex) {
            Logger.getLogger(JFraConsultaMedica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnIngresarEmpleadoActionPerformed

    private void jLabel14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MousePressed
        this.dispose();
    }//GEN-LAST:event_jLabel14MousePressed

    private void Regresar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Regresar1MouseClicked
        
           
        try {
            JFraPrincipal principal = new JFraPrincipal();
             principal.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(JfraEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        this.dispose();
    }//GEN-LAST:event_Regresar1MouseClicked

    private void jLbCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbCerrarMouseClicked
         this.dispose();
    }//GEN-LAST:event_jLbCerrarMouseClicked

    private void jTFIdEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFIdEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFIdEmpleadoActionPerformed

    private void jTFTelefonoCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFTelefonoCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFTelefonoCelularActionPerformed

    private void jTFDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFDireccionActionPerformed

    private void jTFSegundoApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFSegundoApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFSegundoApellidoActionPerformed

    private void jTFPrimerApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFPrimerApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFPrimerApellidoActionPerformed

    private void jTFSegundoNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFSegundoNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFSegundoNombreActionPerformed

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
      try {
            editar();
        } catch (SQLException ex) {
            Logger.getLogger(JFraConsultaMedica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnEditarActionPerformed

    private void jTblEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblEmpleadosMouseClicked
       this.filaSelecionada();
    }//GEN-LAST:event_jTblEmpleadosMouseClicked

    private void jBtnEliminarEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarEmpleadosActionPerformed
        try {
            eliminar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR" +   ex );
        }
    }//GEN-LAST:event_jBtnEliminarEmpleadosActionPerformed

    private void jTFPrimerNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFPrimerNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFPrimerNombreActionPerformed

    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        this.limpiarCamposRellenar();
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

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
            java.util.logging.Logger.getLogger(JfraEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JfraEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JfraEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JfraEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               try {
                    new JfraEmpleados().setVisible(true);
                } catch (Exception ex){
                    Logger.getLogger(JfraEmpleados.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Regresar1;
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnEliminarEmpleados;
    private javax.swing.JButton jBtnIngresarEmpleado;
    private javax.swing.JButton jBtnLimpiar;
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLbCerrar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFDireccion;
    private javax.swing.JTextField jTFIdCargo;
    private javax.swing.JTextField jTFIdEmpleado;
    private javax.swing.JTextField jTFIdEstado;
    private javax.swing.JTextField jTFPrimerApellido;
    private javax.swing.JTextField jTFPrimerNombre;
    private javax.swing.JTextField jTFSegundoApellido;
    private javax.swing.JTextField jTFSegundoNombre;
    private javax.swing.JTextField jTFTelefonoCelular;
    private javax.swing.JTable jTblEmpleados;
    // End of variables declaration//GEN-END:variables
}
