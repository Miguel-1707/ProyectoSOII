package proyecto_sistemas_operativos;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Servidor {
    private final int PUERTO = 1234;
    private ServerSocket ss;
    private Socket cs;
    private DataOutputStream salidaCliente; // Flujo para enviar al cliente
    private DataInputStream entradaCliente;  // Flujo para recibir del cliente

    public Servidor() throws IOException {
        ss = new ServerSocket(PUERTO);
        System.out.println("Iniciando servidor\n");
    }

    // Para iniciar el servidor
    public void startServer() {
        try {
            System.out.println("Esperando por conexión...");
            cs = ss.accept(); // Acepta al cliente
            System.out.println("Cliente en línea");

            entradaCliente = new DataInputStream(cs.getInputStream());
            salidaCliente = new DataOutputStream(cs.getOutputStream());
            Scanner teclado = new Scanner(System.in); // Para enviar desde el servidor

            String mensajeRecibido, mensajeEnviado;

            do {
                // Leer del cliente
                mensajeRecibido = entradaCliente.readUTF();
                System.out.println("Mensaje del cliente: " + mensajeRecibido);

                // Enviar al cliente
                System.out.println("Escriba el mensaje al cliente: ");
                mensajeEnviado = teclado.nextLine();
                salidaCliente.writeUTF(mensajeEnviado);
            } while (!mensajeEnviado.equals("exit"));

            System.out.println("Conexión Terminada");
            cs.close(); // Cerrar conexión
            ss.close(); // Finalizar servidor
            teclado.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        Servidor serv = null;
        try {
            serv = new Servidor();
        } catch (IOException e) {
            System.out.println("Error al iniciar el Servidor");
        }
        if (serv != null)
            serv.startServer();
    }
}
