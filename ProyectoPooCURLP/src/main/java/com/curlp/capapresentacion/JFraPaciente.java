/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curlp.capapresentacion;

import com.curlp.capadatos.CDPaciente;
import com.curlp.capalogica.CLPaciente;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nesto
 */
public class JFraPaciente extends javax.swing.JFrame {

    /**
     * Creates new form JFraPaciente
     */
    public JFraPaciente() throws SQLException{
        initComponents();
        poblarTablaPaciente();
        //encontrarCorrelativo();
        this.jTFNumeroidentidad.requestFocus();
        this.setLocationRelativeTo(null);
    }

    // Metodo  para limpiar los datos de la tabla   
    private void limpiarTabla(){
        DefaultTableModel dtm = (DefaultTableModel) this.jTblPaciente.getModel();
    
        while (dtm.getRowCount() >0){
        dtm.removeRow(0);
        }
    }
    
    // Metodo para poblar de datos la Tabla
    private void poblarTablaPaciente () throws SQLException{
    
        limpiarTabla();
        
        CDPaciente cdc = new CDPaciente();
        List<CLPaciente> miLista = cdc.obtenerListaPaciente();
        DefaultTableModel temp = (DefaultTableModel) this.jTblPaciente.getModel();
        
        miLista.stream().map((CLPaciente cl)->{
            Object [] fila = new Object [15];
            fila [0] = cl.getNumeroIdentidad();
            fila [1] = cl.getPrimerNombre();
            fila [2] = cl.getSegundoNombre();
            fila [3] = cl.getPrimerApellido();
            fila [4] = cl.getSegundoApellido();
            fila [5] = cl.getAntecedentesFamiliares();
            fila [6] = cl.getFechaNacimiento();
            fila [7] = cl.getTipoSangre();
            fila [8] = cl.getDireccion();
            fila [9] = cl.getTelefonoCelular();
            fila [10] = cl.getPeso();
            fila [11] = cl.getEstatura();
            fila [12] = cl.getCiudadProcedencia();
            fila [13] = cl.getEmail();
            fila [14] = cl.getIdSexo();
            return fila;
        }).forEachOrdered(temp::addRow);
    }
    
    
//    // Metodo para habilitar y desabilitar botones
//    private void habilitarBotones (boolean guardar, boolean editar, boolean eliminar,
//                                    boolean limpiar){
//    this.BtnGuardar.setEnabled(guardar);
//    this.BtnEditar.setEnabled(editar);
//    this.BtnEliminar.setEnabled(eliminar);
//    this.BtnLimpiar.setEnabled(limpiar);
//    }
//    
    //Metodos para limpiar las TextField
    private void limpiarTextField(){
        this.jTFPrimernombre.setText("");
        this.jTFSegundonombre.setText("");
        this.jTFPrimerapellido.setText("");
        this.jTFSegundoapellido.setText("");
        this.jTFAntecedentes.setText("");
        this.jDCFechaNacimiento.setDateFormatString("");
        //this.jTFFechanacimiento.setText("");
        this.jTFTiposangre.setText("");
        this.jTFDireccion.setText("");
        this.jTFTelefono.setText("");
        this.jTFPeso.setText("");
        this.jTFEstatura.setText("");
        this.jTFCiudadprocedencia.setText("");
        this.jTFEmail.setText("");
        this.jTFIdsexo.setText("");
        this.jTFNumeroidentidad.requestFocus();
    }
//    
//    
    //Metodo para validala TextFiel
    private boolean validarTextField(){
        boolean estado;
        
        estado = !this.jTFNumeroidentidad.getText().equals("");
        return estado;
    }
    
