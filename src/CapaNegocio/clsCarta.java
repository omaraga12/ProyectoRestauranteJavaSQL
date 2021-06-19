/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.Conexion;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omar AGA
 */
public class clsCarta {
    Conexion objConectar = new Conexion();
    String strSQL;
    ResultSet rs=null;
    
    
    public ResultSet listarCartasActivas() throws Exception{
        strSQL="select * from carta where estado=TRUE";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar cartas...");
        }
    
    }
}
