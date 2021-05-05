/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curlp.capapresentacion;

import com.curlp.capadatos.CDHistoriaClinica;
import com.curlp.capalogica.CLHistoriaClinica;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hnunez
 */
public class JFraHistoriaClinica extends javax.swing.JFrame {

    /**
     * Creates new form JFraHistoriaClinica
     */
    public JFraHistoriaClinica() throws SQLException{
        initComponents();
        poblarTablaHistoriaClinica();
        this.setLocationRelativeTo(null);
    }
    
    //Metodo par limpiar la tabla
    private void limpiarTabla() {
        
        DefaultTableModel dtm = (DefaultTableModel) this.jTblHistoria.getModel();
        
        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    
    }
    
    //Metodo para poblar la tabla
    private void poblarTablaHistoriaClinica() throws SQLException{
        limpiarTabla();
        CDHistoriaClinica cdh = new CDHistoriaClinica();
        List<CLHistoriaClinica> miLista = cdh.obtenerListaHistoriaClinica();
        DefaultTableModel temp = (DefaultTableModel) this.jTblHistoria.getModel();
        
        miLista.stream().map((CLHistoriaClinica cl) -> {
            Object[] fila = new Object[13];
            fila[0] = cl.getNumeroIdentidadPaciente();
            fila[1] = cl.getFechaCreacion();
            fila[2] = cl.getCardiobasculares();
            fila[3] = cl.getPulmonares();
            fila[4] = cl.getDigestivo();
            fila[5] = cl.getDiabetes();
            fila[6] = cl.getRenales();
            fila[7] = cl.getQuirurgicos();
            fila[8] = cl.getAlergicos();
            fila[9] = cl.getTransfusiones();
            fila[10] = cl.getMedicamentos();
            fila[11] = cl.getObservaciones();
            fila[12] = cl.getIdUsuario();
            return fila;
        }).forEachOrdered(temp::addRow);
    }
    
    //Metodo para habilitar y deshabilitar botones
    private void habilitarBotones(boolean guardar, boolean editar, boolean eliminar, boolean buscar){
    
            this.jBtnGuardar.setEnabled(guardar);
            this.jBtnEditar.setEnabled(editar);
            this.jBtnEliminar.setEnabled(eliminar);
            this.jBtnBuscar.setEnabled(buscar);
    }
    
    //Metodo Limpiar las TextField
    private void limpiarTextField(){
        this.jTFIdentidadNuevo.setText("");
        this.jTFUsuario.setText("");
        this.jTFObservaciones.setText("");
        this.jTFMedicamentos.setText("");
        this.jTFIdentidad.requestFocus();
    }
    //Metodo para validar la TextField
    private boolean validarTextField(){
        boolean estado;
        estado = !this.jTFIdentidadNuevo.getText().equals("");
        return estado;
    }
    
