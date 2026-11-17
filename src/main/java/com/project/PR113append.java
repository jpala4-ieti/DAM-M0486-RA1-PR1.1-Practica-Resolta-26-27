package com.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PR113append {

    private static final String[] FRASES = {
        "I can only show you the door",
        "You're the one that has to walk through it"
    };

    public static void main(String[] args) {
        // Definir el camí del fitxer dins del directori "data"
        String camiFitxer = System.getProperty("user.dir") + "/data/frasesMatrix.txt";

        // Crida al mètode que afegeix les frases al fitxer
        afegirFrases(camiFitxer);
    }

    // Mètode que afegeix les frases al final del fitxer amb UTF-8; cada línia acaba amb un salt de línia
    public static void afegirFrases(String camiFitxer) {
        // El tercer paràmetre (append = true) fa que s'escrigui al final, sense esborrar el que ja hi havia;
        // si el fitxer no existeix, el crea
        try (BufferedWriter escriptor = new BufferedWriter(new FileWriter(camiFitxer, StandardCharsets.UTF_8, true))) {
            for (String frase : FRASES) {
                escriptor.write(frase);
                // "\n" i no newLine(): l'enunciat demana "\n" exacte, i newLine() escriu "\r\n" a Windows
                escriptor.write("\n");
            }
        } catch (IOException e) {
            System.out.println("No s'ha pogut escriure el fitxer: " + e.getMessage());
        }
    }
}
