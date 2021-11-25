/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psp_u03_tc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.Operacion;

/**
 *
 * @author Cristian
 */
public class Tienda extends Thread {
    
    private String ipServidor = "";
    private int socketServidor;
    private String operacion;
    private int cantidad;
    private Socket skTienda = null;
    private Operacion op;
    Thread hiloConexion;
    private Scanner sc = new Scanner(System.in);
    
    public Tienda() {
       configConexion();
       hiloConexion = new Thread(this,"hiloConexion");
       hiloConexion.start();
       comunicacionUsuario();
    }
    
    @Override
    public void run(){
        
        while (skTienda == null || !skTienda.isConnected()) {
            try {
                skTienda = new Socket(ipServidor, socketServidor);
                JOptionPane.showMessageDialog(null, "Te has conectado al almacén");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo conectar");
                configConexion();
            }

        }
    }  
        
    private void comunicacionUsuario() {
        boolean salir = false;
        while (!salir) {
            String comando = "";
            try {   
            comando = JOptionPane.showInputDialog("Escriba comando: insertar/ retirar/ consultar/ salir/"
                    + "configurar").toLowerCase();
            } catch (Exception e) {
                
                System.out.println(e);
                
            }
            
                switch (comando) {
                    case "insertar":

                        insertar();
                        break;
                    case "retirar":

                        retirar();
                        break;
                    case "consultar":

                        consultar();
                        break;
                    case "configurar":
                        configConexion();
                        break;
                    case "salir":

                        salir();
                        salir = true;
                         {
                            try {
                                skTienda.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Tienda.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(null,"Escriba un comando valido");
                        break;
                }

            }
        }
    
    
    private void insertar() {
        int chirimoyas = 0;
        try {
            chirimoyas = Integer.parseInt(JOptionPane.showInputDialog("Cuantas desea insertar?"));
        } catch (Exception e) {
            System.out.println(e);
        }
        op = new Operacion("insertar", chirimoyas);
        conexion();
    }
    
    private void retirar(){
        int chirimoyas = 0;
        try {
            chirimoyas = Integer.parseInt(JOptionPane.showInputDialog("Cuantas desea retirar?"));
        } catch (Exception e) {
            System.out.println(e);
        }
        op = new Operacion("retirar", -chirimoyas);
        conexion();
    }
    
    private void consultar(){
        op = new Operacion("consultar");
        try {
            ObjectOutputStream salidaAServidor
                    = new ObjectOutputStream(skTienda.getOutputStream());
            ObjectInputStream entradaDeServidor
                    = new ObjectInputStream(skTienda.getInputStream());

            salidaAServidor.writeObject(op);
            op = (Operacion) entradaDeServidor.readObject();
            JOptionPane.showMessageDialog(null,"El stock de chirimoyas es de " + op.getCantidad() + ".\n");
        } catch (IOException ex) {
            System.out.println("io" + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("clas" + ex.getMessage());
        }
        
    }
    
    private void configConexion() {

        if (skTienda == null ||!skTienda.isConnected()) {
            String ip = JOptionPane.showInputDialog("Introduce IP");
            int puerto = Integer.parseInt(JOptionPane.showInputDialog("Introduce Puerto"));
            ipServidor = ip;
            socketServidor = puerto;
        } else {
            JOptionPane.showMessageDialog(null, "La conexión con el almacen ya está establecida");
        }
    }
    
    private void salir(){
        op = new Operacion("salir");
        conexion();
    }
    
    private void conexion(){
        try {
            ObjectOutputStream salidaAServidor
                    = new ObjectOutputStream(skTienda.getOutputStream());
            ObjectInputStream entradaDeServidor
                    = new ObjectInputStream(skTienda.getInputStream());

            salidaAServidor.writeObject(op);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"io" + ex.getMessage());
        }
    }
    
    public String getIpServidor() {
        return ipServidor;
    }

    public void setIpServidor(String ipServidor) {
        this.ipServidor = ipServidor;
    }

    public int getSocketServidor() {
        return socketServidor;
    }

    public void setSocketServidor(int socketServidor) {
        this.socketServidor = socketServidor;
    }
    
    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
    
    
}