    //Metodo para insertar Historias Clinicas
    private void insertarHistoriaClinica(){
        if(!validarTextField ()){
            JOptionPane.showMessageDialog(null, "Ingrese Numero de Identidad", "SIMEC", JOptionPane.INFORMATION_MESSAGE);
            this.jTFIdentidadNuevo.requestFocus();
        }else {
            try {
                CDHistoriaClinica cdh = new CDHistoriaClinica();
                CLHistoriaClinica clh = new CLHistoriaClinica();
                clh.setNumeroIdentidadPaciente(this.jTFIdentidadNuevo.getText().trim());
                java.util.Date fecha = jDCFecha.getDate();
                SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                clh.setFechaCreacion(oDateFormat.format(fecha));
                if(this.jRBCardiobascular.isSelected()){
                    clh.setCardiobasculares("si");
                }else{
                    clh.setCardiobasculares("no");
                }
                if(this.jRBPulmonar.isSelected()) {
                    clh.setPulmonares("si");
                }else{
                    clh.setPulmonares("no");
                }
                if(this.jRBDigestivo.isSelected()){
                    clh.setDigestivo("si");
                }else{
                    clh.setDigestivo("no");
                }
                if(this.jRBDiabetes.isSelected()){
                    clh.setDiabetes("si");
                }else {
                    clh.setDiabetes("no");
                }
                if(this.jRBRenales.isSelected()){
                    clh.setRenales("si");
                }else{
                    clh.setRenales("no");
                }
                if(this.jRBQuirurgicos.isSelected()){
                    clh.setQuirurgicos("si");
                }else {
                    clh.setQuirurgicos("no");
                }
                if(this.jRBAlergicos.isSelected()){
                    clh.setAlergicos("si");
                }else{
                    clh.setAlergicos("no");
                }
                if(this.jRBTransfusiones.isSelected()){
                    clh.setTransfusiones("si");
                }else{
                    clh.setTransfusiones("no");
                }
                clh.setMedicamentos(this.jTFMedicamentos.getText().trim());
                clh.setObservaciones(this.jTFObservaciones.getText().trim());
                clh.setIdUsuario(Integer.parseInt(this.jTFUsuario.getText().trim()));
                cdh.insertarHistoriaClinica(clh);
                
                JOptionPane.showMessageDialog(null, "Registro Almacenado Satisfactoriamente", "SIMEC", JOptionPane.INFORMATION_MESSAGE);
                
            }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al Almacenar el registro" + ex);
            this.jTFIdentidadNuevo.requestFocus();
            }
        
        }
    }
    
       //Metodo llamado a insertar Historia Clinica
    private void guardar() throws SQLException {
        insertarHistoriaClinica();
        poblarTablaHistoriaClinica();
        habilitarBotones(true, false, false, true);
        limpiarTextField();
        
    }  
        //Metodo para actualizar Historias Clinicas
    private void actualizarHistoriaClinica(){
        if(!validarTextField ()){
            JOptionPane.showMessageDialog(null, "Ingrese Numero de Identidad", "SIMEC", JOptionPane.INFORMATION_MESSAGE);
            this.jTFIdentidadNuevo.requestFocus();
        }else {
            try {
                CDHistoriaClinica cdh = new CDHistoriaClinica();
                CLHistoriaClinica clh = new CLHistoriaClinica();
                clh.setNumeroIdentidadPaciente(this.jTFIdentidadNuevo.getText().trim());
                java.util.Date fecha = jDCFecha.getDate();
                SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                clh.setFechaCreacion(oDateFormat.format(fecha));
                if(this.jRBCardiobascular.isSelected()){
                    clh.setCardiobasculares("si");
                }else{
                    clh.setCardiobasculares("no");
                }
                if(this.jRBPulmonar.isSelected()) {
                    clh.setPulmonares("si");
                }else{
                    clh.setPulmonares("no");
                }
                if(this.jRBDigestivo.isSelected()){
                    clh.setDigestivo("si");
                }else{
                    clh.setDigestivo("no");
                }
                if(this.jRBDiabetes.isSelected()){
                    clh.setDiabetes("si");
                }else {
                    clh.setDiabetes("no");
                }
                if(this.jRBRenales.isSelected()){
                    clh.setRenales("si");
                }else{
                    clh.setRenales("no");
                }
                if(this.jRBQuirurgicos.isSelected()){
                    clh.setQuirurgicos("si");
                }else {
                    clh.setQuirurgicos("no");
                }
                if(this.jRBAlergicos.isSelected()){
                    clh.setAlergicos("si");
                }else{
                    clh.setAlergicos("no");
                }
                if(this.jRBTransfusiones.isSelected()){
                    clh.setTransfusiones("si");
                }else{
                    clh.setTransfusiones("no");
                }
                clh.setMedicamentos(this.jTFMedicamentos.getText().trim());
                clh.setObservaciones(this.jTFObservaciones.getText().trim());
                clh.setIdUsuario(Integer.parseInt(this.jTFUsuario.getText().trim()));
                cdh.actualizarHistoriaMedica(clh);
                
                JOptionPane.showMessageDialog(null, "Registro Actualizado Satisfactoriamente", "SIMEC", JOptionPane.INFORMATION_MESSAGE);
                
            }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al Actualizar el registro" + ex);
            this.jTFIdentidadNuevo.requestFocus();
            }
        
        }
    }
    //Metodo para seleccionar la fila de una table
    private void filaSeleccionada(){
       
        if (this.jTblHistoria.getSelectedRow() !=-1){
            this.jTFIdentidadNuevo.setText(String.valueOf(this.jTblHistoria.getValueAt(this.jTblHistoria.getSelectedRow(), 0)));
            
           // java.util.Date fecha = jDCFecha.getDate();
            Date oDateFormat;
            try {
                oDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse((String)this.jTblHistoria.getValueAt(this.jTblHistoria.getSelectedRow(), 1));
                this.jDCFecha.setDate(oDateFormat);
            } catch (ParseException ex) {
                Logger.getLogger(JFraHistoriaClinica.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            if("si".equals(this.jTblHistoria.getValueAt(this.jTblHistoria.getSelectedRow(), 2).toString())){ 
                    this.jRBCardiobascular.setSelected(true);
            }
            if("si".equals(this.jTblHistoria.getValueAt(this.jTblHistoria.getSelectedRow(), 3).toString())){ 
                    this.jRBPulmonar.setSelected(true);
            }
            if("si".equals(this.jTblHistoria.getValueAt(this.jTblHistoria.getSelectedRow(), 4).toString())){ 
                    this.jRBDigestivo.setSelected(true);
            }
            if("si".equals(this.jTblHistoria.getValueAt(this.jTblHistoria.getSelectedRow(), 5).toString())){ 
                    this.jRBDiabetes.setSelected(true);
            }
            if("si".equals(this.jTblHistoria.getValueAt(this.jTblHistoria.getSelectedRow(), 6).toString())){ 
                    this.jRBRenales.setSelected(true);
            }
            if("si".equals(this.jTblHistoria.getValueAt(this.jTblHistoria.getSelectedRow(), 7).toString())){ 
                    this.jRBQuirurgicos.setSelected(true);
            }
            if("si".equals(this.jTblHistoria.getValueAt(this.jTblHistoria.getSelectedRow(), 8).toString())){ 
                    this.jRBAlergicos.setSelected(true);
            }
            if("si".equals(this.jTblHistoria.getValueAt(this.jTblHistoria.getSelectedRow(), 9).toString())){ 
                    this.jRBTransfusiones.setSelected(true);
            }
            this.jTFMedicamentos.setText(String.valueOf(this.jTblHistoria.getValueAt(this.jTblHistoria.getSelectedRow(), 10)));
            this.jTFObservaciones.setText(String.valueOf(this.jTblHistoria.getValueAt(this.jTblHistoria.getSelectedRow(), 11)));
            this.jTFUsuario.setText(String.valueOf(this.jTblHistoria.getValueAt(this.jTblHistoria.getSelectedRow(), 12)));
           
            
  
        }
    }
        //Metodo llamado a insertar Historia Clinica
    private void actualizar() throws SQLException {
        actualizarHistoriaClinica();
        poblarTablaHistoriaClinica();
        habilitarBotones(false, true, true, true);
        limpiarTextField();
        
    } 
    
        //Metodo para Eliminar
    private void eliminarHistoriaClinica (){
        try {
            CDHistoriaClinica cdh = new CDHistoriaClinica();
            CLHistoriaClinica clh = new CLHistoriaClinica();
            clh.setNumeroIdentidadPaciente(this.jTFIdentidadNuevo.getText().trim());
            cdh.eliminarHistoria(clh);

            JOptionPane.showMessageDialog(null, "Registro Eliminado Satisfactoriamente", "SIMEC", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Eliminar el registro" + ex);
            this.jTFIdentidadNuevo.requestFocus();
        }

    }
    // Metodo de confirmacion de eliminacion de Historia Clinica
    private void eliminar() throws SQLException{
        int resp = JOptionPane.showConfirmDialog(null, "Estas seguro de eliminar el registro", "SIMEC", JOptionPane.YES_NO_OPTION);
        if(resp == JOptionPane.YES_OPTION){
            try {
                eliminarHistoriaClinica();
                poblarTablaHistoriaClinica();
                habilitarBotones(false, true, true, true);
                limpiarTextField();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error" + ex);
                this.jTFIdentidadNuevo.requestFocus();
            }
        }
    }
    //Metodo para poblar la tabla con condicion de campo busque
    private void llenarTablaPorIdentidad(String numeroIdentidad) throws SQLException{
        limpiarTabla();
        CDHistoriaClinica cdh = new CDHistoriaClinica();
        List<CLHistoriaClinica> miLista = cdh.mostrarHistoriaClinicaCondicion(numeroIdentidad);
        DefaultTableModel temp = (DefaultTableModel) this.jTblHistoria.getModel();
        
        miLista.stream().map((CLHistoriaClinica cl) -> {
            Object[] fila = new Object[13];
            fila[0] = cl.getNumeroIdentidadPaciente();
            fila[1] = cl.getFechaCreacion();
            fila[2] = cl.getCardiobasculares();
            fila[3] = cl.getPulmonares();
            fila[4] = cl.getDigestivo();
            fila[5] = cl.getDiabetes();
            fila[6] = cl.getRenales();
            fila[7] = cl.getQuirurgicos();
            fila[8] = cl.getAlergicos();
            fila[9] = cl.getTransfusiones();
            fila[10] = cl.getMedicamentos();
            fila[11] = cl.getObservaciones();
            fila[12] = cl.getIdUsuario();
            return fila;
        }).forEachOrdered(temp::addRow);
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
        jLabel3 = new javax.swing.JLabel();
        Regresar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblHistoria = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jBtnEditar = new javax.swing.JButton();
        jBtnBuscar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTFIdentidad = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jTFIdentidadNuevo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTFUsuario = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTFObservaciones = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTFMedicamentos = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jBtnGuardar = new javax.swing.JButton();
        jRBCardiobascular = new javax.swing.JRadioButton();
        jRBPulmonar = new javax.swing.JRadioButton();
        jRBDigestivo = new javax.swing.JRadioButton();
        jRBDiabetes = new javax.swing.JRadioButton();
        jRBRenales = new javax.swing.JRadioButton();
        jRBQuirurgicos = new javax.swing.JRadioButton();
        jRBAlergicos = new javax.swing.JRadioButton();
        jRBTransfusiones = new javax.swing.JRadioButton();
        jDCFecha = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(79, 198, 203));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("V I S O R  H I S T O R I A  C L I N I C A");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("X");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel3MousePressed(evt);
            }
        });

        Regresar.setBackground(new java.awt.Color(79, 198, 203));
        Regresar.setFont(new java.awt.Font("DejaVu Math TeX Gyre", 1, 12)); // NOI18N
        Regresar.setText("< Regresar");
        Regresar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Regresar.setBorderPainted(false);
        Regresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RegresarMouseClicked(evt);
            }
        });
        Regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(Regresar)
                .addGap(92, 92, 92)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(Regresar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Historia Clinica", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jTblHistoria.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTblHistoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero ID", "Fecha Creacion", "Cardiobasculares", "Pulmonares", "Digestivo", "Diabetes", "Renales", "Quirurgicos", "Alergicos", "Transfuciones", "Medicamentos", "Observaciones", "Id Usuario"
            }
        ));
        jTblHistoria.setShowGrid(true);
        jTblHistoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblHistoriaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblHistoria);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel3.setBackground(new java.awt.Color(79, 198, 203));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controles", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jBtnEditar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnEditar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBtnEditar.setText("Editar");
        jBtnEditar.setEnabled(false);
        jBtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarActionPerformed(evt);
            }
        });

        jBtnBuscar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnBuscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBtnBuscar.setText("Buscar");
        jBtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarActionPerformed(evt);
            }
        });

        jBtnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnEliminar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBtnEliminar.setText("Eliminar");
        jBtnEliminar.setEnabled(false);
        jBtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Ingrese Numero de Identidad");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTFIdentidad, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFIdentidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(79, 203, 146));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cargar Historia Clinica", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Numero Identidad");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Fecha Creacion");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Usuario");

        jTFUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFUsuarioActionPerformed(evt);
            }
        });

        jTFObservaciones.setColumns(20);
        jTFObservaciones.setRows(5);
        jScrollPane2.setViewportView(jTFObservaciones);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Observaciones");

        jTFMedicamentos.setColumns(20);
        jTFMedicamentos.setRows(5);
        jScrollPane3.setViewportView(jTFMedicamentos);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Medicamentos");

        jBtnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnGuardar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBtnGuardar.setText("Agregar");
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jRBCardiobascular.setBackground(new java.awt.Color(79, 203, 146));
        jRBCardiobascular.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRBCardiobascular.setText("CardioBasculares");
        jRBCardiobascular.setActionCommand("si");

        jRBPulmonar.setBackground(new java.awt.Color(79, 203, 146));
        jRBPulmonar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRBPulmonar.setText("Pulmonares");
        jRBPulmonar.setActionCommand("si");

        jRBDigestivo.setBackground(new java.awt.Color(79, 203, 146));
        jRBDigestivo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRBDigestivo.setText("Digestivo");
        jRBDigestivo.setActionCommand("si");

        jRBDiabetes.setBackground(new java.awt.Color(79, 203, 146));
        jRBDiabetes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRBDiabetes.setText("Diabetes");
        jRBDiabetes.setActionCommand("si");

        jRBRenales.setBackground(new java.awt.Color(79, 203, 146));
        jRBRenales.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRBRenales.setText("Renales");
        jRBRenales.setActionCommand("si");

        jRBQuirurgicos.setBackground(new java.awt.Color(79, 203, 146));
        jRBQuirurgicos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRBQuirurgicos.setText("Quirurgicos");
        jRBQuirurgicos.setActionCommand("si");

        jRBAlergicos.setBackground(new java.awt.Color(79, 203, 146));
        jRBAlergicos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRBAlergicos.setText("Alergicos");
        jRBAlergicos.setActionCommand("si");

        jRBTransfusiones.setBackground(new java.awt.Color(79, 203, 146));
        jRBTransfusiones.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRBTransfusiones.setText("Transfusiones");
        jRBTransfusiones.setActionCommand("si");

        jDCFecha.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jBtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTFIdentidadNuevo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jDCFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(jTFUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRBRenales)
                    .addComponent(jRBQuirurgicos)
                    .addComponent(jRBAlergicos)
                    .addComponent(jRBTransfusiones)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jRBCardiobascular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRBPulmonar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jRBDigestivo)
                    .addComponent(jRBDiabetes))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTFIdentidadNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                            .addComponent(jDCFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jRBCardiobascular)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRBPulmonar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRBDigestivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRBDiabetes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRBRenales)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRBQuirurgicos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRBAlergicos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRBTransfusiones)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MousePressed
        this.dispose();
    }//GEN-LAST:event_jLabel3MousePressed

    private void jTFUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFUsuarioActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        try {
            // TODO add your handling code here:
            guardar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Almacenar el registro" + ex);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jTblHistoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblHistoriaMouseClicked
        // TODO add your handling code here:
        filaSeleccionada();
        habilitarBotones(false, true, true, true);
    }//GEN-LAST:event_jTblHistoriaMouseClicked

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
        try {
            actualizar();
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error al Actualizar el registro" + ex);
        }
        
    }//GEN-LAST:event_jBtnEditarActionPerformed

    private void jBtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarActionPerformed
        try {
            eliminar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Eliminar el registro" + ex);
        }
    }//GEN-LAST:event_jBtnEliminarActionPerformed

    private void jBtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarActionPerformed
        String numIdentidad;
        numIdentidad = this.jTFIdentidad.getText().trim();
       
        if (this.jTFIdentidad.getText().trim().equals("")) {
            this.jTFIdentidad.requestFocus();
            try {
                poblarTablaHistoriaClinica();
            } catch (SQLException ex) {
                Logger.getLogger(JFraHistoriaClinica.class.getName()).log(Level.SEVERE, null, ex);
            }
            habilitarBotones(true, false, false, true);
            limpiarTextField();
        } else {
            try {
               llenarTablaPorIdentidad(numIdentidad);
               this.jTFIdentidad.setText("");
            } catch (SQLException ex) {
                Logger.getLogger(JFraHistoriaClinica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
            
       
       
    }//GEN-LAST:event_jBtnBuscarActionPerformed

    private void RegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RegresarMouseClicked
        try {
            JFraPrincipal principal = new JFraPrincipal();
            principal.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(JFraHistoriaClinica.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.dispose();
    }//GEN-LAST:event_RegresarMouseClicked

    private void RegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegresarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RegresarActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFraHistoriaClinica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new JFraHistoriaClinica().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(JFraHistoriaClinica.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Regresar;
    private javax.swing.JButton jBtnBuscar;
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnGuardar;
    private com.toedter.calendar.JDateChooser jDCFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRBAlergicos;
    private javax.swing.JRadioButton jRBCardiobascular;
    private javax.swing.JRadioButton jRBDiabetes;
    private javax.swing.JRadioButton jRBDigestivo;
    private javax.swing.JRadioButton jRBPulmonar;
    private javax.swing.JRadioButton jRBQuirurgicos;
    private javax.swing.JRadioButton jRBRenales;
    private javax.swing.JRadioButton jRBTransfusiones;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTFIdentidad;
    private javax.swing.JTextField jTFIdentidadNuevo;
    private javax.swing.JTextArea jTFMedicamentos;
    private javax.swing.JTextArea jTFObservaciones;
    private javax.swing.JTextField jTFUsuario;
    private javax.swing.JTable jTblHistoria;
    // End of variables declaration//GEN-END:variables

    private void editar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
