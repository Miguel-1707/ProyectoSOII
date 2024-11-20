package proyecto_sistemas_operativos;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
    private final int PUERTO = 1234;
    private ServerSocket ss;
    private Socket cs;
    private DataOutputStream salidaCliente;
    private DataInputStream entradaCliente;
    private JFrame frame;
    private JTextPane textPane;
    private JTextField textField;
    private JButton sendButton;
    private StyledDocument doc;

    @SuppressWarnings("unused")
    public Servidor() {
        try {
            ss = new ServerSocket(PUERTO);
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            // Crear la interfaz gráfica
            frame = new JFrame("Servidor");
            textPane = new JTextPane();
            textField = new JTextField(30);
            sendButton = new JButton("Enviar");

            textPane.setEditable(false); // El área de texto será solo de lectura

            JScrollPane scrollPane = new JScrollPane(textPane);
            scrollPane.setPreferredSize(new Dimension(500, 300)); // Tamaño predeterminado del área de texto

            // Fondo rojo claro
            Color rojoSuave = new Color(255, 182, 193);
            frame.getContentPane().setBackground(rojoSuave);

            frame.add(scrollPane, "North");
            frame.add(textField, "Center");
            frame.add(sendButton, "South");

            sendButton.addActionListener(e -> enviarMensaje());

            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            doc = textPane.getStyledDocument();
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    public void startServer() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                System.out.println("Esperando por conexión...");
                cs = ss.accept();
                System.out.println("Cliente conectado");

                entradaCliente = new DataInputStream(cs.getInputStream());
                salidaCliente = new DataOutputStream(cs.getOutputStream());

                String mensaje;
                while ((mensaje = entradaCliente.readUTF()) != null) {
                    mostrarMensaje("Cliente: " + mensaje, Color.BLUE);
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
                salidaCliente.writeUTF(mensaje);
                mostrarMensaje("Servidor: " + mensaje, Color.RED);
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
        Servidor servidor = new Servidor();
        servidor.startServer();
    }
}
