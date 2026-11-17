package com.project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

public class PR111Files {

    public static void main(String[] args) {
        String camiDirectori = System.getProperty("user.dir") + "/data/pr111";
        gestionarArxius(camiDirectori);
    }

    // Rep la ruta del directori on cal crear la carpeta myFiles
    public static void gestionarArxius(String camiDirectori) {
        try {
            // Path.of va dins del try: una ruta no vàlida llença InvalidPathException
            Path carpeta = Path.of(camiDirectori, "myFiles");
            Path fitxer1 = carpeta.resolve("file1.txt");
            Path fitxer2 = carpeta.resolve("file2.txt");
            Path fitxerReanomenat = carpeta.resolve("renamedFile.txt");

            // createDirectories també crea les carpetes intermèdies i no falla si ja existeixen
            Files.createDirectories(carpeta);
            for (Path fitxer : List.of(fitxer1, fitxer2)) {
                // createFile falla si el fitxer ja existeix (execució anterior interrompuda)
                if (Files.notExists(fitxer)) {
                    Files.createFile(fitxer);
                }
            }
            mostrarLlistat(carpeta);

            // REPLACE_EXISTING: en una segona execució renamedFile.txt ja existeix
            Files.move(fitxer2, fitxerReanomenat, StandardCopyOption.REPLACE_EXISTING);
            Files.delete(fitxer1);
            mostrarLlistat(carpeta);
        } catch (IOException | InvalidPathException e) {
            System.out.println("Error gestionant els arxius: " + e.getMessage());
        }
    }

    // Mostra, per ordre alfabètic, el nom de cada arxiu de la carpeta
    private static void mostrarLlistat(Path carpeta) {
        System.out.println("Els arxius de la carpeta són:");
        // El Stream de Files.list s'ha de tancar: per això va dins d'un try-with-resources
        try (Stream<Path> arxius = Files.list(carpeta)) {
            arxius.map(Path::getFileName).sorted().forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("No s'ha pogut llistar la carpeta: " + e.getMessage());
        }
    }
}
