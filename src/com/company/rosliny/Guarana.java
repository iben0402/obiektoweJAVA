package com.company.rosliny;

import com.company.*;

import java.awt.*;

public class Guarana extends Roslina {
    private static final int GUARANA_ZWIEKSZENIE = 3;
    private static final int GUARANA_SILA = 0;
    private static final int GUARANA_INICJATYWA=0;

    public Guarana(Swiat swiat, Punkt pozycja, int turaUrodzenia)
    {
        super(Typ.GUARANA, swiat, pozycja, turaUrodzenia, GUARANA_SILA, GUARANA_INICJATYWA);
        setKolor(new Color(210, 116, 139));
    }

    @Override
    public String TypOrganizmuToString() {
        return "Guarana";
    }

    @Override
    public boolean DzialaniePodczasAtaku(Organizm napastnik, Organizm ofiara) {
        Punkt tmpPoz = this.getPozycja();
        getSwiat().UsunOrganizm(this);
        napastnik.WykonajRuch(tmpPoz);
        napastnik.setSila(napastnik.getSila() + GUARANA_ZWIEKSZENIE);
        Komentarze.DodajKomentarz(napastnik.OrganizmToString() + " zjada " + OrganizmToString() + " i zwieksza swoja sile na " + napastnik.getSila());
        return true;
    }
}
