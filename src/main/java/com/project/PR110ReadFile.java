package com.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PR110ReadFile {

    public static void main(String[] args) {
        String camiFitxer = System.getProperty("user.dir") + "/data/GestioTasques.java";
        llegirIMostrarFitxer(camiFitxer);  // Només cridem a la funció amb la ruta del fitxer
    }

    // Funció que llegeix el fitxer i mostra les línies amb numeració
    public static void llegirIMostrarFitxer(String camiFitxer) {
        // try-with-resources: el lector es tanca sol, tant si tot va bé com si hi ha un error
        try (BufferedReader lector = new BufferedReader(new FileReader(camiFitxer, StandardCharsets.UTF_8))) {
            String linia;
            int numeroLinia = 1;
            // readLine() retorna null quan s'arriba al final del fitxer
            while ((linia = lector.readLine()) != null) {
                System.out.println(numeroLinia + ": " + linia);
                numeroLinia++;
            }
        } catch (IOException e) {
            // També recull FileNotFoundException (és una IOException) si el fitxer no existeix
            System.out.println("No s'ha pogut llegir el fitxer: " + e.getMessage());
        }
    }
}
