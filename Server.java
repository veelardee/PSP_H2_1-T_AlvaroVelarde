package Hito2;


import java.io.*;
import java.net.*;

/**
 * Clase que implementa el servidor multicliente.
 */
public class Server {
    public static void main(String[] args) {
        int port = 1111; // Puerto del servidor
        DataAccess dataAccess = new DataAccess();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado. Esperando clientes...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado.");
                new Thread(new ClientHandler(clientSocket, dataAccess)).start();
            }
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}

/**
 * Clase que gestiona las solicitudes de un cliente.
 */
class ClientHandler implements Runnable {
    private Socket clientSocket;
    private DataAccess dataAccess;

    public ClientHandler(Socket clientSocket, DataAccess dataAccess) {
        this.clientSocket = clientSocket;
        this.dataAccess = dataAccess;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            out.println("Conectado al servidor. Opciones: [buscar <clave>] o [listar].");

            String request;
            while ((request = in.readLine()) != null) {
                if (request.startsWith("buscar")) {
                    String key = request.substring(7).trim();
                    out.println(dataAccess.getData(key));
                } else if (request.equalsIgnoreCase("listar")) {
                    out.println("Claves disponibles: " + dataAccess.listKeys());
                } else {
                    out.println("Comando no reconocido. Usa [buscar <clave>] o [listar].");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al atender al cliente: " + e.getMessage());
        }
    }
}

