/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capaLogica;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author gonza
 */
public class Validaciones {
    
    public static void validarSoloNumeros(JTextField campo){
        campo.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
            char c= e.getKeyChar();
            if(!Character.isDigit(c)&& c!='.' ){
                e.consume();
            }
            if(c=='.'&& campo.getText().contains(".")){
            e.consume();
            }
            }
    
    });
    }
     public static void validarSoloLetras(JTextField campo){
        campo.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
            char c= e.getKeyChar();
            if(Character.isDigit(c)){
                e.consume();
            }
            }
    
    });
    } 

}
