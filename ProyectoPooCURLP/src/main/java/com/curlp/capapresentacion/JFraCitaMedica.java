/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curlp.capapresentacion;

import com.curlp.capadatos.CDCitaMedica;
import com.curlp.capalogica.CLCitaMedica;
import java.awt.Image;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admi
 */
public class JFraCitaMedica extends javax.swing.JFrame {

    /**
     * Creates new form JFraCitaMedica
     */
    public JFraCitaMedica() throws SQLException {
        initComponents();
        encontrarCorrelativo();
        //poblarTablaCitas();
        this.setLocationRelativeTo(null);
        
    }
    //Metodo para limpiar tabla
    private void limpiarTabla () { 
        DefaultTableModel dtm =  (DefaultTableModel) this.jTblCitaMedica.getModel();
        
        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }
    //Metodo para poblar tabla
    private void poblarTablaCitas() throws SQLException {
        limpiarTabla();
        CDCitaMedica cm = new CDCitaMedica();
        List<CLCitaMedica> myList = cm.obtenerListaCitaMedica();
        DefaultTableModel temp = (DefaultTableModel) this.jTblCitaMedica.getModel();
        
        myList.stream().map((CLCitaMedica cd) -> {
            Object[] fila = new Object[11];
            fila[0] = cd.getNumeroIdentidad();
            fila[1] = cd.getNombrePaciente();
            fila[2] = cd.getIdCitaMedica();
            fila[3] = cd.getObservaciones();
            fila[4] = cd.getFecha();
            fila[5] = cd.getHoraInicio();
            fila[6] = cd.getHoraFinal();
            fila[7] = cd.getIdUsuario();
            fila[8] = cd.getNombreUsuario();
            fila[9] = cd.getNombreEmpleado();
            fila[10] = cd.getCargo();
            return fila;
        }).forEachOrdered(temp::addRow);
    
    }
    
    private void encontrarCorrelativo() throws SQLException{
        CDCitaMedica cdc = new CDCitaMedica();
        CLCitaMedica clc = new CLCitaMedica();
        clc.setIdCitaMedica(cdc.autoIncrementarCitaId());
        this.jTFIdCita.setText(String.valueOf(clc.getIdCitaMedica()));
    }
    private void busquedaCitaMedica(String identidad) throws SQLException{
        limpiarTabla();
        CDCitaMedica cdcm = new CDCitaMedica();
        List<CLCitaMedica> miList = cdcm.busquedaCita(identidad);
        DefaultTableModel dtm = (DefaultTableModel) this.jTblCitaMedica.getModel();

        miList.stream().map((CLCitaMedica cd) -> {
            Object[] fila = new Object[11];
            fila[0] = cd.getNumeroIdentidad();
            fila[1] = cd.getNombrePaciente();
            fila[2] = cd.getIdCitaMedica();
            fila[3] = cd.getObservaciones();
            fila[4] = cd.getFecha();
            fila[5] = cd.getHoraInicio();
            fila[6] = cd.getHoraFinal();
            fila[7] = cd.getIdUsuario();
            fila[8] = cd.getNombreUsuario();
            fila[9] = cd.getNombreEmpleado();
            fila[10] = cd.getCargo();
            return fila;
        }).forEachOrdered(dtm::addRow);
    }
                
