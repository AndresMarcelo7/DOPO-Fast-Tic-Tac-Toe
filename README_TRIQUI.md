# Juego de Triqui (Tic-Tac-Toe) - Patrón MVC

## Mini Proyecto de Programación Orientada a Objetos

Este es un mini proyecto que demuestra el **patrón de diseño MVC (Modelo-Vista-Controlador)** usando un juego simple de Triqui.

---

## Ejecucion Codigo 

### Con Java
```bash
# Compilar y ejecutar (generar archivos .class)
javac -d . src/main/java/org/example/**/*.java && java org.example.Main

# Ejecutar (usando archivos .class)
java -cp out/production/classes org.example.Main
```

### Archivo jar

#### Crear archivo jar con java (despues de compilar)
```bash
# Crear archivo jar
jar cvfe Triqui.jar org.example.Main -C out/production/classes .
```

#### Ejecutar archivo jar con java
```bash
# Ejecutar archivo jar
java -jar Triqui.jar
```




