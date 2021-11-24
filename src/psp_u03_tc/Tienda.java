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
    
    private String ipServidor = "Localhost";
    private int socketServidor = 5000;
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
                JOptionPane.showMessageDialog(null, "Tienda conectada al almacén");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo conectar");
                configConexion();
            }

        }
    }  
        
    private void comunicacionUsuario(){
        boolean salir = false;
        String comando = "";
        while(!salir){
            System.out.println("Escriba comando: insertar/ retirar/ consultar/ salir/"
                    + "configurar");
            comando = sc.nextLine().toLowerCase();
            
            switch (comando) {
                case "insertar":
                    System.out.println("I");
                    insertar(10);
                    break;
                case "retirar":
                    System.out.println("R");
                    retirar(10);
                    break;
                case "consultar":
                    System.out.println("C");
                    consultar();
                    break;
                case "configurar":
                    configConexion();
                    break;
                case "salir":
                    System.out.println("Adios");
                    salir();
                    salir=true;
                    {
                        try {
                            skTienda.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Tienda.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                default:
                    System.out.println("Escriba un comando valido");
                    break;
            }
        }
    }     
    
    private void insertar(int chirimoyas) {
        op = new Operacion("insertar", chirimoyas);
        conexion();
    }
    
    private void retirar(int chirimoyas){
        op = new Operacion("retirar", -chirimoyas);
        conexion();
    }
    
    private void consultar(){
        op = new Operacion("consultar");
        conexion();
    }
    
    private void configConexion() {
        String ip = JOptionPane.showInputDialog("Introduce IP");
        int puerto = Integer.parseInt(JOptionPane.showInputDialog("Introduce Puerto"));
        ipServidor = ip;
        socketServidor = puerto;
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
            //Operacion respuesta = (Operacion) entradaDeServidor.readObject();
            //System.out.println(respuesta.toString());
        } catch (IOException ex) {
            System.out.println("io" + ex.getMessage());
        } //catch (ClassNotFoundException ex) {
          //  System.out.println("clas" + ex.getMessage());
        //}
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
