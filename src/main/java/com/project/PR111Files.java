package com.project;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class PR111Files {

    public static void main(String[] args) {
        String camiDirectori = System.getProperty("user.dir") + "/data/pr111";
        gestionarArxius(camiDirectori);
    }

    // Rep la ruta del directori on cal crear la carpeta myFiles
    public static void gestionarArxius(String camiDirectori) {
        Path carpeta = Path.of(camiDirectori, "myFiles");
        Path fitxer1 = carpeta.resolve("file1.txt");
        Path fitxer2 = carpeta.resolve("file2.txt");
        Path fitxerReanomenat = carpeta.resolve("renamedFile.txt");

        try {
            // createDirectories també crea les carpetes intermèdies i no falla si ja existeixen
            Files.createDirectories(carpeta);
            crearFitxerBuit(fitxer1);
            crearFitxerBuit(fitxer2);
            mostrarLlistat(carpeta);

            // REPLACE_EXISTING: en una segona execució renamedFile.txt ja existeix i move() fallaria
            Files.move(fitxer2, fitxerReanomenat, StandardCopyOption.REPLACE_EXISTING);
            Files.delete(fitxer1);
            mostrarLlistat(carpeta);
        } catch (IOException e) {
            System.out.println("Error gestionant els arxius: " + e.getMessage());
        }
    }

    // Crea el fitxer buit; si ja existeix (execució anterior interrompuda) el deixa com està
    private static void crearFitxerBuit(Path fitxer) throws IOException {
        if (Files.notExists(fitxer)) {
            Files.createFile(fitxer);
        }
    }

    // Mostra el nom de cada arxiu de la carpeta
    private static void mostrarLlistat(Path carpeta) throws IOException {
        System.out.println("Els arxius de la carpeta són:");
        // El DirectoryStream s'ha de tancar: per això va dins d'un try-with-resources
        try (DirectoryStream<Path> arxius = Files.newDirectoryStream(carpeta)) {
            for (Path arxiu : arxius) {
                System.out.println(arxiu.getFileName());
            }
        }
    }
}
