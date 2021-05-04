/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curlp.capadatos;

import com.curlp.capalogica.CLHistoriaClinica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Admi
 */
public class CDHistoriaClinica {
    //Declarar variables de coneccion de consultas
    private final Connection cn;
    PreparedStatement ps;
    ResultSet rs;
     
    public CDHistoriaClinica() throws SQLException {
        this.cn = Conexion.conectar();
    }
    
    //Metodo para insertar una Historia Clinica
    public void insertarHistoriaClinica (CLHistoriaClinica cl) throws SQLException {
         String sql = "{CALL sp_insertarHistoriaClinica(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try{
            ps = cn.prepareCall(sql);
            ps.setString(1, cl.getNumeroIdentidadPaciente());
            ps.setString(2, cl.getFechaCreacion());
            ps.setString(3, cl.getCardiobasculares());
            ps.setString(4, cl.getPulmonares());
            ps.setString(5, cl.getDigestivo());
            ps.setString(6, cl.getDiabetes());
            ps.setString(7, cl.getRenales());
            ps.setString(8, cl.getQuirurgicos());
            ps.setString(9, cl.getAlergicos());
            ps.setString(10, cl.getTransfusiones());   
            ps.setString(11, cl.getMedicamentos());  
            ps.setString(12, cl.getObservaciones());  
            ps.setInt(13, cl.getIdUsuario());  
            ps.execute();
            
           
           
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al guardar los datos  "+ e.getMessage());
        
        }
    }
    
    //Metodo para actualizar Historia Clinica
    public void actualizarHistoriaMedica(CLHistoriaClinica cl) {
      
       String sql = "{CALL sp_actualizarHistoriaClinica(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        
        try{
            ps = cn.prepareCall(sql);
            ps.setString(1, cl.getNumeroIdentidadPaciente());
            ps.setString(2, cl.getFechaCreacion());
            ps.setString(3, cl.getCardiobasculares());
            ps.setString(4, cl.getPulmonares());
            ps.setString(5, cl.getDigestivo());
            ps.setString(6, cl.getDiabetes());
            ps.setString(7, cl.getRenales());
            ps.setString(8, cl.getQuirurgicos());
            ps.setString(9, cl.getAlergicos());
            ps.setString(10, cl.getTransfusiones());   
            ps.setString(11, cl.getMedicamentos());  
            ps.setString(12, cl.getObservaciones());  
            ps.setInt(13, cl.getIdUsuario());  
            ps.execute();
           
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "error: "+ e.getMessage());
        
        }
    
    }
    
    //Metodo para eliminar Historia Clinica
    
    public void eliminarHistoria(CLHistoriaClinica cl) throws  SQLException{
        String sql = "{CALL sp_eliminarHistoriaClinica(?)}";
        
        try {
            ps = cn.prepareCall(sql);
            ps.setString(1, cl.getNumeroIdentidadPaciente());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }
    
    //Metodo para poblar la tabla Historia Clinica
    public List<CLHistoriaClinica> obtenerListaHistoriaClinica() throws SQLException{
        String sql = "{CALL sp_mostrarHistoriaClinica()}";
        
        List<CLHistoriaClinica> miLista = null;
        try{
            Statement st = cn.createStatement();
            rs = st.executeQuery(sql);
            miLista = new ArrayList<>();
            while(rs.next()) {
                CLHistoriaClinica cl = new CLHistoriaClinica();
                
                cl.setNumeroIdentidadPaciente(rs.getString("numeroIdentidad"));
                cl.setFechaCreacion(rs.getString("historiaClinicaFechaCreacion"));
                cl.setCardiobasculares(rs.getString("historiaClinicaCardiobasculares"));
                cl.setPulmonares(rs.getString("historiaClinicaPulmonares"));
                cl.setDigestivo(rs.getString("historiaClinicaDigestivo"));
                cl.setDiabetes(rs.getString("historiaClinicaDiabetes"));
                cl.setRenales(rs.getString("historiaClinicaRenales"));
                cl.setQuirurgicos(rs.getString("historiaClinicaQuirurgicos"));
                cl.setAlergicos(rs.getString("historiaClinicaAlergicos"));
                cl.setTransfusiones(rs.getString("historiaClinicaTransfusiones"));
                cl.setMedicamentos(rs.getString("historiaClinicaMedicamentos"));
                cl.setObservaciones(rs.getString("historiaClinicaObservaciones"));
                cl.setIdUsuario(rs.getInt("idUsuario"));
                miLista.add(cl);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "error: "+ e.getMessage());
        
        }
        return miLista;   
    }
    
    //Metodo para poblar la tabla Historia Clinica
    public List<CLHistoriaClinica> mostrarHistoriaClinicaCondicion(String numeroIdentidad) throws SQLException{
        String sql = "{CALL sp_mostrarHistoriaClinicaCondicion(?)}";
        
        if("".equals(sql)){
            JOptionPane.showMessageDialog(null, "Viene vacio", "SIMEC", JOptionPane.INFORMATION_MESSAGE);
        }
        List<CLHistoriaClinica> miLista = null;
        try{
            ps = cn.prepareStatement(sql);
            ps.setString(1, numeroIdentidad);
            rs = ps.executeQuery();
            miLista = new ArrayList<>();
            while(rs.next()) {
                CLHistoriaClinica cl = new CLHistoriaClinica();
                cl.setNumeroIdentidadPaciente(rs.getString("numeroIdentidad"));
                cl.setFechaCreacion(rs.getString("historiaClinicaFechaCreacion"));
                cl.setCardiobasculares(rs.getString("historiaClinicaCardiobasculares"));
                cl.setPulmonares(rs.getString("historiaClinicaPulmonares"));
                cl.setDigestivo(rs.getString("historiaClinicaDigestivo"));
                cl.setDiabetes(rs.getString("historiaClinicaDiabetes"));
                cl.setRenales(rs.getString("historiaClinicaRenales"));
                cl.setQuirurgicos(rs.getString("historiaClinicaQuirurgicos"));
                cl.setAlergicos(rs.getString("historiaClinicaAlergicos"));
                cl.setTransfusiones(rs.getString("historiaClinicaTransfusiones"));
                cl.setMedicamentos(rs.getString("historiaClinicaMedicamentos"));
                cl.setObservaciones(rs.getString("historiaClinicaObservaciones"));
                cl.setIdUsuario(rs.getInt("idUsuario"));
                miLista.add(cl);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "error: "+ e.getMessage());
        
        }
        return miLista;   
    }
       
}
