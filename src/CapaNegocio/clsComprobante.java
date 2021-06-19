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
public class clsComprobante {
    Conexion objConectar = new Conexion();
    String strSQL;
    ResultSet rs=null;
    
    
    public ResultSet generarNumeroComprobante(String tipo) throws Exception{
        strSQL="select * from generarNumeroComprobante('"+tipo+"')";
        try {
            rs=objConectar.consultarBD_PA(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al generar numero de comprobante..."+e);
        }
    
    }
    public ResultSet obtenerDatosParaImpresion(int idComanda) throws Exception{
        strSQL="select case tipo when 'B' then 'BOLETA' else 'FACTURA' end as tipo, serie||'-'||correlativo as serie, Cliente.nombre_completo, Cliente.numero_documento,\n" +
                "total_gravadas, total_igv, importe_total from comprobante inner join comanda on comanda.id_comanda=comprobante.fk_comanda\n" +
                "inner join cliente on cliente.id_cliente=comanda.fk_cliente where fk_comanda="+idComanda+"";
        try {
            rs=objConectar.consultarBD_PA(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al generar numero de comprobante..."+e);
        }
        
    }
 
    
}
