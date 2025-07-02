package com.playpenbank.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * PlayPenUtil is a utility class that provides random phrases related to the PlayPen Bank's
 * clients who are experiencing problems accessing their funds.
 * Keep our client's morale high with these optimistic messages! WE LOVE OUR CLIENTS!
 */
public class PlayPenUtil {

    private static final List<String> FRASES = Arrays.asList(
            "El que depositó dólares, recibirá dólares!",
            "Por su seguridad, su dinero está descansando.",
            "Tranquilo, aquí no hay crisis ni problemas",
            "Hemos tenido que adoptar una medida transitoria",
            "¡Aguante, ya falta menos!",
            "El 2001 será un gran año para todos!",
            "La situación del país comienza a mejorar",
            "Nos cagaron",
            "Estamos mal pero vamos bien",
            "En dos horas viajaremos a la estratósfera",
            "Argentina está condenada al éxito"
    );
    private static final Random RANDOM = new Random();

    public static String fraseRandom() {
        return FRASES.get(RANDOM.nextInt(FRASES.size()));
    }
}