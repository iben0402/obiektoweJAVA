package com.company.rosliny;

import com.company.*;

import java.awt.*;
import java.util.Random;

public class WilczeJagody extends Roslina {
    private static final int JAGODY_SILA = 99;
    private static final int JAGODY_INICJATYWA =0;

    public WilczeJagody(Swiat swiat, Punkt pozycja, int turaUrodzenia)
    {
        super(Typ.WILCZE_JAGODY, swiat, pozycja, turaUrodzenia, JAGODY_SILA, JAGODY_INICJATYWA);
        setKolor(new Color(121, 63, 178));
        setSzansaRozmnazania(0.1);
    }

    @Override
    public String TypOrganizmuToString() {
        return "Wilcze Jagody";
    }

    @Override
    public boolean DzialaniePodczasAtaku(Organizm napastnik, Organizm ofiara) {
        Komentarze.DodajKomentarz(napastnik.OrganizmToString() + " zjada " + OrganizmToString());
        if(napastnik.getSila() >= JAGODY_SILA)
        {
            getSwiat().UsunOrganizm(this);
            Komentarze.DodajKomentarz(napastnik.OrganizmToString() + " niszczy krzak wilczej jagody");
        }
        if(napastnik.CzyJestZwierzeciem())
        {
            getSwiat().UsunOrganizm(napastnik);
            Komentarze.DodajKomentarz(napastnik.OrganizmToString() + " ginie od wilczej jagody");
        }
        return true;
    }
}
