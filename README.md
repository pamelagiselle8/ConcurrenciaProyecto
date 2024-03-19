# Instrucciones

## Descargar un dataset
El dataset con el cual estabamos probando el programa fue: https://www.kaggle.com/datasets/jerseyneo/reddit-adhd-dataset , dentro del zip de este utilizamos el ADHD-comment.csv

## Cambio de rutas
En main.java del preprocesamiento, cambiar el valor de rawDataset a la direccion donde este el dataset a usar, tambien cambiar finalDataset a la direccion a donde se quiere guardar el resultado, se puede cambiar el nombre dejandolo en txt para luego utilizarlo

En freqAnalyzer.java cambiar wordcount y wcountPares, a la direccion en donde este el output de wordcount y wordcountpares

## Correr Programa
1. Inciar Hadoop

2. Correr Main.java

3. Subir dataset a Hadoop con hadoop fs -put -f "Direccion donde esta el resultado del preprocesamiento" "Directorio de Hadoop en donde se va a guardar"

4. Correr el wordcounter con: javac -classpath ${HADOOP_CLASSPATH} -d 'Direccion de la carpeta de clases' 'Direccion de WordCount.java'

5. Luego hacer: jar -cvf wordcount.java -C clases/ , clases siendo la carpeta de clases

6. Finalmente hacer: hadoop jar 'Direccion del jar creado' WordCount 'Directorio donde esta guardado el dataset en hadoop' 'Directorio donde se guardara el output'

7. Repetir el proceso con el de pares

8. Descargar el output de Hadoop

9. Asegurarse de que las direcciones de las ubicaciones de ambos outputs este bien en freqAnalyzer.java

10. Correr freqAnalyzer.java
