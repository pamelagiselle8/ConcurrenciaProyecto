# Instrucciones

> Nota sobre rutas absolutas: Debido a que algunos de los archivos utilizados en el proyecto eran muy pesados para ser subidos al repositorio, utilizamos rutas absolutas en los siguientes programas: Main.java, freqAnalyzer.java

## Descargar el Dataset
El dataset utilizado en este proyecto:

https://www.kaggle.com/datasets/jerseyneo/reddit-adhd-dataset


Descomprimimos el archivo zip y de los archivos csv, utilizamos el archivo ADHD-comment.csv

> Main.java : Actualizar la ruta al dataset a preprocesar (en este caso ruta absoluta del archivo ADHD-comment.csv que descargamos), y la ruta al directorio donde se guardará el dataset preprocesado (las rutas se almacenan en las variables rawDataset y finalDataset, respectivamente).

## Procedimiento Preprocesamiento
> /ConcurrenciaProyecto/Preprocesamiento/src/preprocesamiento/Main.java
  
1. Correr programa de Preprocesamiento (podemos correrlo en NetBeans), luego de haber actualizado las rutas necesarias.
   
3. Abrimos una ventana de la terminal e inciamos Hadoop con los siguientes comandos:
```
   cd /ruta_a_hadoop
   start-all.sh
```

3. Creamos una carpeta llamada Dataset donde subiremos el dataset preprocesado:
```
  hadoop fs -mkdir /Dataset
  hadoop fs -put /ruta_a_dataset_preprocesado/DatasetFinal.txt /Dataset
```

## Preparar terminal para correr los programas de WordCount
1. Abrimos una nueva ventana de la terminal donde iremos al directorio que contiene la carpeta WordCount y correremos los siguientes comandos:
```
  cd /ruta_al_proyecto/ConcurrenciaProyecto/WordCount
  export HADOOP_CLASSPATH=$(hadoop classpath)
```
Para verificar que se hizo exitosamente, corremos:
  `echo $HADOOP_CLASSPATH`

2. Creamos dos carpetas donde se guardarán los outputs de los WordCounters:
```
  hadoop fs -mkdir /Output
  hadoop fs -mkdir /OutputPares
```

> Nota: Necesitaremos compilar los programas de WordCount con la versión 8 de Java.

## Procedimiento WordCount (palabras individuales)
> /ProyectoConcurrencia/WordCounter/WordCount.java

1. Compilar WordCount.java con el siguiente comando:
```
  /ruta_a_java_version_8/javac -classpath ${HADOOP_CLASSPATH} -d '/ruta_al_proyecto/ConcurrenciaProyecto/WordCounter/clases' '/ruta_al_proyecto/ConcurrenciaProyecto/WordCounter/WordCount.java'
```

2. Juntar el output en un solo archivo jar:
  `jar -cvf wordcount.jar -C clases/ .`

3. Correr Word Counter
  `hadoop jar /ruta_al_proyecto/ConcurrenciaProyecto/WordCounter/wordcount.jar WordCount /ProyectoConcu/Dataset /Output`

## Procedimiento WordCountPares (pares de palabras)
> /ProyectoConcurrencia/WordCounter/WordCount.java

Repetimos el procedimiento, esta vez para el WordCount de pares de palabras:
```
  /ruta_a_java_version_8/javac -classpath ${HADOOP_CLASSPATH} -d '/ruta_al_proyecto/ConcurrenciaProyecto/WordCounter/clasesPares' '/ruta_al_proyecto/ConcurrenciaProyecto/WordCounter/WordCountPares.java'
  jar -cvf wordcountpares.jar -C clasesPares/ .
  hadoop jar /ruta_al_proyecto/ConcurrenciaProyecto/WordCounter/wordcountpares.jar WordCountPares /ProyectoConcu/Dataset /OutputPares
```

## Descargamos ambos outputs de los WordCounters desde Hadoop (se encuentran en las carpetas Output y OutputPares)

## Procedimiento Frequency Analyzer
> /ConcurrenciaProyecto/Preprocesamiento/src/preprocesamiento/freqAnalyzer.java
> freqAnalyzer.java : Actualizar la ruta a los outputs que acabamos de descargar (las rutas se almacenan en las variables wordcount y wordcountPares, respectivamente).

1. En la clase freqAnalyzer.java existen dos métodos: uno que genera el top de las palabras individuales más frecuentes y el otro genera el top para los pares de palabras más frecuentes. Se recomiendo descomentar en el main el llamado al método que deseemos usar y comentar el otro para mejor visibilidad en la salida.

2. Corremos freqAnalyzer.java (puede ser en NetBeans)