    //Metodo para insertar un paciente en la tabla
    private void insertarPaciente(){
    if (!validarTextField()){
    JOptionPane.showMessageDialog(null, "Tiene que ingrewsar numero de identidad ","Control",
                        JOptionPane.INFORMATION_MESSAGE);
       this.jTFNumeroidentidad.requestFocus();            
        }else{
        try{
            CDPaciente cdc = new CDPaciente();
            CLPaciente cl = new CLPaciente();
            
           cl.setNumeroIdentidad(this.jTFNumeroidentidad.getText().trim());
            cl.setPrimerNombre(this.jTFPrimernombre.getText().trim());
            cl.setSegundoNombre(this.jTFSegundonombre.getText().trim());
            cl.setPrimerApellido(this.jTFPrimerapellido.getText().trim());
            cl.setSegundoApellido(this.jTFSegundoapellido.getText().trim());
            cl.setAntecedentesFamiliares(this.jTFAntecedentes.getText().trim());
            java.util.Date fecha = jDCFechaNacimiento.getDate();
            SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            cl.setFechaNacimiento(oDateFormat.format(fecha));
            cl.setTipoSangre(this.jTFTiposangre.getText().trim());
            cl.setDireccion(this.jTFDireccion.getText().trim());
            cl.setTelefonoCelular(this.jTFTelefono.getText().trim());
            cl.setPeso(Double.parseDouble(this.jTFPeso.getText().trim()));
            cl.setEstatura(Double.parseDouble(this.jTFEstatura.getText().trim()));
            cl.setCiudadProcedencia(this.jTFCiudadprocedencia.getText().trim());
            cl.setEmail(this.jTFEmail.getText().trim());
            cl.setIdSexo(Integer.parseInt(this.jTFIdsexo.getText().trim()));
            
            cdc.insertarPaciente(cl);
             JOptionPane.showMessageDialog(null, "Registro almacenado exitosamente ","Control",
                        JOptionPane.INFORMATION_MESSAGE);
            
            
        } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "error" +   ex );
       this.jTFPrimernombre.requestFocus();  
            
    }
    
    }}
  
    // Metodo para llamr el metodo de insertar paciente 
    private void guardar()throws SQLException{
        insertarPaciente();
        poblarTablaPaciente();
        //habilitarBotones(true, false, false,true);
        limpiarTextField();
    }

    // Metodo para actualizar un registro de ela tabla paciente
    
    private void actualizarPaciente() {
        if (!validarTextField()) {
            JOptionPane.showMessageDialog(null, "Tiene que ingrewsar numero de identidad ", "Control",
                    JOptionPane.INFORMATION_MESSAGE);
            this.jTFNumeroidentidad.requestFocus();
        } else {
            try {
                CDPaciente cdc = new CDPaciente();
                CLPaciente cl = new CLPaciente();

                cl.setNumeroIdentidad(this.jTFNumeroidentidad.getText().trim());
                cl.setPrimerNombre(this.jTFPrimernombre.getText().trim());
                cl.setSegundoNombre(this.jTFSegundonombre.getText().trim());
                cl.setPrimerApellido(this.jTFPrimerapellido.getText().trim());
                cl.setSegundoApellido(this.jTFSegundoapellido.getText().trim());
                cl.setAntecedentesFamiliares(this.jTFAntecedentes.getText().trim());
                java.util.Date fecha = jDCFechaNacimiento.getDate();
                SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                cl.setFechaNacimiento(oDateFormat.format(fecha));
                cl.setTipoSangre(this.jTFTiposangre.getText().trim());
                cl.setDireccion(this.jTFDireccion.getText().trim());
                cl.setTelefonoCelular(this.jTFTelefono.getText().trim());
                cl.setPeso(Double.parseDouble(this.jTFPeso.getText().trim()));
                cl.setEstatura(Double.parseDouble(this.jTFEstatura.getText().trim()));
                cl.setCiudadProcedencia(this.jTFCiudadprocedencia.getText().trim());
                cl.setEmail(this.jTFEmail.getText().trim());
                cl.setIdSexo(Integer.valueOf(this.jTFIdsexo.getText().trim()));
                cdc.actualizarPaciente(cl);

                JOptionPane.showMessageDialog(null, "Actualizado exitosamente ", "Control",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "error al actualizar registro" + ex);
                this.jTFPrimernombre.requestFocus();

            }
        }
    }
