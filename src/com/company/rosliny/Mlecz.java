package com.company.rosliny;

import com.company.Punkt;
import com.company.Roslina;
import com.company.Swiat;

import java.awt.*;
import java.util.Random;

public class Mlecz extends Roslina {
    private static final int MLECZ_SILA = 0;
    private static final int MLECZ_INICJATYWA = 0;
    private static final int PROBY = 3;

    public Mlecz(Swiat swiat, Punkt pozycja, int turaUrodzenia)
    {
        super(Typ.MLECZ, swiat, pozycja, turaUrodzenia, MLECZ_SILA, MLECZ_INICJATYWA);
        setKolor(new Color(232, 226, 130));
        setSzansaRozmnazania(0.1);
    }

    @Override
    public void Akcja() {
        Random rand = new Random();
        for (int i = 0; i < PROBY; i++) {
            int tmpLos = rand.nextInt(100);
            if(tmpLos < getSzansaRozmnazania() * 100) Rozprzestrzenianie();
        }
    }

    @Override
    public String TypOrganizmuToString() {
        return "Mlecz";
    }
}
