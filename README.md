# EJ26_OLC1_P1_202031046

Proyecto 1 - Organización de Lenguajes y Compiladores 1

## Información del estudiante

* Nombre: Ronald Samayoa
* Carné: 202031046/3339860241301

## Descripción

Aplicación desarrollada en Java utilizando Swing para la interfaz gráfica.

El proyecto implementa análisis léxico y sintáctico mediante JFlex y Java CUP, integrados mediante Maven.

## Estructura de la entrega

Al descomprimir el archivo entregado se obtiene una carpeta principal que contiene el proyecto:

```text
Compi1_EDVJ26/
└── EJ26_OLC1_P1_202031046/
    ├── pom.xml
    ├── src/
    └── README.md
```

Las instrucciones de compilación y ejecución deben realizarse dentro de la carpeta:

```text
EJ26_OLC1_P1_202031046
```

## Requisitos

* Java JDK 24 o superior
* Apache Maven 3.9 o superior

Verificar instalación:

```bash
java -version
mvn -version
```

## Compilación

1. Abrir una terminal.
2. Ingresar a la carpeta raíz del proyecto:

```bash
cd Compi1_EDVJ26/EJ26_OLC1_P1_202031046
```

3. Ejecutar:

```bash
mvn clean package
```

Este comando genera automáticamente:

* Analizador léxico (JFlex)
* Analizador sintáctico (Java CUP)
* Compilación de clases Java
* Archivo JAR ejecutable

Al finalizar, Maven creará la carpeta:

```text
target/
```

y dentro de ella el archivo:

```text
EJ26_OLC1_P1_202031046-1.0-SNAPSHOT.jar
```

## Ejecución

Desde la carpeta raíz del proyecto:

```bash
java -jar target/EJ26_OLC1_P1_202031046-1.0-SNAPSHOT.jar
```

Alternativamente:

```bash
mvn exec:java -Dexec.mainClass="com.olc1.golite.Main"
```

## Dependencias

Las dependencias son descargadas automáticamente por Maven durante la compilación, por lo que no es necesario instalar librerías adicionales.

## Nota

La entrega no incluye archivos compilados ni la carpeta `target`.

Para generar el proyecto completo únicamente es necesario ejecutar:

```bash
mvn clean package
```

desde la carpeta `EJ26_OLC1_P1_202031046`.