//  
     // Metodo para seleccionar  los datos de la fila y asi poder modificarlos 
     private void filaSeleccionada(){
        if (this.jTblPaciente.getSelectedRow() != -1){
           
            this.jTFNumeroidentidad.setText(String.valueOf(this.jTblPaciente.getValueAt(this.jTblPaciente.getSelectedRow(),0)));
            this.jTFPrimernombre.setText(String.valueOf(this.jTblPaciente.getValueAt(this.jTblPaciente.getSelectedRow(),1)));
            this.jTFSegundonombre.setText(String.valueOf(this.jTblPaciente.getValueAt(this.jTblPaciente.getSelectedRow(),2)));
            this.jTFPrimerapellido.setText(String.valueOf(this.jTblPaciente.getValueAt(this.jTblPaciente.getSelectedRow(),3)));
            this.jTFSegundoapellido.setText(String.valueOf(this.jTblPaciente.getValueAt(this.jTblPaciente.getSelectedRow(),4)));
            this.jTFAntecedentes.setText(String.valueOf(this.jTblPaciente.getValueAt(this.jTblPaciente.getSelectedRow(),5)));
            this.jDCFechaNacimiento.setDateFormatString(String.valueOf(this.jTblPaciente.getValueAt(this.jTblPaciente.getSelectedRow(),6)));
            this.jTFTiposangre.setText(String.valueOf(this.jTblPaciente.getValueAt(this.jTblPaciente.getSelectedRow(),7)));
            this.jTFDireccion.setText(String.valueOf(this.jTblPaciente.getValueAt(this.jTblPaciente.getSelectedRow(),8)));
            this.jTFTelefono.setText(String.valueOf(this.jTblPaciente.getValueAt(this.jTblPaciente.getSelectedRow(),9)));
            this.jTFPeso.setText(String.valueOf(this.jTblPaciente.getValueAt(this.jTblPaciente.getSelectedRow(),10)));
            this.jTFEstatura.setText(String.valueOf(this.jTblPaciente.getValueAt(this.jTblPaciente.getSelectedRow(),11)));
            this.jTFCiudadprocedencia.setText(String.valueOf(this.jTblPaciente.getValueAt(this.jTblPaciente.getSelectedRow(),12)));
            this.jTFEmail.setText(String.valueOf(this.jTblPaciente.getValueAt(this.jTblPaciente.getSelectedRow(),13)));
            this.jTFIdsexo.setText(String.valueOf(this.jTblPaciente.getValueAt(this.jTblPaciente.getSelectedRow(),14)));
        } 
     }
