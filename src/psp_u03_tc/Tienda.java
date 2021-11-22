/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psp_u03_tc;

import java.net.Socket;
import java.util.Scanner;



/**
 *
 * @author Cristian
 */
public class Tienda extends Thread {
    
    private String ipServidor = "Localhost";
    private int socketServidor = 5000;
    private String operacion;
    private int cantidad;
    private Socket tienda = null;
    
    public Tienda() {
       Thread hiloConexion = new Thread(this,"hiloConexion");
       hiloConexion.start();
       comunicacionUsuario();
    }
    
    @Override
    public void run(){
        
    }  
        
    private void comunicacionUsuario(){
        boolean salir = false;
        String comando = "";
        Scanner sc = new Scanner(System.in);
        while(!comando.equalsIgnoreCase("Salir")){
            System.out.println("Escriba comando: insertar/ retirar/ consultar/ "
                    + "configurar");
            comando = sc.nextLine().toLowerCase();
            
            switch(comando){
                case  "insertar":
                    System.out.println("I");
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
