/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Omar AGA
 */
public class clsComanda {
    Conexion objConectar = new Conexion();
    String strSQL;

    ResultSet rs=null;
  
          


    public int guardarComanda(int idCliente) throws Exception{
       strSQL="insert into comanda (fecha, hora, estado, fk_cliente) values (current_date,current_timestamp,'g',"+idCliente+") returning id_comanda";
       ResultSet data=null;
       int idComandaGenerada = 0;
        try {
            data=objConectar.consultarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al guardar comanda");
        }
        while(data.next()){
            idComandaGenerada=data.getInt(1);
        }
      return idComandaGenerada;
    }
    
        public void transacionComanda(ArrayList consultas ) throws Exception{
        try {
            objConectar.ejecutarBDTransacciones(consultas);
        } catch (Exception e) {
            System.out.println("Error al ejecutar bloque de instrucciones...");
        }
       
    }
    

}
