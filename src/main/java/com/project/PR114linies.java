package com.project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PR114linies {

    private static final int QUANTITAT_NUMEROS = 10;
    private static final int LIMIT_SUPERIOR = 100;  // exclòs: els números van de 0 a 99

    public static void main(String[] args) {
        // Definir el camí del fitxer dins del directori "data"
        String camiFitxer = System.getProperty("user.dir") + "/data/numeros.txt";

        // Crida al mètode que genera i escriu els números aleatoris
        generarNumerosAleatoris(camiFitxer);
    }

    // Mètode per generar 10 números aleatoris i escriure'ls al fitxer
    public static void generarNumerosAleatoris(String camiFitxer) {
        Random generador = new Random();
        List<String> numeros = new ArrayList<>();
        for (int i = 0; i < QUANTITAT_NUMEROS; i++) {
            numeros.add(String.valueOf(generador.nextInt(LIMIT_SUPERIOR)));
        }
        // String.join posa el salt només ENTRE els números: el fitxer acaba amb l'últim dígit.
        // Files.write(path, llista) no serveix aquí: afegeix un salt després de cada línia,
        // també de l'última
        String contingut = String.join("\n", numeros);

        try {
            // Path.of va dins del try: una ruta no vàlida llença InvalidPathException
            Files.writeString(Path.of(camiFitxer), contingut, StandardCharsets.UTF_8);
        } catch (IOException | InvalidPathException e) {
            System.out.println("No s'ha pogut escriure el fitxer: " + e.getMessage());
        }
    }
}
