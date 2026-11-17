package com.project;

import com.vdurmont.emoji.EmojiParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PR11_ampliacio {

    // Text amb àlies d'emoji (":nom:") que la llibreria emoji-java converteix en el caràcter Unicode
    private static final List<String> LINIES = List.of(
        "Hola, DAM! :wave:",
        "Avui toca llegir i escriure fitxers :open_file_folder: :pencil2:",
        "Si els tests passen: :white_check_mark: :tada:",
        "Si no passen: :x: :sob:",
        "I després, un cafè :coffee:"
    );

    public static void main(String[] args) {
        String camiFitxer = System.getProperty("user.dir") + "/data/emojis.txt";
        generarFitxerEmojis(camiFitxer);
    }

    // Escriu al fitxer, amb UTF-8, les línies amb els àlies substituïts per emojis
    public static void generarFitxerEmojis(String camiFitxer) {
        // parseToUnicode canvia cada ":àlies:" conegut pel seu emoji i deixa la resta del text igual
        String contingut = EmojiParser.parseToUnicode(String.join("\n", LINIES)) + "\n";

        try {
            // UTF-8 és imprescindible: els emojis no es poden representar en codificacions com ISO-8859-1
            Files.writeString(Path.of(camiFitxer), contingut, StandardCharsets.UTF_8);
            System.out.println("Fitxer generat: " + camiFitxer);
        } catch (IOException e) {
            System.out.println("No s'ha pogut escriure el fitxer: " + e.getMessage());
        }
    }
}
