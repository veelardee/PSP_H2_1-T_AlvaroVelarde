package Hito2;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que gestiona el acceso a los datos desde un fichero binario.
 */
public class DataAccess {
    private Map<String, String> data;
    private static final String FILE_NAME = "libros.dat";

    public DataAccess() {
        data = new HashMap<>();
        ensureFileExists();
        loadDataFromFile(FILE_NAME);
    }

    /**
     * Asegura que el archivo binario existe, y lo crea con datos de ejemplo si no está presente.
     */
    private void ensureFileExists() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            // Crear datos iniciales
            data.put("java", "Introducción a la Programación en Java");
            data.put("python", "Python para Principiantes");
            data.put("javascript", "JavaScript: Las Buenas Prácticas");
            data.put("c#", "C# a Fondo");

            // Guardar datos en el archivo binario
            saveDataToFile();
            System.out.println("Archivo 'libros.dat' creado con datos iniciales.");
        }
    }

    /**
     * Carga los datos desde el archivo binario indicado.
     *
     * @param fileName Nombre del archivo.
     */
    private void loadDataFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj = ois.readObject();
            
            // Verificación de tipo seguro
            if (obj instanceof Map<?, ?>) {
                // Comprobamos que las claves y valores sean del tipo correcto
                Map<?, ?> tempMap = (Map<?, ?>) obj;
                data = new HashMap<>();
                for (Map.Entry<?, ?> entry : tempMap.entrySet()) {
                    if (entry.getKey() instanceof String && entry.getValue() instanceof String) {
                        data.put((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                System.out.println("Datos cargados correctamente desde 'libros.dat'.");
            } else {
                System.out.println("El archivo no contiene un formato válido de datos.");
                data = new HashMap<>(); // Reiniciamos los datos en caso de error
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar los datos: " + e.getMessage());
        }
    }


    /**
     * Guarda los datos en el archivo binario.
     */
    private void saveDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    /**
     * Obtiene el valor asociado a una clave.
     *
     * @param key Clave de búsqueda.
     * @return Valor asociado a la clave o un mensaje de error.
     */
    public String getData(String key) {
        return data.getOrDefault(key.toLowerCase(), "No se encontró información para: " + key);
    }

    /**
     * Obtiene todas las claves disponibles en los datos.
     *
     * @return Cadena con todas las claves.
     */
    public String listKeys() {
        return String.join(", ", data.keySet());
    }
}
