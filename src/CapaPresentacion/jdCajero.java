/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacion;

import CapaNegocio.clsComprobante;
import CapaNegocio.clsCarta;
import CapaNegocio.clsCliente;
import CapaNegocio.clsComanda;
import CapaNegocio.clsPlato;
import capaLogica.Validaciones;
import java.awt.Image;
import java.awt.Label;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Omar AGA
 */
public class jdCajero extends javax.swing.JDialog {
    clsCarta objCarta=new clsCarta();
    clsPlato objPlato= new clsPlato();
    clsCliente objCliente=new clsCliente();
    clsComprobante objComprobante=new clsComprobante();
    clsComanda objComanda= new clsComanda();
    DefaultTableModel modeloTabVenta = new DefaultTableModel();
    
    

    /**
     * Creates new form jdCajero
     */
    public jdCajero(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        initComponents();
        listarCartas();
        listarPlatos();
        Validaciones.validarSoloNumeros(txtCantidad);
        Validaciones.validarSoloNumeros(txtDni);
        generarNumeroComprobante();
        
        modeloTabVenta= new DefaultTableModel();
        modeloTabVenta.addColumn("ID");
        modeloTabVenta.addColumn("Nombre");
        modeloTabVenta.addColumn("Precio");
        modeloTabVenta.addColumn("Cántidad");
        modeloTabVenta.addColumn("Total");
        tblVenta.setModel(modeloTabVenta);
        TableColumn  columna = tblVenta.getColumn("ID");
        columna.setMinWidth(20);
        columna.setMaxWidth(40);
        TableColumn  columna3 = tblVenta.getColumn("Cántidad");
        columna3.setMinWidth(60);
        columna3.setMaxWidth(80);
        TableColumn  columna2 = tblVenta.getColumn("Precio");
        columna2.setMinWidth(60);
        columna2.setMaxWidth(80);
        TableColumn  columna4 = tblVenta.getColumn("Total");
        columna4.setMinWidth(60);
        columna4.setMaxWidth(80);
        
    }
    
    public void calcularTotales(){
        DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
        separadoresPersonalizados.setDecimalSeparator('.');
        DecimalFormat formato1 = new DecimalFormat("#.00", separadoresPersonalizados);
        float total=0;
        int filas=tblVenta.getRowCount();
       
                try{
                    for(int i=0; i<filas; i++){
                        String importe=obtenerPrecio(tblVenta.getValueAt(i, 4).toString());
                        total=total+Float.parseFloat(importe);
                    }
                    lblTotal.setText(formato1.format(total));
                }catch(Exception e){
                    System.err.println("error"+e);
                }
           
            
          float totalSinigv= (float) (total/1.18);
          lblTG.setText(formato1.format((totalSinigv)));
          float igv=total-totalSinigv;
          lbligv.setText(formato1.format(igv));
    }
    
    public void listarCartas(){
        ResultSet data;
        try {
            data=objCarta.listarCartasActivas();
            
            while (data.next()) {
                cboCartas.addItem(data.getInt(1)+"-"+data.getString(2));
                
            }
        } catch (Exception ex) {
            System.out.println("ERROR"+ex);
        }
        
    
    }
    
    
    public String obtenerIDCarta(){
        String cadena=cboCartas.getSelectedItem().toString();
        String data[]=cadena.split("-");
        String id=data[0];
        return id;
    }
    public void listarPlatos(){
        tblPlatos.setDefaultRenderer(Object.class, new ImgTabla());
        DefaultTableModel modeloTab = new DefaultTableModel();
        modeloTab.addColumn("ID");
        modeloTab.addColumn("Nombre");
        modeloTab.addColumn("Precio");
        modeloTab.addColumn("Imagen");
   
        tblPlatos.setModel(modeloTab);
        ResultSet data = null;
        
        
        TableColumn  columna = tblPlatos.getColumn("ID");
        columna.setMinWidth(20);
        columna.setMaxWidth(40);
        TableColumn  columna2 = tblPlatos.getColumn("Precio");
        columna2.setMinWidth(40);
        columna2.setMaxWidth(60);
        
        int idCarta=Integer.parseInt(obtenerIDCarta());
        try {
               data=objPlato.listarPlatosActivosPorCarta(idCarta);
               while(data.next()){
                   String directorio=data.getString(3);
                   System.err.println(directorio);
                   ImageIcon icon = new ImageIcon(getClass().getResource(directorio));
                   Image conversion= icon.getImage();
                   Image tamaño=conversion.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                   ImageIcon imagenFinal=new ImageIcon(tamaño);
                   
                   modeloTab.addRow(new Object[]{data.getInt(1),data.getString(2),"S/ "+data.getFloat(6), new JLabel(imagenFinal)});
                   tblPlatos.setRowHeight(100);
                   
               }
        } catch (Exception e) {
            System.err.println("error"+ e);
        }
    }
    
