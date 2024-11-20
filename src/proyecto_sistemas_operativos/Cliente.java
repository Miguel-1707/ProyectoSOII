package proyecto_sistemas_operativos;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Cliente {
    private final int PUERTO = 1234;
    private final String HOST = "192.168.100.63";
    private Socket cs;
    private DataOutputStream salidaServidor;
    private DataInputStream entradaServidor;
    private JFrame frame;
    private JTextPane textPane;
    private JTextField textField;
    private JButton sendButton;
    private StyledDocument doc;

    @SuppressWarnings("unused")
    public Cliente() {
        try {
            cs = new Socket(HOST, PUERTO);
            System.out.println("Cliente conectado al servidor");

            // Crear la interfaz gráfica
            frame = new JFrame("Cliente");
            textPane = new JTextPane();
            textField = new JTextField(30);
            sendButton = new JButton("Enviar");

            textPane.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textPane);
            scrollPane.setPreferredSize(new Dimension(500, 300)); // Tamaño predeterminado

            // Fondo azul claro
            Color azulSuave = new Color(173, 216, 230);
            frame.getContentPane().setBackground(azulSuave);

            frame.add(scrollPane, "North");
            frame.add(textField, "Center");
            frame.add(sendButton, "South");

            sendButton.addActionListener(e -> enviarMensaje());

            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            doc = textPane.getStyledDocument();
        } catch (IOException e) {
            System.out.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }

    public void startClient() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                entradaServidor = new DataInputStream(cs.getInputStream());
                salidaServidor = new DataOutputStream(cs.getOutputStream());

                String mensaje;
                while ((mensaje = entradaServidor.readUTF()) != null) {
                    mostrarMensaje("Servidor: " + mensaje, Color.RED);
                }
            } catch (IOException e) {
                System.out.println("Conexión cerrada.");
            }
        });
        executor.shutdown();
    }

    private void enviarMensaje() {
        try {
            String mensaje = textField.getText();
            if (!mensaje.isEmpty()) {
                salidaServidor.writeUTF(mensaje);
                mostrarMensaje("Cliente: " + mensaje, Color.BLUE);
                textField.setText("");
            }
        } catch (IOException e) {
            mostrarMensaje("Error enviando mensaje", Color.GRAY);
        }
    }

    private void mostrarMensaje(String mensaje, Color color) {
        try {
            Style estilo = textPane.addStyle("Color", null);
            StyleConstants.setForeground(estilo, color);
            doc.insertString(doc.getLength(), mensaje + "\n", estilo);
        } catch (BadLocationException e) {
            System.out.println("Error mostrando mensaje: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.startClient();
    }
}
