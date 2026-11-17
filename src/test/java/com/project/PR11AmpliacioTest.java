package com.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class PR11AmpliacioTest {

    @TempDir
    Path directoriTemporal;

    @Test
    void testGenerarFitxerEmojis() throws IOException {
        Path fitxer = directoriTemporal.resolve("emojis.txt");

        PR11_ampliacio.generarFitxerEmojis(fitxer.toString());

        // Si el fitxer no fos UTF-8 vàlid, readString llençaria MalformedInputException
        String contingut = Files.readString(fitxer, StandardCharsets.UTF_8);
        assertTrue(contingut.contains("☕"), "L'àlies :coffee: s'hauria d'haver convertit en l'emoji.");
        assertFalse(contingut.matches("(?s).*:[a-z0-9_]+:.*"), "No hauria de quedar cap àlies sense convertir.");
        assertEquals(5, contingut.lines().count(), "El fitxer hauria de tenir 5 línies.");
    }
}