//    
    // Metodo para llamar el metodo de actualizar registro de la tabla
    private void editar()throws SQLException{
        actualizarPaciente();
        poblarTablaPaciente();
    //habilitarBotones(true, false, false,true);
        limpiarTextField();
   //encontrarCorrelativo();
    } 
    
   // Metodo para eliminar
    private void eliminarPaciente(){

        try {
            CDPaciente cdc = new CDPaciente();
            CLPaciente cl = new CLPaciente();
            cl.setNumeroIdentidad(this.jTFNumeroidentidad.getText().trim());
            cdc.eliminarPaciente(cl);
            
            JOptionPane.showMessageDialog(null, "Eliminado exitosamente ", "Control",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error al eliminar registro" + ex);
            this.jTFPrimernombre.requestFocus();

        }

    }
    private void eliminar () throws SQLException{
        int resp = JOptionPane.showConfirmDialog(null, "Seguro que quiere eliminar","control",
                                                 JOptionPane.YES_NO_OPTION);
       
        if (resp == JOptionPane.YES_OPTION) {
            try {
                eliminarPaciente();
                poblarTablaPaciente();
                //habilitarBotones(true, false, false, true);
                limpiarTextField();
                //encontrarCorrelativo();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "error " + ex);
            }
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        Regresar1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblPaciente = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jTFNumeroidentidad = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTFPrimernombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTFSegundonombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTFPrimerapellido = new javax.swing.JTextField();
        jTFSegundoapellido = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jDCFechaNacimiento = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTFDireccion = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTFIdsexo = new javax.swing.JTextField();
        jTFCiudadprocedencia = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTFEmail = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTFTelefono = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTFPeso = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTFEstatura = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTFTiposangre = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTFAntecedentes = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        BtnLimpiar = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnEditar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 204));
        setFocusCycleRoot(false);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(79, 198, 203));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("S I S T E M A    C L I N I C O    S I M E C");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("X");
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel19MousePressed(evt);
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

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INFORMACION PACIENTES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        jTblPaciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero Identidad", "Primer Nombre", "Segundo Nombre", "Primer Apellido", "Segundo Apellido", "Antecedentes Familiares", "Fecha de Nacimiento", "Tipo de Sangre", "Direccion", "Telefono Celular", "Peso", "Estatura", "Ciudad Procedencia", "Email", "Sexo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTblPaciente.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTblPaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblPacienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblPaciente);
        if (jTblPaciente.getColumnModel().getColumnCount() > 0) {
            jTblPaciente.getColumnModel().getColumn(0).setResizable(false);
            jTblPaciente.getColumnModel().getColumn(0).setPreferredWidth(200);
            jTblPaciente.getColumnModel().getColumn(1).setResizable(false);
            jTblPaciente.getColumnModel().getColumn(1).setPreferredWidth(200);
            jTblPaciente.getColumnModel().getColumn(2).setResizable(false);
            jTblPaciente.getColumnModel().getColumn(2).setPreferredWidth(200);
            jTblPaciente.getColumnModel().getColumn(3).setResizable(false);
            jTblPaciente.getColumnModel().getColumn(3).setPreferredWidth(200);
            jTblPaciente.getColumnModel().getColumn(4).setResizable(false);
            jTblPaciente.getColumnModel().getColumn(4).setPreferredWidth(200);
            jTblPaciente.getColumnModel().getColumn(5).setResizable(false);
            jTblPaciente.getColumnModel().getColumn(5).setPreferredWidth(200);
            jTblPaciente.getColumnModel().getColumn(6).setResizable(false);
            jTblPaciente.getColumnModel().getColumn(6).setPreferredWidth(200);
            jTblPaciente.getColumnModel().getColumn(7).setResizable(false);
            jTblPaciente.getColumnModel().getColumn(7).setPreferredWidth(200);
            jTblPaciente.getColumnModel().getColumn(8).setResizable(false);
            jTblPaciente.getColumnModel().getColumn(8).setPreferredWidth(200);
            jTblPaciente.getColumnModel().getColumn(9).setResizable(false);
            jTblPaciente.getColumnModel().getColumn(9).setPreferredWidth(200);
            jTblPaciente.getColumnModel().getColumn(10).setResizable(false);
            jTblPaciente.getColumnModel().getColumn(10).setPreferredWidth(200);
            jTblPaciente.getColumnModel().getColumn(11).setResizable(false);
            jTblPaciente.getColumnModel().getColumn(11).setPreferredWidth(200);
            jTblPaciente.getColumnModel().getColumn(12).setResizable(false);
            jTblPaciente.getColumnModel().getColumn(12).setPreferredWidth(200);
            jTblPaciente.getColumnModel().getColumn(13).setResizable(false);
            jTblPaciente.getColumnModel().getColumn(13).setPreferredWidth(200);
            jTblPaciente.getColumnModel().getColumn(14).setResizable(false);
            jTblPaciente.getColumnModel().getColumn(14).setPreferredWidth(200);
        }

        jPanel2.setBackground(new java.awt.Color(79, 203, 146));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingreso Paciente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jPanel5.setBackground(new java.awt.Color(79, 203, 146));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Generales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Numero Identidad");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Primer Nombre");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Segundo Nombre");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Primer Apellido");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Segundo Apellido");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Fecha Nacimiento");

        jDCFechaNacimiento.setDateFormatString("yyy-MM-dd");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Telefono Celular");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Direccion");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Id Sexo");

        jTFIdsexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFIdsexoActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Ciudad Procedencia");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Email");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFNumeroidentidad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTFPrimernombre, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTFSegundonombre, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTFPrimerapellido, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTFSegundoapellido, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTFCiudadprocedencia)
                            .addComponent(jTFIdsexo)
                            .addComponent(jDCFechaNacimiento, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jTFTelefono)
                            .addComponent(jTFEmail)))
                    .addComponent(jTFDireccion))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDCFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTFNumeroidentidad, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFPrimernombre, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFSegundonombre, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFIdsexo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTFPrimerapellido, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTFEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel14)
                        .addComponent(jTFCiudadprocedencia, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTFSegundoapellido, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(40, 40, 40))
        );

        jPanel6.setBackground(new java.awt.Color(79, 203, 146));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Medicos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Peso");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Estatura");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Antecedentes Familiares");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Tipo de Sangre");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFEstatura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTFPeso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTFTiposangre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTFAntecedentes, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFTiposangre, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(13, 13, 13)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFEstatura, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFAntecedentes, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(79, 203, 146));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Acciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        BtnLimpiar.setBackground(new java.awt.Color(255, 255, 255));
        BtnLimpiar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnLimpiar.setText("Limpiar");
        BtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLimpiarActionPerformed(evt);
            }
        });

        BtnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        BtnGuardar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnGuardar.setText("Guardar");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });

        BtnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        BtnEliminar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnEliminar.setText("Eliminar");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });

        BtnEditar.setBackground(new java.awt.Color(255, 255, 255));
        BtnEditar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnEditar.setText("Editar");
        BtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(BtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(79, 203, 146));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar Por Identidad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Buscar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Regresar1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Regresar1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTblPacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblPacienteMouseClicked
        filaSeleccionada();
