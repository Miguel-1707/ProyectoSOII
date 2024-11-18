/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_sistemas_operativos;

import java.util.*;
import java.io.*;
import java.net.*;

public class Cliente{

    private final int PUERTO = 1234; //Puerto para la conexión
    private final String HOST = "localhost"; //Host para la conexión
    private String mensajeServidor; //Mensajes entrantes (recibidos) en el servidor
    private Socket cs; //Socket del cliente
    private DataOutputStream salidaServidor; //Flujo de datos de salida
    
    public Cliente() throws IOException{
       cs = new Socket(HOST, PUERTO); //Socket para el cliente en localhost en puerto 1234
       System.out.println("Iniciando cliente\n");
     } 

    public void startClient(){ //Método para iniciar el cliente
        Scanner teclado=new Scanner(System.in);
        String msg=null;
        try{            
            //Flujo de datos hacia el servidor
            salidaServidor = new DataOutputStream(cs.getOutputStream());              
             do{                
                 msg=teclado.nextLine();
                 salidaServidor.writeUTF( msg+"\n");                
               }while(msg.compareTo("exit")!=0);                       

            cs.close();//Fin de la conexión
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

 public static void main(String[] args) { //throws IOException
     Cliente cli=null;
       try{ 
          cli = new Cliente(); //Se crea el cliente e intenta hacer la conexion
        }catch(IOException e){
            System.out.println("\nError en la conexion, Servidor ausente  ");}
        if( cli!=null)
          cli.startClient(); //el cliente envia mensajes al servidor
    }  
}    