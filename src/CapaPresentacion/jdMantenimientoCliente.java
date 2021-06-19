/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacion;

import CapaNegocio.clsCliente;
import capaLogica.Validaciones;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Omar AGA
 */
public class jdMantenimientoCliente extends javax.swing.JDialog {
    clsCliente objCliente= new clsCliente();
    
    /**
     * Creates new form jdMantenimientoCliente
     */
    public jdMantenimientoCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        listarClientes();
        Validaciones.validarSoloNumeros(txtDocumento);
        poputTable();
        Toolkit t= Toolkit.getDefaultToolkit();
        setIconImage(t.getImage(getClass().getResource("/Media/icono.png")));
        
    }
    
    public void listarClientes(){
        
        DefaultTableModel modeloTab = new DefaultTableModel();
        modeloTab.addColumn("ID");
        modeloTab.addColumn("Documento");
        modeloTab.addColumn("Nombre Completo");
        modeloTab.addColumn("Dirección");
        modeloTab.addColumn("Estado");
   
        tblCliente.setModel(modeloTab);
        ResultSet data = null;
        
        
        TableColumn  columna = tblCliente.getColumn("ID");
        columna.setMinWidth(20);
        columna.setMaxWidth(40);
        TableColumn  columna2 = tblCliente.getColumn("Documento");
        columna2.setMinWidth(90);
        columna2.setMaxWidth(120);
        TableColumn  columna3 = tblCliente.getColumn("Estado");
        columna3.setMinWidth(40);
        columna3.setMaxWidth(60);
        TableColumn  columna4 = tblCliente.getColumn("Nombre Completo");
        columna4.setMinWidth(280);
        columna4.setMaxWidth(280);
     
        try {
               data=objCliente.listarClientes();
               while(data.next()){
                   String estado=null;
                   if(data.getBoolean("estado")==true){
                       estado="Activo";
                   }else{
                    estado="No Activo";
                   }
                   modeloTab.addRow(new Object[]{data.getInt(1),data.getString(2),data.getString(4), data.getString(5), estado});
                  
                   
               }
        } catch (Exception e) {
            System.err.println("error"+ e);
        }
    
    
    }
    
        public void busquedaFiltradaClientePorNombre(String busqueda){
        
        DefaultTableModel modeloTab = new DefaultTableModel();
        modeloTab.addColumn("ID");
        modeloTab.addColumn("Documento");
        modeloTab.addColumn("Nombre Completo");
        modeloTab.addColumn("Dirección");
        modeloTab.addColumn("Estado");
   
        tblCliente.setModel(modeloTab);
        ResultSet data = null;
        
        
        TableColumn  columna = tblCliente.getColumn("ID");
        columna.setMinWidth(20);
        columna.setMaxWidth(40);
        TableColumn  columna2 = tblCliente.getColumn("Documento");
        columna2.setMinWidth(90);
        columna2.setMaxWidth(120);
        TableColumn  columna3 = tblCliente.getColumn("Estado");
        columna3.setMinWidth(40);
        columna3.setMaxWidth(60);
        TableColumn  columna4 = tblCliente.getColumn("Nombre Completo");
        columna4.setMinWidth(280);
        columna4.setMaxWidth(280);
     
        try {
               data=objCliente.busquedaFiltradaClientesPorNombre(busqueda);
               while(data.next()){
                   String estado=null;
                   if(data.getBoolean("estado")==true){
                       estado="Activo";
                   }else{
                    estado="No Activo";
                   }
                   modeloTab.addRow(new Object[]{data.getInt(1),data.getString(2),data.getString(4), data.getString(5), estado});
                  
                   
               }
        } catch (Exception e) {
            System.err.println("error"+ e);
        }
    }
    
  public void busquedaFiltradaClientePorDocumento(String busqueda){
        
        DefaultTableModel modeloTab = new DefaultTableModel();
        modeloTab.addColumn("ID");
        modeloTab.addColumn("Documento");
        modeloTab.addColumn("Nombre Completo");
        modeloTab.addColumn("Dirección");
        modeloTab.addColumn("Estado");
   
        tblCliente.setModel(modeloTab);
        ResultSet data = null;
        
        
        TableColumn  columna = tblCliente.getColumn("ID");
        columna.setMinWidth(20);
        columna.setMaxWidth(40);
        TableColumn  columna2 = tblCliente.getColumn("Documento");
        columna2.setMinWidth(90);
        columna2.setMaxWidth(120);
        TableColumn  columna3 = tblCliente.getColumn("Estado");
        columna3.setMinWidth(40);
        columna3.setMaxWidth(60);
        TableColumn  columna4 = tblCliente.getColumn("Nombre Completo");
        columna4.setMinWidth(280);
        columna4.setMaxWidth(280);
     
        try {
               data=objCliente.busquedaFiltradaClientesPorDocumento(busqueda);
               while(data.next()){
                   String estado=null;
                   if(data.getBoolean("estado")==true){
                       estado="Activo";
                   }else{
                    estado="No Activo";
                   }
                   modeloTab.addRow(new Object[]{data.getInt(1),data.getString(2),data.getString(4), data.getString(5), estado});
                  
                   
               }
        } catch (Exception e) {
            System.err.println("error"+ e);
        }
    }
    public int validarClienteDuplicado(String documento){
        ResultSet data = null;
        int rpt=0;
     
        try {
               data=objCliente.validadClienteDuplicado(documento);
               while(data.next()){
                   rpt=1;
               }
        } catch (Exception e) {
            System.err.println("error"+ e);
        }
      return rpt;
    }
    public void poputTable(){
    JPopupMenu pupupMenu= new JPopupMenu();
    JMenuItem menuItem1=new JMenuItem("Agregar", new ImageIcon(getClass().getResource("/Media/add.png")));
    menuItem1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        
           jdCajero.txtDni.setText(txtDocumento.getText());
           jdCajero.lblDireccion.setText(txtDireccion.getText());
           jdCajero.lblNombreCliente.setText(txtNombre.getText());
           dispose(); 
        }
    });
    pupupMenu.add(menuItem1);
    tblCliente.setComponentPopupMenu(pupupMenu);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        btnModificar = new javax.swing.JButton();
        btnDarBaja = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        cboTipoD = new javax.swing.JComboBox<>();
        btnActualizar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtBusqueda = new javax.swing.JTextField();
        cboTipoB = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCliente = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(14, 40, 68));

        jLabel1.setBackground(new java.awt.Color(14, 40, 68));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("MANTENIMIENTO DE CLIENTES");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(14, 40, 68));
        jLabel2.setText("N° Documento");

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        jButton1.setBackground(new java.awt.Color(14, 40, 68));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/icoBuscR.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(14, 40, 68));
        jLabel4.setText("Nombre Completo:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(14, 40, 68));
        jLabel5.setText("Dirección:");

        txtDocumento.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        txtDireccion.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        btnModificar.setBackground(new java.awt.Color(14, 40, 68));
        btnModificar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnDarBaja.setBackground(new java.awt.Color(14, 40, 68));
        btnDarBaja.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDarBaja.setForeground(new java.awt.Color(255, 255, 255));
        btnDarBaja.setText("Dar de Baja");
        btnDarBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDarBajaActionPerformed(evt);
            }
        });

        btnRegistrar.setBackground(new java.awt.Color(14, 40, 68));
        btnRegistrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        cboTipoD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cboTipoD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI", "RUC" }));

        btnActualizar.setBackground(new java.awt.Color(14, 40, 68));
        btnActualizar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/loop.png"))); // NOI18N
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cboTipoD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(btnRegistrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDarBaja)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel4)
                    .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTipoD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificar)
                    .addComponent(btnDarBaja)
                    .addComponent(btnRegistrar)
                    .addComponent(btnActualizar))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(14, 40, 68));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/buscar.png"))); // NOI18N
        jLabel10.setText("Busqueda por:");

        txtBusqueda.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
        });

        cboTipoB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cboTipoB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Documento" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboTipoB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(125, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTipoB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCliente);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 24, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(87, 87, 87)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(585, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased
      String busqueda=cboTipoB.getSelectedItem().toString();
      if(busqueda.equalsIgnoreCase("Nombre")){
          busquedaFiltradaClientePorNombre(txtBusqueda.getText());
      }else{
          busquedaFiltradaClientePorDocumento(txtBusqueda.getText());
      }
    }//GEN-LAST:event_txtBusquedaKeyReleased

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        String documento=txtDocumento.getText();
        String nombre=txtNombre.getText().toUpperCase();
        String direccion= txtDireccion.getText();
        String tipo=null;
        int pos=tblCliente.getSelectedRow();
        if(pos==-1){
            JOptionPane.showMessageDialog(null, "Tiene que seleccionar un cliente para poder modificar", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            String idCliente=tblCliente.getValueAt(pos, 0).toString();

                if(cboTipoD.getSelectedItem().toString().equalsIgnoreCase("DNI")){
                    tipo="D";
                }else{
                    tipo="R";
                }
                if(documento.length()!=8 && tipo=="D"){
                    JOptionPane.showMessageDialog(null, "El DNI debe tener 8 números", "Error", JOptionPane.ERROR_MESSAGE);
                }else
                    if(documento.length()!=11 && tipo=="R"){
                    JOptionPane.showMessageDialog(null, "El RUC debe tener 11 números", "Error", JOptionPane.ERROR_MESSAGE);
                    }else{

                            if(txtNombre.getText().isEmpty()){
                                JOptionPane.showMessageDialog(null, "Ingrese el nombre completo del cliente por favor", "Error", JOptionPane.ERROR_MESSAGE);
                            }else{
                                try {
                                    objCliente.modificarCliente(Integer.parseInt(idCliente),documento, nombre, direccion, tipo);
                                    JOptionPane.showMessageDialog(null, "Cliente modificado exitosamente");
                                    busquedaFiltradaClientePorDocumento(documento);
                                    txtDocumento.setText("");
                                    txtNombre.setText("");
                                    txtDireccion.setText("");
                                    btnDarBaja.setText("Dar de Baja");
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(null, "Error al modificar cliente "+ex, "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }

                     }
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnDarBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarBajaActionPerformed
      int pos=tblCliente.getSelectedRow();
        if(pos==-1){
            JOptionPane.showMessageDialog(null, "Tiene que seleccionar un cliente para actualzar su estado", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
        
        
            String idCliente=tblCliente.getValueAt(pos, 0).toString();
            String estado=null;
            String documento=txtDocumento.getText();
            if(btnDarBaja.getText().equalsIgnoreCase("Dar de baja")){
                estado="false";
            }else{
                estado="true";
            }


                                try {
                                    objCliente.actualizarEstadoCliente(Integer.parseInt(idCliente), estado);
                                    JOptionPane.showMessageDialog(null, "Actualizacion de estado de cliente exitoso");
                                    busquedaFiltradaClientePorDocumento(documento);
                                    txtDocumento.setText("");
                                    txtNombre.setText("");
                                    txtDireccion.setText("");
                                    btnDarBaja.setText("Dar de Baja");
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(null, "Error al actualizar estado de cliente "+ex, "Error", JOptionPane.ERROR_MESSAGE);
                                }
       }
    }//GEN-LAST:event_btnDarBajaActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        String documento=txtDocumento.getText();
        String nombre=txtNombre.getText().toUpperCase();
        String direccion= txtDireccion.getText();
        String tipo=null;
            if(cboTipoD.getSelectedItem().toString().equalsIgnoreCase("DNI")){
                tipo="D";
            }else{
                tipo="R";
            }
            if(documento.length()!=8 && tipo=="D"){
                JOptionPane.showMessageDialog(null, "El DNI debe tener 8 números", "Error", JOptionPane.ERROR_MESSAGE);
            }else
                if(documento.length()!=11 && tipo=="R"){
                JOptionPane.showMessageDialog(null, "El RUC debe tener 11 números", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    if(validarClienteDuplicado(documento)==0){
                        if(txtNombre.getText().isEmpty()){
                            JOptionPane.showMessageDialog(null, "Ingrese el nombre completo del cliente por favor", "Error", JOptionPane.ERROR_MESSAGE);
                        }else{
                            try {
                                objCliente.registrarCliente(documento, nombre, direccion, tipo);
                                
                                    JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente");
                                    busquedaFiltradaClientePorDocumento(documento);
                                    txtDocumento.setText("");
                                    txtNombre.setText("");
                                    txtDireccion.setText("");
                               
                                
                                
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Error al registrar cliente "+ex.getLocalizedMessage()+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                System.err.println(ex.getLocalizedMessage()+ex.getMessage());
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "El cliente con "+cboTipoD.getSelectedItem().toString()+" N° "+documento+" ya esta registrado", "Error", JOptionPane.ERROR_MESSAGE);
                        txtDocumento.setText("");
                    }
                 }
                
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        listarClientes();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void tblClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClienteMouseClicked
       
        int pos=tblCliente.getSelectedRow();
        String estado=tblCliente.getValueAt(pos, 4).toString();
        if(estado.equalsIgnoreCase("No activo")){
            btnDarBaja.setText("Dar de Alta");
        }else{
            btnDarBaja.setText("Dar de Baja");
        }
        if(tblCliente.getValueAt(pos, 1).toString().length()==8){
            cboTipoD.setSelectedIndex(0);
        }else{
          cboTipoD.setSelectedIndex(1);  
        }
        txtDocumento.setText(tblCliente.getValueAt(pos, 1).toString());
        txtNombre.setText(tblCliente.getValueAt(pos, 2).toString());
        txtDireccion.setText(tblCliente.getValueAt(pos, 3).toString());
        
        //Para Cajero
                                
                                    
                                
    }//GEN-LAST:event_tblClienteMouseClicked

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
            java.util.logging.Logger.getLogger(jdMantenimientoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jdMantenimientoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jdMantenimientoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jdMantenimientoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jdMantenimientoCliente dialog = new jdMantenimientoCliente(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnDarBaja;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cboTipoB;
    private javax.swing.JComboBox<String> cboTipoD;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCliente;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtDireccion;
    public static javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
