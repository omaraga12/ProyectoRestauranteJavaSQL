/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacion;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Omar AGA
 */
public class ImgTabla extends DefaultTableCellRenderer {


    /*public Component geTableCellRenderComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1){
        
    if(o instanceof JLabel){
        JLabel lbl=(JLabel)o;
        return lbl;
              
    }
        return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
    }*/

            @Override
         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         if (value instanceof JLabel) {
         JLabel label = (JLabel) value;
         label.setOpaque(true);
         fillColor(table, label, isSelected);
         return label;
         } else {
         return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         }
         }
         public void fillColor(JTable t, JLabel l, boolean isSelected) {
         if (isSelected) {
         l.setBackground(t.getSelectionBackground());
         l.setForeground(t.getSelectionForeground());
         } else {
         l.setBackground(t.getBackground());
         l.setForeground(t.getForeground());
         }
         }
 }
    

