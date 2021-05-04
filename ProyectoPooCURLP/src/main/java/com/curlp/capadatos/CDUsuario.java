/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curlp.capadatos;

import com.curlp.capalogica.CLUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CDUsuario {
    private final Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Statement st;

    public CDUsuario() throws SQLException{
       this.cn = Conexion.conectar();
    }

     //Metodo para insertar empleado en la tabla
    public void insertarUsuario(CLUsuario cl) throws SQLException{
        String sql = "{CALL sp_insertarUsuario(?,?,?,?,?,?)}";
        
         try{
            ps = cn.prepareCall(sql);
            ps.setString(1, cl.getNombreUsuario());
            ps.setString(2, cl.getPassword());
            ps.setString(3, cl.getFechaCreacion());
            ps.setString(4, cl.getFechaBaja());
            ps.setInt(5, cl.getIdEstado());
            ps.setInt(6, cl.getIdEmpleado());
            ps.execute();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "erro al insertar usuario "+ e.getMessage());
        
        }      
    }
    
    //Metodo para actualizar empleado en la tabla
     public void actualizarUsuario(CLUsuario cl) {
        String sql = "{CALL sp_actualizarUsuario(?,?,?,?,?,?,?)}";
        try{
            ps = cn.prepareCall(sql);
            ps.setInt(1, cl.getIdUsuario());
            ps.setString(2, cl.getNombreUsuario());
            ps.setString(3, cl.getPassword());
            ps.setString(4, cl.getFechaCreacion());
            ps.setString(5, cl.getFechaBaja());
            ps.setInt(6, cl.getIdEstado());
            ps.setInt(7, cl.getIdEmpleado());
            ps.execute();
                              
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "error al actualizar usuario: "+ e.getMessage());
        
        }
    
    }
    
    // Metodo para eliminar un usuario
    public void eliminarUsuario(CLUsuario cl) throws SQLException{
        String sql = "{CALL sp_eliminarUsuario(?)}";
        
        try{
            ps = cn.prepareCall(sql);
            ps.setInt(1, cl.getIdUsuario());
            ps.execute();
           
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "error: "+ e.getMessage());
        
        }
    }
    
    
    // Metodo para autoincrementar empleado ID
    public int autoIncrementarUsuario() throws SQLException{
        int idUsuario = 0;
        String sql = "{CALL sp_autoIncrementarUsuarioId()}";
        try{
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            rs.next();
            idUsuario = rs.getInt("idUsuario");
            if(idUsuario == 0) {
                idUsuario = 1;
            }
                        
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "error: "+ e.getMessage());
        
        }
        return idUsuario;
    }
    //Metodo para poblar la tabla empleados
    public List<CLUsuario> obtenerListaUsuarios() throws SQLException{
        String sql = "{CALL sp_mostrarUsuarios()}";
        List<CLUsuario> miLista = null;
        try{
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            miLista = new ArrayList<>();
            while(rs.next()) {
                CLUsuario cl = new CLUsuario();
                cl.setIdUsuario(rs.getInt("idUsuario"));
                cl.setNombreUsuario(rs.getString("usuarioNombreUsuario"));
                cl.setPassword(rs.getString("usuarioPassword"));
                cl.setFechaCreacion(rs.getString("usuarioFechaCreacion"));
                cl.setFechaBaja(rs.getString("usuarioFechaBaja"));
                cl.setIdEstado(rs.getInt("idEstado"));
                cl.setIdEmpleado(rs.getInt("idEmpleado"));
                miLista.add(cl);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "error: "+ e.getMessage());
        
        }
        return miLista;   
    }
  
}