//    habilitarBotones(false, true,  true, true);
    }//GEN-LAST:event_jTblPacienteMouseClicked

    private void jLabel19MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MousePressed
        this.dispose();
    }//GEN-LAST:event_jLabel19MousePressed

    private void Regresar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Regresar1MouseClicked
          try {
            JFraPrincipal principal = new JFraPrincipal();
            principal.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(JFraHistoriaClinica.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.dispose();
     
    }//GEN-LAST:event_Regresar1MouseClicked

    private void jTFIdsexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFIdsexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFIdsexoActionPerformed

    private void BtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLimpiarActionPerformed
        limpiarTextField();
    }//GEN-LAST:event_BtnLimpiarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        try {
            eliminar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR" +   ex );
        }
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditarActionPerformed
        try {
            editar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR" +   ex );
        }
    }//GEN-LAST:event_BtnEditarActionPerformed

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        try {
            guardar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR" +   ex );
        }
    }//GEN-LAST:event_BtnGuardarActionPerformed

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
            java.util.logging.Logger.getLogger(JFraPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFraPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFraPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFraPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JFraPaciente().setVisible(true);
                } catch (SQLException ex) {
                     JOptionPane.showMessageDialog(null, "error" +   ex );
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnEditar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnLimpiar;
    private javax.swing.JButton Regresar1;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDCFechaNacimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFAntecedentes;
    private javax.swing.JTextField jTFCiudadprocedencia;
    private javax.swing.JTextField jTFDireccion;
    private javax.swing.JTextField jTFEmail;
    private javax.swing.JTextField jTFEstatura;
    private javax.swing.JTextField jTFIdsexo;
    private javax.swing.JTextField jTFNumeroidentidad;
    private javax.swing.JTextField jTFPeso;
    private javax.swing.JTextField jTFPrimerapellido;
    private javax.swing.JTextField jTFPrimernombre;
    private javax.swing.JTextField jTFSegundoapellido;
    private javax.swing.JTextField jTFSegundonombre;
    private javax.swing.JTextField jTFTelefono;
    private javax.swing.JTextField jTFTiposangre;
    private javax.swing.JTable jTblPaciente;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
