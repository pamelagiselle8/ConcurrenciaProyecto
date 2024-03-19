## Instrucciones

# Descargar un dataset
El dataset con el cual estabamos probando el programa fue: https://www.kaggle.com/datasets/jerseyneo/reddit-adhd-dataset , dentro del zip de este utilizamos el ADHD-comment.csv

# Cambio de rutas
En main.java del preprocesamiento, cambiar el valor de rawDataset a la direccion donde este el dataset a usar, tambien cambiar finalDataset a la direccion a donde se quiere guardar el resultado, se puede cambiar el nombre dejandolo en txt para luego utilizarlo
En freqAnalyzer.java cambiar wordcount y wcountPares, a la direccion en donde este el output de wordcount y wordcountpares

# Correr Programa
Correr Main.java
Subir dataset a Hadoop con hadoop fs -put -f "Direccion donde esta el resultado del preprocesamiento" "Directorio de Hadoop en donde se va a guardar"
Correr el wordcounter con: javac -classpath ${HADOOP_CLASSPATH} -d 'Direccion de la carpeta de clases' 'Direccion de WordCount.java'
Luego hacer: jar -cvf wordcount.java -C clases/ , clases siendo la carpeta de clases
Finalmente hacer: hadoop jar 'Direccion del jar creado' WordCount 'Directorio donde esta guardado el dataset en hadoop' 'Directorio donde se guardara el output'
Repetir el proceso con el de pares
Descargar el output de Hadoop
Asegurarse de que las direcciones de las ubicaciones de ambos outputs este bien en freqAnalyzer.java
Correr freqAnalyzer.java
