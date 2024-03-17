package preprocesamiento;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        
        // ---------- DECLARACIÓN DE VARIABLES Y DEFINICIÓN DE RUTAS ----------

        // stopwords - Lista para almacenar las stopwords de nuestro diccionario genérico y las agregadas por nosotros
        HashSet<String> stopwords = new HashSet<String>();

        // Rutas al dataset original, dataset final y diccionarios de stopwords
        String  rawDataset = "/Users/pame/Desktop/concurrencia/Datasets/Reddit_ADHD.csv",
                finalDataset = "/Users/pame/Desktop/concurrencia/Datasets/DatasetFinal.txt",
                stopwordsGenerales = "src/recursos/swGenerales.txt",
                stopwordsAgregadas = "src/recursos/swAgregadas.txt",
                linea = "";
        
        
        // ---------- LEER Y GUARDAR STOPWORDS EN ARRAYLIST ----------
        
        // Leer las stopwords de nuestro diccionario de stopwords genérico y guardarlas en el HashSet
        try (BufferedReader lectura = new BufferedReader(new FileReader(stopwordsGenerales))) {
            while ((linea = lectura.readLine()) != null)
                 stopwords.add(linea.trim().toLowerCase().replaceAll("\\P{Print}",""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Leer las stopwords que agregamos para también guardarlas en el HashSet
        try (BufferedReader lectura = new BufferedReader(new FileReader(stopwordsAgregadas))) {
            while ((linea = lectura.readLine()) != null)
                if (!linea.isBlank() && !linea.isEmpty())
                    stopwords.add(linea.trim().toLowerCase().replaceAll("\\P{Print}",""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Leyendo y guardando stopwords...");
        
        
        // ---------- LECTURA DEL DATASET, VALIDACIÓN DE DATOS Y ESCRITURA EN ARCHIVO DE DATASET FINAL ----------
        
        System.out.println("Leyendo y validando datos...");
        
         // Empezar a leer el dataset original usando BufferedReader y FileReader
         try (BufferedReader lectura = new BufferedReader(new FileReader(rawDataset))) {
             
            // BufferedWriter para escribir en el archivo del dataset final
            BufferedWriter writer = new BufferedWriter(new FileWriter(finalDataset));

            // Leer palabras y omitir símbolos
            while ((linea = lectura.readLine()) != null) {
                int i = 0;
                String palabras = "";
                
                while (i < linea.length()) {
                    boolean salto = false;
                    // Llamamos al método esLetra para validar el caracter
                    while (i < linea.length() && esLetra(linea.charAt(i))) {
                        palabras += linea.charAt(i);
                        salto = true;
                        i++;
                    }
                    // La variable salto nos dice si acabamos de leer una palabra, de ser así, haremos salto de línea
                    if (salto)
                        palabras += '\n';
                    i++;
                }

                // Crear un arreglo con las palabras leídas del dataset (sin validar aún)
                String[] arrPalabras = palabras.trim().split("\\s+");

                // String para almacenar la nueva linea (libre de stopwords y símbolos)
                String lineaValidada = "";
                
                // Recorrer palabra por palabra buscando stopwords y omitiéndolas
                for (String p : arrPalabras) {
                    // Esta línea elimmina todos los caracteres no imprimibles según Unicode
                    // Sin ella, el .contains no funcionaría porque no compararía bien las Strings
                    p.replaceAll("\\P{Print}","");
                    if (stopwords.contains(p.toLowerCase())) {
                        // No omitimos las stopwords, solo las reemplazamos por este caracter: ¥
                        // Hacemos esto para garantizar que en el WordCount de pares se tomen 
                        // siempre dos palabras que realmente estén una seguida de la otra
                        // (si sólo omitimos las stopwords, se pierde el orden real que tenían las palabras en el dataset original)
                        lineaValidada +=  "¥\n";
                    }
                    else if (
                        // Validar que no sea una stopword
                        !stopwords.contains(p.toLowerCase())

                        // Validar que contenga al menos dos caracteres
                        && p.length() > 2
                            
                        ) {
                        // Si la palabra es válida, agregar a lineaValidada
                        lineaValidada += p.toLowerCase() + "\n";
                    }
                }
                
                // Validar no guardar la línea si todas las palabras en esa línea eran stopwords (porque la línea estaría vacía)
                if (!lineaValidada.isEmpty() && !lineaValidada.isBlank()) {
                    writer.write(lineaValidada.trim().toLowerCase());
                    writer.newLine();
                }

            }
            System.out.println("El dataset fue preprocesado exitosamente.");
            
         } catch (IOException e) {
            e.printStackTrace();
         }
         
    }

    
    // Método que valida que un caracter sea letra o apóstrofe usando ASCII
    public static boolean esLetra(char caracter) {
        return  (caracter > 64 && caracter < 91)
                || (caracter > 96 && caracter < 123)
                || caracter == 39;
    }
}
