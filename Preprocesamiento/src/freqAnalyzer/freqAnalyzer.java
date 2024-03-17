
package freqAnalyzer;

import java.io.*;
import java.util.*;
import java.util.Comparator;

public class freqAnalyzer {
    // Rutas a los archivos de output del WordCount
    static String  wordcount = "/Users/pame/Desktop/concurrencia/ProyectoConcurrencia/WordCounter/output/count.txt",
                wcountPares = "/Users/pame/Desktop/concurrencia/ProyectoConcurrencia/WordCounter/output/countPares.txt",
                linea = "";
    static int i = 0, minimumSupport = 5000, top = 20;
    
    // Descomentar el analyzer que se desee correr
    public static void main(String[] args) {
        // Top de palabras más frecuentes
        freqAnalyzer();
        
        // Top de pares de palabras más frecuentes
//        pairFreqAnalyzer();
    }
    
    // Analizar palabras individuales
    public static void freqAnalyzer() {
        // ArrayList donde guardaremos todas las palabras que cumplan con el minimum support
        ArrayList<wordFreq> palabras = new ArrayList<wordFreq>();
        
        try (BufferedReader lectura = new BufferedReader(new FileReader(wordcount))) {
            while ((linea = lectura.readLine()) != null) {
                
                // Validar stopwords (están representadas por este símbolo: ¥)
                if (!linea.contains("¥")) {
                    
                    // Utilizar una expresión regular para dividir la cadena
                    // parts[0] - Contenido
                    // parts[1] - Frecuencia
                    String[] parts = linea.split("\\s+");
                                       
                    // Validar que se cumpla el minimum support
                    if (Integer.parseInt(parts[1]) >= minimumSupport)
                        // Agregar palabra y su frecuencia al HashSet
                        palabras.add(new wordFreq(parts[0], Integer.parseInt(parts[1])));
                    
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Ordenar palabras según frecuencia (orden ascendente)
        palabras.sort(Comparator.comparing(wordFreq::getFreq).reversed());
        
        // Mostrar el top de palabras más frecuentes
        System.out.println(palabras.size());
        while (i < palabras.size() && i <= top) {
            System.out.println((i+1) + ". " + palabras.get(i));
            i++;
        }
            
    }
    
    // Analizar pares de palabras
    public static void pairFreqAnalyzer() {
        // ArrayList donde guardaremos todos los pares que cumplan con el minimum support
        ArrayList<wordFreq> pares = new ArrayList<wordFreq>();
        
        try (BufferedReader lectura = new BufferedReader(new FileReader(wcountPares))) {
            while ((linea = lectura.readLine()) != null) {
                
                // Validar stopwords (están representadas por este símbolo: ¥)
                if (!linea.contains("¥")) {
                    
                    // Utilizar una expresión regular para dividir la cadena
                    // parts[0] - Contenido
                    // parts[1] - Frecuencia
                    String[] parts = linea.split("\\s+");
                    String contenido = parts[0];
                                       
                    // Validar que se cumpla el minimum support
                    if (Integer.parseInt(parts[1]) >= minimumSupport)
                        // Agregar palabra y su frecuencia al HashSet
                        pares.add(new wordFreq(contenido.replace(",", " "), Integer.parseInt(parts[1])));
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Validar si dos pares tienen las mismas palabras pero en distinto orden
        for (int i = 0; i < pares.size(); i++) {
            wordFreq par = pares.get(i);
            for (int j = 0; j < pares.size(); j++) {
                if (i != j) {
                    wordFreq otro = pares.get(j);
                    if (par.esIgual(otro))
                        pares.remove(otro);
                }
            }
        }
        
        // Ordenar pares según frecuencia (orden ascendente)
        pares.sort(Comparator.comparing(wordFreq::getFreq).reversed());
        
        // Mostrar el top de pares más frecuentes
        int i = 0;
        while (i < pares.size() && i <= top) {
            System.out.println((i+1) + ". " + pares.get(i));
            i++;
        }
    }
    
}