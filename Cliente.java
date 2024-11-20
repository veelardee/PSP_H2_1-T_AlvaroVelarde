package Hito2;

import java.io.*;
import java.net.*;

/**
 * Clase que implementa el cliente con un menú interactivo.
 */
public class Cliente {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 1111;

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println(in.readLine()); // Mensaje de bienvenida del servidor

            while (true) {
                // Imprimir menú
                printMenu();

                // Leer opción del usuario
                System.out.print("Seleccione una opción: ");
                String option = console.readLine();

                if ("3".equals(option)) { // Salir
                    System.out.println("Cerrando conexión...");
                    out.println("salir");
                    break;
                } else if ("1".equals(option)) { // Buscar libro
                    System.out.print("Ingrese la clave del libro que desea buscar: ");
                    String key = console.readLine();
                    out.println("buscar " + key);
                } else if ("2".equals(option)) { // Listar libros
                    out.println("listar");
                } else { // Opción no válida
                    System.out.println("Opción no válida. Intente de nuevo.");
                    continue;
                }

                // Leer y mostrar la respuesta del servidor
                System.out.println("Respuesta del servidor: " + in.readLine());
            }
        } catch (IOException e) {
            System.out.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }

    /**
     * Imprime el menú de opciones para el cliente.
     */
    private static void printMenu() {
        System.out.println("\n--- Menú ---");
        System.out.println("1. Buscar libro");
        System.out.println("2. Listar libros disponibles");
        System.out.println("3. Salir");
        System.out.println("--------------");
    }
}