  public void busquedaFiltradaPlatos(){
        tblPlatos.setDefaultRenderer(Object.class, new ImgTabla());
        DefaultTableModel modeloTab = new DefaultTableModel();
        modeloTab.addColumn("ID");
        modeloTab.addColumn("Nombre");
        modeloTab.addColumn("Precio");
        modeloTab.addColumn("Imagen");
   
        tblPlatos.setModel(modeloTab);
        ResultSet data = null;
        
        
        TableColumn  columna = tblPlatos.getColumn("ID");
        columna.setMinWidth(20);
        columna.setMaxWidth(40);
        TableColumn  columna2 = tblPlatos.getColumn("Precio");
        columna2.setMinWidth(40);
        columna2.setMaxWidth(60);
        
        int idCarta=Integer.parseInt(obtenerIDCarta());
        try {
               data=objPlato.busquedaFiltradaPlatosActivosPorCarta(idCarta, txtBusqueda.getText());
               while(data.next()){
                   String directorio=data.getString(3);
                   System.err.println(directorio);
                   ImageIcon icon = new ImageIcon(getClass().getResource(directorio));
                   Image conversion= icon.getImage();
                   Image tamaño=conversion.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                   ImageIcon imagenFinal=new ImageIcon(tamaño);
                   
                   modeloTab.addRow(new Object[]{data.getInt(1),data.getString(2),"S/ "+data.getFloat(6), new JLabel(imagenFinal)});
                   tblPlatos.setRowHeight(100);
                   
               }
        } catch (Exception e) {
            System.err.println("error"+ e);
        }
    }
  
    public String obtenerPrecio(String precioCompleto){
       String precio=precioCompleto.substring(2);
        
        return precio;
    
    }
    
 public int buscarCliente(String documento){
        
        ResultSet data = null;
        int rpt=0;
       
        try {
               data=objCliente.buscarCliente(documento);
               while(data.next()){
                   lblDireccion.setText(data.getString(5));
                   lblNombreCliente.setText(data.getString(4));
                   rpt=1;
               }
        } catch (Exception e) {
            System.err.println("error"+ e);
        }
        return rpt;
    }
 public int obtenerIDCliente(String documento){
        
        ResultSet data = null;
        int id=0;
       
        try {
               data=objCliente.obtenerIDCLiente(documento);
               while(data.next()){
                  id= data.getInt(1);
               }
        } catch (Exception e) {
            System.err.println("error"+ e);
        }
        System.err.println("ID de cliente con dni "+documento+"es "+id);
        return id;
    } 
 
  public void obtenerDatosImpresion(int idComanda){
        
        ResultSet data = null;
       
       
        try {
               data=objComprobante.obtenerDatosParaImpresion(idComanda);
               while(data.next()){
                  CapaPresentacion.reportes.jdComprobante.tipo=data.getString(1);
                  CapaPresentacion.reportes.jdComprobante.codigo=data.getString(2);
                  CapaPresentacion.reportes.jdComprobante.cliente=data.getString(3);
                  CapaPresentacion.reportes.jdComprobante.documento=data.getString(4);
                  CapaPresentacion.reportes.jdComprobante.TG=data.getString(5);
                  CapaPresentacion.reportes.jdComprobante.igv=data.getString(6);
                  CapaPresentacion.reportes.jdComprobante.total=data.getString(7);
               }
        } catch (Exception e) {
            System.err.println("error"+ e);
        }
        
    } 
   public String generarNumeroComprobante() throws SQLException {
        ResultSet data = null;
        String tipo=null;
        String codigo=null;
        if(cbotipoComprobante.getSelectedItem().toString().equalsIgnoreCase("Boleta")){
            tipo="B";
        }else{
            tipo="F";
        }
        try {
               
               data=objComprobante.generarNumeroComprobante(tipo);
              
               while(data.next()){
                   lblCodigoComprobante.setText((data.getString(1)));
                   codigo=data.getString(1);
               }
              
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        } finally{
            data.close();
        }
        return codigo;
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cboCartas = new javax.swing.JComboBox<>();
        txtBusqueda = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPlatos = new javax.swing.JTable();
        btnCantidad = new javax.swing.JButton();
        txtCantidad = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        cbotipoComprobante = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        lblCodigoComprobante = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVenta = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        lblTG = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        lbligv = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblNombreCliente = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(14, 40, 68));

