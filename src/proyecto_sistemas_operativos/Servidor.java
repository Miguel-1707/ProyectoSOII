
package proyecto_sistemas_operativos;

import java.io.*;
import java.net.*;

public class Servidor{  
    private final int PUERTO = 1234; //Puerto para la conexión
    private final String HOST = "localhost"; //Host para la conexión
    private String mensajeServidor; //Mensajes entrantes (recibidos) en el servidor
    private ServerSocket ss; //Socket del servidor
    private Socket cs; //Socket del cliente
       
    public Servidor() throws IOException{
       ss = new ServerSocket(PUERTO);//Se crea el socket para el servidor en puerto 1234
       cs = new Socket(); //Socket para el cliente
       System.out.println("Iniciando servidor\n");
    }

    public void startServer() {   //Método para iniciar el servidor   
        try{
            System.out.println("Esperando..."); //Esperando conexión

            cs = ss.accept(); //Accept comienza el socket y espera una conexión desde un cliente
            System.out.println("Cliente en línea");

            //Se obtiene el flujo entrante desde el cliente
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            
            while((mensajeServidor = entrada.readLine()) != null){ //Mientras haya mensajes desde el cliente            
                //Se muestra por pantalla el mensaje recibido
                System.out.println(mensajeServidor);
            }
            
            System.out.println("Fin de la conexión");
            
            ss.close();//Se finaliza la conexión con el cliente
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

     public static void main(String[] args) throws IOException{        
        Servidor serv=null;

        try{
         serv= new Servidor(); //Se crea el servidor
        }catch(IOException e){
             System.out.println("Error al iniciar el Servidor");
         }
         if(serv!=null)
            serv.startServer(); //Se inicia el servidor
    }
}
