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
    
    public Tienda() {
       Thread hiloConexion = new Thread(this,"hiloConexion");
       hiloConexion.start();
       comunicacionUsuario();
    }
    
    @Override
    public void run(){
        try {
            skTienda = new Socket(ipServidor, socketServidor);
        } catch (IOException ex) {
            System.out.println("no se pudo conectar por " + ex.getMessage());;
        }
    }  
        
    private void comunicacionUsuario(){
        boolean salir = false;
        String comando = "";
        Scanner sc = new Scanner(System.in);
        while(!comando.equalsIgnoreCase("Salir")){
            System.out.println("Escriba comando: insertar/ retirar/ consultar/ salir/"
                    + "configurar");
            comando = sc.nextLine().toLowerCase();
            
            switch(comando){
                case "insertar":
                    System.out.println("I");
                    insertar();
                    break;
                case "retirar":
                    System.out.println("R");
                    break;
                case "consultar":
                    System.out.println("C");
                    break;
                case "configurar":
                    System.out.println("CO");
                    break;
                case "salir":
                    System.out.println("Adios");
                    break;
                default:
                    System.out.println("Escriba un comando valido");
                    break;
            }
        }
    }     
    
    public void insertar() {
        Operacion op = new Operacion("insertar", 10);

        try {
            ObjectOutputStream salidaAServidor
                    = new ObjectOutputStream(skTienda.getOutputStream());
            ObjectInputStream entradaDeServidor
                    = new ObjectInputStream(skTienda.getInputStream());

            salidaAServidor.writeObject(op);
            Operacion respuesta = (Operacion) entradaDeServidor.readObject();
            System.out.println(respuesta.toString());
        } catch (IOException ex) {
            System.out.println("io" + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("clas" + ex.getMessage());
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
