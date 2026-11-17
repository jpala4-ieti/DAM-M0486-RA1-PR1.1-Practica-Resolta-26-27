package com.project;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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
        Path origen = Path.of(rutaOrigen);
        Path desti = Path.of(rutaDesti);

        // isRegularFile és fals tant si l'origen no existeix com si és una carpeta
        if (!Files.isRegularFile(origen)) {
            System.out.println("Error: l'arxiu d'origen no existeix o no és un fitxer: " + rutaOrigen);
            return;  // sortim abans de tocar el destí, així no es crea
        }
        if (Files.exists(desti)) {
            System.out.println("Advertència: l'arxiu de destinació ja existeix i serà sobreescrit.");
        }

        try {
            // Files.copy(origen, desti) faria la còpia en una línia, però l'enunciat demana fer-la línia a línia.
            // ponytail: es llegeix tot l'origen a memòria; per a fitxers molt grans, llegir i escriure per blocs
            String contingut = Files.readString(origen, StandardCharsets.UTF_8);
            // Límit -1: split conserva les línies buides, també la del final. Si l'origen acaba amb "\n",
            // l'últim element és "" i el destí també acabarà amb salt de línia; si no, tampoc
            String[] linies = contingut.split("\n", -1);

            try (BufferedWriter escriptor = Files.newBufferedWriter(desti, StandardCharsets.UTF_8)) {
                for (int i = 0; i < linies.length; i++) {
                    if (i > 0) {
                        escriptor.write("\n");  // el salt va ENTRE línies, mai després de l'última
                    }
                    escriptor.write(linies[i]);
                }
            }
            System.out.println("Còpia realitzada correctament.");
        } catch (IOException e) {
            System.out.println("Error: no s'ha pogut copiar l'arxiu: " + e.getMessage());
        }
    }
}
