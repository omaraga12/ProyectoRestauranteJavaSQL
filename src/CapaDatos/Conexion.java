/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDatos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Omar AGA
 */
public class Conexion {
    private String driver,url,user,password;
    private Connection con;
    private Statement sent=null;
    private CallableStatement cs;

    //Constructor
    public Conexion() {
        this.driver = "org.postgresql.Driver";
        //String servidor="database-2.cfhzxl8ixjw6.us-west-1.rds.amazonaws.com";
        String servidor="localhost";
        this.url = "jdbc:postgresql://"+servidor+":5432/restaurante";
        //this.user = "masteruser";
        this.user = "postgres";
        //this.password = "abc123456789";
        this.password = "fbi73416947";
        this.con = null;
    }
    
    //Conectar a BD
    public void conectarBD() throws Exception{
        try {
            Class.forName(driver);
            con=DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new Exception ("Error al conectar la BD...");
        }
            
    }
    
    //Consultar estado de la conexión
    public boolean getEstado() throws SQLException {
        return this.con.isClosed();
    }
    
    //07 Abril 2021
    
    //Desconectar de la BD
    public void desconectarBD() throws Exception{
        try {
            con.close();
        } catch (SQLException e) {
            throw new Exception("Error al desconectar de la BD.");
        }
    }
    
    //Ejecutar consultas a la BD:  SELECT - Función
    public ResultSet consultarBD(String strSQL) throws Exception{
        ResultSet rs=null;
        try {
            conectarBD();
            sent = con.createStatement();
            rs=sent.executeQuery(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception ("Error al ejecutar consulta...");
        } finally{
            if(con!=null){
                desconectarBD();   
            }   
        }
    }
    
    //Ejecutar inserta/elimina/modifica base de datos: INSERT, UPDATE y DELETE - Procedimientos
    public void ejecutarBD (String strSQL) throws Exception{
        try {
            conectarBD();
            sent = con.createStatement();
            sent.executeUpdate(strSQL);
        } catch (Exception e) {
            throw new Exception ("Error al ejecutar consulta...");
        } finally{
            if(con!=null){
                desconectarBD();
            }
        }
    }
    
    public void ejecutarBDTransacciones(String strSQL1, String strSQL2, String strSQL3,String strSQL4) throws Exception{
        try {
            conectarBD();
            con.setAutoCommit(false);
            sent=con.createStatement();
            sent.executeUpdate(strSQL1);
            sent.executeUpdate(strSQL2);
            sent.executeUpdate(strSQL3);
            sent.executeUpdate(strSQL4);
            con.commit();
            con.setAutoCommit(true);
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al ejecutar consultas...");
        }finally{
            if(con!=null){
                desconectarBD();
            }
        }
    }
    
    //Ejecuta un arreglo de sentencias (cadenas)
    public void ejecutarBDTransacciones(ArrayList arregloConsultas) throws Exception{
        try {
            conectarBD();
            con.setAutoCommit(false);
            sent=con.createStatement();
            for(Object consulta:arregloConsultas){
                sent.executeUpdate((String)consulta);
            }
            con.commit();
            con.setAutoCommit(true);
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al ejecutar consultas...");
        }finally{
            if(con!=null){
                desconectarBD();
            }
        }
    }
    
    public ResultSet consultarBD_PA(String strSQL) throws Exception{
        ResultSet rs=null;
        try {
            conectarBD();
            cs = con.prepareCall(strSQL);
            rs = cs.executeQuery();
            //cs.setInt(1,55);
            //cs.registerOutParameter(1, java.sql.Types.INTEGER);
            //Integer i= cs.getInt(1);
            return rs;
        } catch (Exception e) {
            throw new Exception ("Error al ejecutar consulta con PA...");
        } finally{
            if(con!=null){
                desconectarBD();   
                cs.close();
            }   
        }
    }

   public Connection getCon() {
        return con;
    }
}
