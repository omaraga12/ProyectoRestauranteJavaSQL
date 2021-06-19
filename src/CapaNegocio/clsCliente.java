/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.Conexion;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Omar AGA
 */
public class clsCliente {
    Conexion objConectar = new Conexion();
    String strSQL;
    ResultSet rs=null;
    
    
    public ResultSet listarClientes() throws Exception{
        strSQL="select * from cliente order by nombre_completo asc";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar clientes..."+e);
        }
    
    }
    
    public ResultSet busquedaFiltradaClientesPorNombre( String busqueda) throws Exception{
        String filtro="%"+busqueda+"%";
        strSQL="select * from cliente where  UPPER(nombre_completo) like UPPER('"+filtro+"')";
        
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar cliente...");
        }
    
    }
    
     public ResultSet busquedaFiltradaClientesPorDocumento( String busqueda) throws Exception{
        String filtro="%"+busqueda+"%";
        strSQL="select * from cliente where  UPPER(numero_documento) like UPPER('"+filtro+"')";
        
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar cliente...");
        }
    }
    public void registrarCliente(String documento, String nombre, String direccion, String tipo) throws Exception{
      strSQL="insert into cliente (numero_documento, nombre_completo, direccion, estado, tipo_documento) values('"+documento+"','"+nombre+"','"+direccion+"',true,'"+tipo+"')";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar cliente...");
        }
    }
    public ResultSet validadClienteDuplicado(String documento) throws Exception{
        strSQL="select * from cliente where numero_documento='"+documento+"'";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar cliente..."+e);
        }
    
    }
    
    public void modificarCliente(int id,String documento, String nombre, String direccion, String tipo) throws Exception{
      strSQL="update cliente set numero_documento='"+documento+"', nombre_completo='"+nombre+"', direccion='"+direccion+"', tipo_documento='"+tipo+"' where id_cliente="+id+"";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al modificar cliente...");
        }
    }
     public void actualizarEstadoCliente(int id, String estado) throws Exception{
      strSQL="update cliente set  estado="+estado+" where id_cliente="+id+"";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al actualizar estado de cliente...");
        }
    }
     public ResultSet buscarCliente(String documento) throws Exception{
        strSQL="select * from cliente where numero_documento='"+documento+"'";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar cliente..."+e);
        }
    
    }
     
   public ResultSet obtenerIDCLiente(String documento) throws Exception{
        strSQL="select id_cliente from cliente where numero_documento='"+documento+"'";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al obtener id de cliente..."+e);
        }
    
    }
}
