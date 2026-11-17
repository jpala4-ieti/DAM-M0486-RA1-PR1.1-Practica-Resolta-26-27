package com.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class PR115cp {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Error: Has d'indicar dues rutes d'arxiu.");
            System.out.println("Ús: PR115cp <origen> <destinació>");
            return;
        }

        // Ruta de l'arxiu origen
        String rutaOrigen = args[0];
        // Ruta de l'arxiu destinació
        String rutaDesti = args[1];

        // Crida al mètode per copiar l'arxiu
        copiarArxiu(rutaOrigen, rutaDesti);
    }

    // Mètode per copiar un arxiu de text de l'origen al destí
    public static void copiarArxiu(String rutaOrigen, String rutaDesti) {
        try {
            // Path.of va dins del try: una ruta no vàlida llença InvalidPathException
            Path origen = Path.of(rutaOrigen);
            Path desti = Path.of(rutaDesti);

            // isRegularFile és fals tant si l'origen no existeix com si és una carpeta
            if (!Files.isRegularFile(origen)) {
                System.out.println("Error: l'origen no existeix o no és un fitxer: " + rutaOrigen);
                return;  // sortim abans de tocar el destí, així no es crea
            }
            if (Files.exists(desti)) {
                // Obrir el destí per escriure'l el buida: si fos el mateix fitxer que l'origen,
                // se'n perdria el contingut abans de llegir-lo
                if (Files.isSameFile(origen, desti)) {
                    System.out.println("Error: l'origen i la destinació són el mateix arxiu.");
                    return;
                }
                System.out.println("Advertència: l'arxiu de destinació ja existeix"
                        + " i serà sobreescrit.");
            }

            // Files.copy(origen, desti) faria la còpia en una línia, però l'enunciat demana
            // fer-la línia a línia
            try (BufferedReader lector =
                         Files.newBufferedReader(origen, StandardCharsets.UTF_8);
                 BufferedWriter escriptor =
                         Files.newBufferedWriter(desti, StandardCharsets.UTF_8)) {
                // No es fa servir readLine() perquè descarta els salts de línia i després no es
                // pot saber si l'origen acabava amb salt o no. Es llegeix caràcter a caràcter i
                // cada línia es guarda AMB el seu salt, de manera que la còpia és exacta
                StringBuilder linia = new StringBuilder();
                int caracter;
                while ((caracter = lector.read()) != -1) {
                    linia.append((char) caracter);
                    if (caracter == '\n') {
                        escriptor.write(linia.toString());
                        linia.setLength(0);  // buidar per començar la línia següent
                    }
                }
                // Última línia, si l'origen no acaba amb salt (si hi acaba, és buida)
                escriptor.write(linia.toString());
            }
            System.out.println("Còpia realitzada correctament.");
        } catch (IOException | InvalidPathException e) {
            System.out.println("Error: no s'ha pogut copiar l'arxiu: " + e.getMessage());
        }
    }
}
