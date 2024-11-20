package proyecto_sistemas_operativos;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    private final int PUERTO = 1234;
    private final String HOST = "192.168.100.63";
    private Socket cs;
    private DataOutputStream salidaServidor; // Flujo para enviar al servidor
    private DataInputStream entradaServidor; // Flujo para recibir del servidor

    public Cliente() throws IOException {
        cs = new Socket(HOST, PUERTO);
        System.out.println("Iniciando cliente\nEspere por favor...");
    }

    public void startClient() {
        try {
            salidaServidor = new DataOutputStream(cs.getOutputStream());
            entradaServidor = new DataInputStream(cs.getInputStream());
            Scanner teclado = new Scanner(System.in); // Para escribir hacia el servidor

            String mensajeEnviado, mensajeRecibido;

            do {
                // Enviar al servidor
                System.out.println("Escriba el mensaje al servidor:");
                mensajeEnviado = teclado.nextLine();
                salidaServidor.writeUTF(mensajeEnviado);

                // Leer del servidor
                mensajeRecibido = entradaServidor.readUTF();
                System.out.println("Mensaje del servidor: " + mensajeRecibido);
            } while (!mensajeEnviado.equals("exit"));

            System.out.println("Conexión cerrada");
            cs.close(); // Cerrar la conexión
            teclado.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Cliente cli = null;
        try {
            cli = new Cliente();
        } catch (IOException e) {
            System.out.println("\nError en la conexión, servidor ausente");
        }
        if (cli != null) {
            cli.startClient();
        }
    }
}