        jLabel1.setBackground(new java.awt.Color(14, 40, 68));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/cajero.png"))); // NOI18N
        jLabel1.setText("GESTION DE CAJA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(377, 377, 377))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 208, 49)));
        jPanel4.setPreferredSize(new java.awt.Dimension(537, 100));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(14, 40, 68));
        jLabel7.setText("Carta:");

        cboCartas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cboCartas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboCartasItemStateChanged(evt);
            }
        });

        txtBusqueda.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(14, 40, 68));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/buscar.png"))); // NOI18N
        jLabel8.setText("Nombre del plato:");

        tblPlatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio", "Imagen"
            }
        ));
        jScrollPane1.setViewportView(tblPlatos);

        btnCantidad.setBackground(new java.awt.Color(14, 40, 68));
        btnCantidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCantidad.setForeground(new java.awt.Color(255, 255, 255));
        btnCantidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/mas.png"))); // NOI18N
        btnCantidad.setText("Agregar");
        btnCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCantidadActionPerformed(evt);
            }
        });

        txtCantidad.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(14, 40, 68));
        jLabel9.setText("Cántidad:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboCartas, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCantidad)
                        .addGap(148, 148, 148))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cboCartas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCantidad))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(234, 123, 12)));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(14, 40, 68));
        jLabel11.setText("Tipo comprobante:");

        cbotipoComprobante.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbotipoComprobante.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Boleta", "Factura" }));
        cbotipoComprobante.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbotipoComprobanteItemStateChanged(evt);
            }
        });
        cbotipoComprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbotipoComprobanteMouseClicked(evt);
            }
        });

        jPanel10.setBackground(new java.awt.Color(0, 153, 102));

        lblCodigoComprobante.setBackground(new java.awt.Color(102, 255, 0));
        lblCodigoComprobante.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCodigoComprobante.setForeground(new java.awt.Color(255, 255, 255));
        lblCodigoComprobante.setText("_");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCodigoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblCodigoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tblVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio", "Cántidad", "Importe"
            }
        ));
        jScrollPane2.setViewportView(tblVenta);

        jPanel7.setBackground(new java.awt.Color(14, 40, 68));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Total Gravadas");

        jPanel11.setBackground(new java.awt.Color(0, 153, 102));

        lblTG.setBackground(new java.awt.Color(102, 255, 0));
        lblTG.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTG.setForeground(new java.awt.Color(255, 255, 255));
        lblTG.setText("_");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTG, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblTG, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Total IGV(18%)");

        jPanel12.setBackground(new java.awt.Color(0, 153, 102));

        lbligv.setBackground(new java.awt.Color(102, 255, 0));
        lbligv.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbligv.setForeground(new java.awt.Color(255, 255, 255));
        lbligv.setText("_");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbligv, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbligv, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Importe Total");

        jPanel13.setBackground(new java.awt.Color(0, 153, 102));

        lblTotal.setBackground(new java.awt.Color(102, 255, 0));
        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTotal.setText("_");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel13))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(14, 40, 68));
        jLabel2.setText("DNI/RUC:");

        txtDni.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        jButton1.setBackground(new java.awt.Color(14, 40, 68));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/icoBuscR.png"))); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(14, 40, 68));
        jLabel4.setText("Cliente:");

        lblNombreCliente.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblNombreCliente.setText("_");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(14, 40, 68));
        jLabel5.setText("Dirección:");

        lblDireccion.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblDireccion.setText("_");

        jButton3.setBackground(new java.awt.Color(14, 40, 68));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/borrar.png"))); // NOI18N
        jButton3.setText("Quitar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(14, 40, 68));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Historial");

        jButton5.setBackground(new java.awt.Color(14, 40, 68));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/agregar.png"))); // NOI18N
        jButton5.setText("Guardar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(14, 40, 68));
        jButton6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/folder.png"))); // NOI18N
        jButton6.setText("Historial");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNombreCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jButton3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                                        .addComponent(jButton5))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbotipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(lblNombreCliente)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDireccion)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(cbotipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jButton5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addComponent(jButton4))
        );

        jPanel6.setBackground(new java.awt.Color(14, 40, 68));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Detalle de Pedido");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(201, 201, 201))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(14, 40, 68));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Lista de Platos");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(197, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(198, 198, 198))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(901, 901, 901))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboCartasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboCartasItemStateChanged
        listarPlatos();
    }//GEN-LAST:event_cboCartasItemStateChanged

    private void btnCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCantidadActionPerformed
      int fsel=tblPlatos.getSelectedRow();
      DefaultTableModel modeloTab = new DefaultTableModel();
      
        if(fsel==-1){
                JOptionPane.showMessageDialog(null, "Debe seleccionar un plato", "Error", JOptionPane.ERROR_MESSAGE);
            }else
            try{

                modeloTab= (DefaultTableModel) tblPlatos.getModel();
                
                String datos[]= new String [5];
                datos[0]= tblPlatos.getValueAt( fsel, 0).toString(); //id
                datos[3]= txtCantidad.getText();  //cantidad
                datos[1]= tblPlatos.getValueAt( fsel, 1).toString();//nombre
                String precio= obtenerPrecio(tblPlatos.getValueAt( fsel, 2).toString()); //precio
                
                float x = Float.parseFloat(datos[3])*Float.parseFloat(precio);
                String importe= String.valueOf(x);
                datos[2]="S/ "+precio;
                datos[4]="S/ "+importe;
                
             
        
                modeloTabVenta.addRow(datos);
                tblVenta.setModel(modeloTabVenta);
                calcularTotales();
                txtCantidad.setText("");
                
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Debe indicar la cántidad", "Error", JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_btnCantidadActionPerformed
   
   public int guardarComanda() throws SQLException{
        int idCliente=obtenerIDCliente(txtDni.getText());
        int idComandaGenerada=0; 
        try {
            idComandaGenerada=objComanda.guardarComanda(idCliente);
            
        } catch (Exception ex) {
            System.err.println("Error"+ex);
        }
      
      return idComandaGenerada;  
      
    }
   
 public void transacionComanda() throws Exception{
       ArrayList <String> sql= new ArrayList<>();
       int idComanda=guardarComanda();
       
        String codigoComprobante=generarNumeroComprobante();
        String [] codigoC=codigoComprobante.split("-");
        String serie=codigoC[0];
        int correlativo=Integer.parseInt(codigoC[1]);
        
       String tipo;
       float TotalG=Float.parseFloat(lblTG.getText());
       System.err.println("id_comanda"+idComanda+" seria"+serie+" correlativo "+correlativo+"total ");
       float TotalIGV=Float.parseFloat(lbligv.getText());
       
       float Total=Float.parseFloat(lblTotal.getText());
       
       if(cbotipoComprobante.getSelectedItem().toString().equalsIgnoreCase("Boleta")){
            tipo="B";
        }else{
            tipo="F";
        }
        
        int cantDatos=tblVenta.getRowCount();
   
       //Detalle de comanda
        for(int i=0; i<cantDatos; i++){
            int cantidad=Integer.parseInt(tblVenta.getValueAt(i, 3).toString());
            float precioV=Float.parseFloat(obtenerPrecio(tblVenta.getValueAt(i, 2).toString()));
            int idPlato=Integer.parseInt(tblVenta.getValueAt(i, 0).toString());
            float costo= objPlato.ObtenerCostoPlato(idPlato);
            System.err.println("cantidad "+cantidad+" costo "+costo+" precio "+precioV+"id_plato "+idPlato+" id_comanda "+idComanda);
            sql.add("insert into detalle_comanda (cantidad, costo, precio_vendido, fk_plato, fk_comanda) values("+cantidad+","+costo+","+precioV+","+idPlato+","+idComanda+")");
        }
        //Comprobante
          sql.add("insert into comprobante (serie, correlativo, tipo, total_gravadas, total_igv, importe_total, fk_comanda) values('"+serie+"',"+correlativo+",'"+tipo+"',"+TotalG+","+TotalIGV+","+Total+","+idComanda+")");
        
            objComanda.transacionComanda(sql);
            JOptionPane.showMessageDialog(null, "Comanda generada exitosamente");
            txtDni.setText("");
            lblDireccion.setText("");
            lblNombreCliente.setText("");
            lblTG.setText("0.00");
            lblTotal.setText("0.00");
            lbligv.setText("0.00");
            generarNumeroComprobante();
            try{
              for(int j=0; j<cantDatos-1; j++){
                 modeloTabVenta.removeRow(j);
                 j-=1;
                 }
                 modeloTabVenta.removeRow(0);
            }catch (Exception e){}
     
        //Generando comprobante para imprimir
        CapaPresentacion.reportes.jdComprobante jd=new  CapaPresentacion.reportes.jdComprobante (null, true);
            CapaPresentacion.reportes.jdComprobante.idComanda=idComanda;
            obtenerDatosImpresion(idComanda);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
        
    }
    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased
        busquedaFiltradaPlatos();
    }//GEN-LAST:event_txtBusquedaKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
     int fell= tblVenta.getSelectedRow();
        if (fell==-1){
            JOptionPane.showMessageDialog(null, "Debe seleccionar un plato para poder eliminar", "Error",JOptionPane.ERROR_MESSAGE);
        }else{
            modeloTabVenta= (DefaultTableModel) tblVenta.getModel();
            modeloTabVenta.removeRow(fell);
        }

        calcularTotales();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int rpt=buscarCliente(txtDni.getText());
        if(rpt==0){
            jdMantenimientoCliente jd=new jdMantenimientoCliente(null, true);
            jdMantenimientoCliente.txtDocumento.setText(txtDni.getText());
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            
        }
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbotipoComprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbotipoComprobanteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cbotipoComprobanteMouseClicked

    private void cbotipoComprobanteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbotipoComprobanteItemStateChanged
        try {
            generarNumeroComprobante();
        } catch (SQLException ex) {
            Logger.getLogger(jdCajero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbotipoComprobanteItemStateChanged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
     int tamañoCadena=txtDni.getText().length();
     String tipo=cbotipoComprobante.getSelectedItem().toString();
        
        if(tamañoCadena!=11  && tipo.equalsIgnoreCase("Factura")){
            JOptionPane.showMessageDialog(null, "Para generar una Factura el documento ingresado debe ser RUC","Error",JOptionPane.ERROR_MESSAGE);
        }else{
            if((tamañoCadena!=8 && tamañoCadena!=11)  && tipo.equalsIgnoreCase("Boleta")){
                JOptionPane.showMessageDialog(null, "El Número de Documento debe tener 8 digitos para DNI  o 11 digitos para RUC","Error",JOptionPane.ERROR_MESSAGE);
            }else{
                  if(tblVenta.getRowCount()>=1){
                      try {
                          transacionComanda();
                          
                         
                      } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al guardar comanda", "Error",JOptionPane.ERROR_MESSAGE);
                        }
                  }else{
                      JOptionPane.showMessageDialog(null, "Debe agregar plato primero", "Error",JOptionPane.ERROR_MESSAGE);
                  }
            }
            
            }
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(jdCajero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jdCajero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jdCajero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jdCajero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    jdCajero dialog = new jdCajero(new javax.swing.JFrame(), true);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    dialog.setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(jdCajero.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCantidad;
    private javax.swing.JComboBox<String> cboCartas;
    private javax.swing.JComboBox<String> cbotipoComprobante;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCodigoComprobante;
    public static javax.swing.JLabel lblDireccion;
    public static javax.swing.JLabel lblNombreCliente;
    private javax.swing.JLabel lblTG;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lbligv;
    private javax.swing.JTable tblPlatos;
    private javax.swing.JTable tblVenta;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtCantidad;
    public static javax.swing.JTextField txtDni;
    // End of variables declaration//GEN-END:variables
}
