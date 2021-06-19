/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.Conexion;
import java.sql.ResultSet;

/**
 *
 * @author Omar AGA
 */
public class clsPlato {
    Conexion objConectar = new Conexion();
    String strSQL;
    ResultSet rs=null;
    
    
    public ResultSet listarPlatosActivosPorCarta(int idCarta) throws Exception{
        strSQL="select * from plato where fk_carta='"+idCarta+"' and estado=TRUE";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar platos...");
        }
    
    }
    
    public ResultSet busquedaFiltradaPlatosActivosPorCarta(int idCarta, String busqueda) throws Exception{
        String filtro="%"+busqueda+"%";
        strSQL="select * from plato where fk_carta='"+idCarta+"' and UPPER(nombre) like UPPER('"+filtro+"') and estado=TRUE ";
        
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar platos...");
        }
    }
   
      public float ObtenerCostoPlato(int idplato) throws Exception{
        strSQL="select costo from plato where id_plato="+idplato+"";
        float costo=0;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                costo=rs.getFloat(1);
            }
            
        } catch (Exception e) {
            throw new Exception("Error al obtener costo de plato...");
        }
        return costo;
    }
}
