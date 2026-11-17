package com.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class PR112cat {

    public static void main(String[] args) {
        // Comprovar que s'ha proporcionat una ruta com a paràmetre
        if (args.length == 0) {
            System.out.println("No s'ha proporcionat cap ruta d'arxiu.");
            return;
        }

        // Obtenir la ruta del fitxer des dels paràmetres
        String rutaArxiu = args[0];
        mostrarContingutArxiu(rutaArxiu);
    }

    // Funció per mostrar el contingut de l'arxiu o el missatge d'error corresponent
    public static void mostrarContingutArxiu(String rutaArxiu) {
        File arxiu = new File(rutaArxiu);
        if (arxiu.isDirectory()) {
            System.out.println("El path no correspon a un arxiu, sinó a una carpeta.");
            return;
        }

        // No cal comprovar abans si existeix: si no hi és, FileReader llença
        // FileNotFoundException, que és una IOException com qualsevol altre error de lectura
        try (BufferedReader lector = new BufferedReader(
                new FileReader(arxiu, StandardCharsets.UTF_8))) {
            // Primer es llegeix tot el fitxer i després es mostra: si la lectura falla a mitges,
            // només surt el missatge d'error i no un tros del contingut
            StringWriter contingut = new StringWriter();
            lector.transferTo(contingut);
            // print i no println: com la comanda cat, no s'hi afegeix cap salt de línia
            System.out.print(contingut);
        } catch (IOException e) {
            System.out.println("El fitxer no existeix o no és accessible.");
        }
    }
}
