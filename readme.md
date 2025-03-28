# 📚 Library Manager System (LMS)

LMS es un sistema de gestión de bibliotecas que busca mejorar la eficiencia y control de los libros. Permite gestionar los préstamos a usuarios, controlando la entrega y devolución de los mismos.

Este proyecto es una aplicación de escritorio desarrollada en **Java 21** utilizando **JavaFX** para la interfaz gráfica y **PostgreSQL 15** como base de datos. Implementa el patrón de diseño **Service** y utiliza **JPA con EclipseLink** para la persistencia de datos.

## 🚀 Entorno de Desarrollo

- **Java**: versión 21
    
- **JavaFX**: para la construcción de la interfaz de usuario
    
- **PostgreSQL**: versión 15, base de datos relacional
    
- **JPA + EclipseLink**: para la gestión de la persistencia de datos
    

## 📌 Tareas del Proyecto

- Diseño del **diagrama de clases**
    
- Creación del **diseño de pantallas** en JavaFX
    
- Implementación del **modelo de clases en Java**
    
- Construcción de la **interfaz gráfica** en JavaFX
    
- Conexión con **PostgreSQL** mediante JPA y EclipseLink
    
- Aplicación del **patrón de diseño Service**
    

## ⚡ Funcionalidades

El sistema permite la gestión de una biblioteca con las siguientes características:

- 📖 **Gestión de libros**: Alta, baja, modificación y consulta de libros
    
- 🔄 **Gestión de préstamos**: Registro y control de préstamos de libros
    
- 📚 **Gestión de copias de libros**: Administración de ejemplares de un mismo libro
    
- 👤 **Gestión de usuarios**: Administración de usuarios que pueden solicitar préstamos
    
- 📦 **Gestión de racks**: Organización de libros en estanterías
    
- 💰 **Gestión de multas**: Control de sanciones por devolución tardía
    

## 🛠️ Instalación y Configuración

1. **Clonar el repositorio:**
    
    ```bash
    git clone https://github.com/tu-usuario/tu-repositorio.git
    cd tu-repositorio
    ```
    
2. **Configurar la base de datos en PostgreSQL:**
    
    - Crear una base de datos llamada `biblioteca`.
        
    - Configurar las credenciales en el archivo de configuración del proyecto.
        
3. **Compilar y ejecutar el proyecto:**
    
    - Asegurarse de tener Java 21 y JavaFX configurados.
        
    - Ejecutar la aplicación desde el IDE o con Maven/Gradle.
        

## 📄 Licencia

Este proyecto está bajo la licencia MIT.
