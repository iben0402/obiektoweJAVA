package com.company;

public class Komentarze {

    private static String tekst = "";

    public static void DodajKomentarz(String komentarz) {tekst += komentarz + "\n";}

    public static String getTekst() {
        return tekst;
    }

    public static void WyczyscKomentarz() {tekst="";}
}