    private void limpiarTextField(){
        
        this.jTFIdCita.setText("");
        this.jTFObservaciones.setText("");
        //this.jDCFechaIngreso.setDateFormatString("");
        //this.jTFFecha.setText("");
        this.jTFHoraInicio.setText("");
        this.jTFHoraFinal.setText("");
        this.jTFIdentidad.setText("");
        this.jTFIdUsuario.setText("");
        
    }
    private void limpiarIdentidad(){
        this.jTFIdentidadN.setText("");
    }
    //validad textFiels
    private boolean validadTexFielO(){
        boolean o;
        o =!this.jTFObservaciones.getText().equals("");
        return o;
    }
    private boolean validadTexFielF(){
        boolean f;
        //f =!this.jTFFecha.getText().equals("");
        f =!this.jDCFechaIngreso.getDateFormatString().equals("");
        
        return f;
    }
    private boolean validadTexFielHi(){
        boolean hi;
        hi =!this.jTFHoraInicio.getText().equals("");
        
        return hi;
    }
    private boolean validadTexFielHf(){
        boolean hf;
        hf = !this.jTFHoraFinal.getText().equals("");
        return hf;
    }
    private void ingresarCita(){
        if(!validadTexFielO()) {
            JOptionPane.showMessageDialog(null, "Tiene que ingresar las observaciones","SistemaClinico",JOptionPane.INFORMATION_MESSAGE);
            this.jTFObservaciones.requestFocus();
        }else{
            if(!validadTexFielF()) {JOptionPane.showMessageDialog(null, "Tiene que ingresar la fecha de creacion","SistemaClinico",JOptionPane.INFORMATION_MESSAGE);
            
            }else{
                if(!validadTexFielHi()) {
                    JOptionPane.showMessageDialog(null, "Tiene que ingresar la Hora de inicio","SistemaClinico",JOptionPane.INFORMATION_MESSAGE);
                    this.jTFHoraInicio.requestFocus();
            }else{
                if(!validadTexFielHf()) {
                    JOptionPane.showMessageDialog(null, "Tiene que ingresar la Hora final de la cita","SistemaClinico",JOptionPane.INFORMATION_MESSAGE);
                    this.jTFHoraFinal.requestFocus();
                }else{
                    try{
                        CDCitaMedica cdc = new CDCitaMedica();
                        CLCitaMedica clc = new CLCitaMedica();
                        clc.setObservaciones(this.jTFObservaciones.getText().trim());
                        java.util.Date fecha = jDCFechaIngreso.getDate();
                        SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        clc.setFecha(oDateFormat.format(fecha));
                        //clc.setFecha(this.jTFFecha.getText().trim());
                        clc.setHoraInicio(this.jTFHoraInicio.getText().trim());
                        clc.setHoraFinal(this.jTFHoraFinal.getText().trim());
                        clc.setNumeroIdentidad(this.jTFIdentidad.getText().trim());
                        clc.setIdUsuario(Integer.parseInt(this.jTFIdUsuario.getText().trim()));
                        
                        cdc.insertarCitaMedica(clc);
                        JOptionPane.showMessageDialog(null, "Ingresado correctamente","SistemaClinico",JOptionPane.INFORMATION_MESSAGE);
                        
                    }catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al almacenar registro","SistemaClinico",JOptionPane.INFORMATION_MESSAGE);
                        this.jTFObservaciones.requestFocus();
                    }
                
                }
                }
            }
        
        }
    }
    public void guardar() throws SQLException{
        ingresarCita();
        poblarTablaCitas();
        limpiarTextField();
        encontrarCorrelativo();
    }
    //Metodo para actualizar
    private void actualizarCita(){
        if(!validadTexFielO()) {
            JOptionPane.showMessageDialog(null, "Tiene que Ingresar el cargo que desea actualizar","SistemaClinico",JOptionPane.INFORMATION_MESSAGE);
            this.jTFObservaciones.requestFocus();
        }else{
            try {
                CDCitaMedica cdc = new CDCitaMedica();
                CLCitaMedica clc = new CLCitaMedica();
                clc.setObservaciones(this.jTFObservaciones.getText().trim());
                //clc.setFecha(this.jDCFechaIngreso.getDateFormatString().trim());
                java.util.Date fecha = jDCFechaIngreso.getDate();
                SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                clc.setFecha(oDateFormat.format(fecha));
                //clc.setFecha(this.jTFFecha.getText().trim());
                clc.setHoraInicio(this.jTFHoraInicio.getText().trim());
                clc.setHoraFinal(this.jTFHoraFinal.getText().trim());
                clc.setNumeroIdentidad(this.jTFIdentidad.getText().trim());
                clc.setIdUsuario(Integer.parseInt(this.jTFIdUsuario.getText().trim()));
                cdc.actualizarCitaMedica(clc);
                JOptionPane.showMessageDialog(null, "Actualizado correctamente", "SistemaClinico", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al Actualizar Registro", "SistemaClinico", JOptionPane.INFORMATION_MESSAGE);
                this.jTFObservaciones.requestFocus();
            }
        }
    }
    //Metodo Actualizar
    private void actualizar() throws SQLException{
        actualizarCita();
        poblarTablaCitas();
        limpiarTextField();
        encontrarCorrelativo();
    }
    //Metodo para eliminar
    private void eliminarCita() {
        try {
            CDCitaMedica cdc = new CDCitaMedica();
            CLCitaMedica clc = new CLCitaMedica();
            clc.setIdCitaMedica(Integer.parseInt(this.jTFIdCita.getText().trim()));
            clc.setObservaciones(this.jTFObservaciones.getText().trim());
            clc.setFecha(this.jDCFechaIngreso.getDateFormatString().trim());
            //clc.setFecha(this.jTFFecha.getText().trim());
            clc.setHoraInicio(this.jTFHoraInicio.getText().trim());
            clc.setHoraFinal(this.jTFHoraFinal.getText().trim());
            clc.setNumeroIdentidad(this.jTFIdentidad.getText().trim());
            clc.setIdUsuario(Integer.parseInt(this.jTFIdUsuario.getText().trim()));
            cdc.eliminarCitaMedica(clc);
            JOptionPane.showMessageDialog(null, "Eliminado correctamente", "SistemaClinico", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar Registro", "SistemaClinico", JOptionPane.INFORMATION_MESSAGE);
            this.jTFObservaciones.requestFocus();
        }
    }
    //Metodo para seleccionar los datos de la fila y asi poder modificarlos
    private void seleccionarCita() { this.jTFIdentidad.setText(String.valueOf(this.jTblCitaMedica.getValueAt(this.jTblCitaMedica.getSelectedRow(), 0)));
        if(this.jTblCitaMedica.getSelectedRow() != -1){
            this.jTFIdentidad.setText(String.valueOf(this.jTblCitaMedica.getValueAt(this.jTblCitaMedica.getSelectedRow(), 0)));
            this.jTFIdCita.setText(String.valueOf(this.jTblCitaMedica.getValueAt(this.jTblCitaMedica.getSelectedRow(), 2)));
            this.jTFObservaciones.setText(String.valueOf(this.jTblCitaMedica.getValueAt(this.jTblCitaMedica.getSelectedRow(), 3)));
            Date oDateFormat;
            try {
                oDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse((String)this.jTblCitaMedica.getValueAt(this.jTblCitaMedica.getSelectedRow(), 4));
                this.jDCFechaIngreso.setDate(oDateFormat);
            } catch (ParseException ex) {
                Logger.getLogger(JFraHistoriaClinica.class.getName()).log(Level.SEVERE, null, ex);
            }
            //this.jTFFecha.setText(String.valueOf(this.jTblCitaMedica.getValueAt(this.jTblCitaMedica.getSelectedRow(), 4)));
            this.jTFHoraInicio.setText(String.valueOf(this.jTblCitaMedica.getValueAt(this.jTblCitaMedica.getSelectedRow(), 5)));
            this.jTFHoraFinal.setText(String.valueOf(this.jTblCitaMedica.getValueAt(this.jTblCitaMedica.getSelectedRow(), 6)));       
            this.jTFIdUsuario.setText(String.valueOf(this.jTblCitaMedica.getValueAt(this.jTblCitaMedica.getSelectedRow(), 7)));
        }
    }
    //eliminar
    private void eliminar() {
        int resp = JOptionPane.showConfirmDialog(null, "?Esta seguro que desea eliminar el cargo?", "SistemaClinico", 
                   JOptionPane.YES_NO_OPTION);
        if(resp == JOptionPane.YES_OPTION){
            try {
                eliminarCita();
                poblarTablaCitas();
                limpiarTextField();
                encontrarCorrelativo();
            }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar Registro", "SistemaClinico", JOptionPane.INFORMATION_MESSAGE);
            this.jTFObservaciones.requestFocus();
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
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        Regresar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTFObservaciones = new javax.swing.JTextField();
        jTFHoraInicio = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTFHoraFinal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jBtnIngresar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jBtnEditar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jTFIdCita = new javax.swing.JTextField();
        jBtnLimpiar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jTFIdUsuario = new javax.swing.JTextField();
        jTFIdentidad = new javax.swing.JTextField();
        jDCFechaIngreso = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTFIdentidadN = new javax.swing.JTextField();
        jBthMostrar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblCitaMedica = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        jPanel4.setBackground(new java.awt.Color(79, 198, 203));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel9.setText("X");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel9MousePressed(evt);
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("V I S O R  C I T A  M É D I C A");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(Regresar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 267, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(261, 261, 261)
                .addComponent(jLabel9))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addGap(0, 35, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(Regresar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(79, 198, 203));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingreso de una nueva cita medica", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel3.setText("Observaciones");

        jLabel4.setText("Fecha de Ingreso:");

        jLabel5.setText("Hora de Inicio:");

        jLabel6.setText("Hora Final de la cita:");

        jLabel7.setText("Numero de identidad ");

        jLabel8.setText("Usuario :");

        jBtnIngresar.setBackground(new java.awt.Color(79, 198, 203));
        jBtnIngresar.setFont(new java.awt.Font("DejaVu Math TeX Gyre", 1, 12)); // NOI18N
        jBtnIngresar.setText("Ingresar");
        jBtnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnIngresarActionPerformed(evt);
            }
        });

        jLabel11.setText("del paciente:");

        jBtnEditar.setBackground(new java.awt.Color(79, 198, 203));
        jBtnEditar.setFont(new java.awt.Font("DejaVu Math TeX Gyre", 1, 12)); // NOI18N
        jBtnEditar.setText("Editar");
        jBtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarActionPerformed(evt);
            }
        });

        jLabel13.setText("Id cita Medica");

        jTFIdCita.setEditable(false);

        jBtnLimpiar.setBackground(new java.awt.Color(79, 198, 203));
        jBtnLimpiar.setFont(new java.awt.Font("DejaVu Math TeX Gyre", 1, 12)); // NOI18N
        jBtnLimpiar.setText("Limpiar");
        jBtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLimpiarActionPerformed(evt);
            }
        });

        jBtnEliminar.setBackground(new java.awt.Color(79, 198, 203));
        jBtnEliminar.setFont(new java.awt.Font("DejaVu Math TeX Gyre", 1, 12)); // NOI18N
        jBtnEliminar.setText("Eliminar");
        jBtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarActionPerformed(evt);
            }
        });

        jDCFechaIngreso.setDateFormatString("yyy- MM-dd");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTFIdCita, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel4))
                            .addGap(10, 10, 10)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTFHoraInicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(30, 30, 30)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTFObservaciones, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                        .addComponent(jDCFechaIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jTFIdentidad, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(2, 2, 2)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel8))))
                            .addGap(18, 18, 18)
                            .addComponent(jTFHoraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jBtnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTFIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jBtnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBtnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTFIdCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(43, 43, 43)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTFObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDCFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jTFHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTFHoraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jTFIdentidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jTFIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(79, 198, 203));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mas Opciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel12.setText("Buscar por Numero de identidad ");

        jTFIdentidadN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFIdentidadNKeyReleased(evt);
            }
        });

        jBthMostrar.setBackground(new java.awt.Color(79, 198, 203));
        jBthMostrar.setFont(new java.awt.Font("DejaVu Math TeX Gyre", 1, 14)); // NOI18N
        jBthMostrar.setText("Mostrar todo");
        jBthMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBthMostrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12)
                        .addGap(23, 23, 23)
                        .addComponent(jTFIdentidadN, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jBthMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTFIdentidadN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jBthMostrar, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        jPanel6.setBackground(new java.awt.Color(79, 203, 146));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Citas Medicas Actuales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jTblCitaMedica.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero de identidad", "Nombre del paciente", "Id Cita Medica", "Observaciones", "Fecha", "Hora Inicio", "Hora Final", "Id Usuario", "Nombre Usuario", "Nombre del empleado", "Cargo de empleado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTblCitaMedica.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTblCitaMedica.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblCitaMedicaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblCitaMedica);
        if (jTblCitaMedica.getColumnModel().getColumnCount() > 0) {
            jTblCitaMedica.getColumnModel().getColumn(0).setResizable(false);
            jTblCitaMedica.getColumnModel().getColumn(0).setPreferredWidth(200);
            jTblCitaMedica.getColumnModel().getColumn(1).setResizable(false);
            jTblCitaMedica.getColumnModel().getColumn(1).setPreferredWidth(200);
            jTblCitaMedica.getColumnModel().getColumn(2).setResizable(false);
            jTblCitaMedica.getColumnModel().getColumn(2).setPreferredWidth(200);
            jTblCitaMedica.getColumnModel().getColumn(3).setResizable(false);
            jTblCitaMedica.getColumnModel().getColumn(3).setPreferredWidth(200);
            jTblCitaMedica.getColumnModel().getColumn(4).setResizable(false);
            jTblCitaMedica.getColumnModel().getColumn(4).setPreferredWidth(200);
            jTblCitaMedica.getColumnModel().getColumn(5).setResizable(false);
            jTblCitaMedica.getColumnModel().getColumn(5).setPreferredWidth(200);
            jTblCitaMedica.getColumnModel().getColumn(6).setResizable(false);
            jTblCitaMedica.getColumnModel().getColumn(6).setPreferredWidth(200);
            jTblCitaMedica.getColumnModel().getColumn(7).setResizable(false);
            jTblCitaMedica.getColumnModel().getColumn(7).setPreferredWidth(200);
            jTblCitaMedica.getColumnModel().getColumn(8).setResizable(false);
            jTblCitaMedica.getColumnModel().getColumn(9).setResizable(false);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MousePressed
        this.dispose();
    }//GEN-LAST:event_jLabel9MousePressed

    private void jBtnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnIngresarActionPerformed
        try {
            guardar();
        } catch (SQLException ex) {
            Logger.getLogger(JFraCitaMedica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnIngresarActionPerformed

    private void jBthMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBthMostrarActionPerformed
        try {
            poblarTablaCitas();
        } catch (SQLException ex) {
            Logger.getLogger(JFraCargo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBthMostrarActionPerformed

    private void RegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RegresarMouseClicked
        try {
            JFraPrincipal principal = new JFraPrincipal();
            principal.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(JFraHistoriaClinica.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        this.dispose();
    }//GEN-LAST:event_RegresarMouseClicked

    private void jBtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_jBtnEliminarActionPerformed

    private void jTblCitaMedicaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblCitaMedicaMouseClicked
        seleccionarCita();
    }//GEN-LAST:event_jTblCitaMedicaMouseClicked

    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        limpiarTextField();
        try {
            encontrarCorrelativo();
        } catch (SQLException ex) {
            Logger.getLogger(JFraCitaMedica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    private void jTFIdentidadNKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFIdentidadNKeyReleased
        if (this.jTFIdentidadN.getText().equals("")) {
            try {
                poblarTablaCitas();
            } catch (SQLException ex) {
                Logger.getLogger(JFraConsultaMedica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
            busquedaCitaMedica(this.jTFIdentidadN.getText());
        } catch (SQLException ex) {
            Logger.getLogger(JFraConsultaMedica.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }//GEN-LAST:event_jTFIdentidadNKeyReleased

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
        try {
            actualizar();
        } catch (SQLException ex) {
            Logger.getLogger(JFraCitaMedica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnEditarActionPerformed

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
            java.util.logging.Logger.getLogger(JFraCitaMedica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFraCitaMedica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFraCitaMedica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFraCitaMedica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JFraCitaMedica().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(JFraCitaMedica.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Regresar;
    private javax.swing.JButton jBthMostrar;
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnIngresar;
    private javax.swing.JButton jBtnLimpiar;
    private com.toedter.calendar.JDateChooser jDCFechaIngreso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFHoraFinal;
    private javax.swing.JTextField jTFHoraInicio;
    private javax.swing.JTextField jTFIdCita;
    private javax.swing.JTextField jTFIdUsuario;
    private javax.swing.JTextField jTFIdentidad;
    private javax.swing.JTextField jTFIdentidadN;
    private javax.swing.JTextField jTFObservaciones;
    private javax.swing.JTable jTblCitaMedica;
    // End of variables declaration//GEN-END:variables
}
