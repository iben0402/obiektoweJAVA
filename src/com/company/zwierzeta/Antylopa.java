package com.company.zwierzeta;

import com.company.*;

import java.awt.*;
import java.util.Random;

public class Antylopa extends Zwierze {
    private static final int ANTYLOPA_ZASIEG = 2;
    private static final int ANTYLOPA_SZANSA = 1;
    private static final int ANTYLOPA_SILA = 4;
    private static final int ANTYLOPA_INICJATYWA = 4;

    public Antylopa(Swiat swiat, Punkt pozycja, int turaUrodzenia)
    {
        super(Organizm.Typ.ANTYLOPA, swiat, pozycja, turaUrodzenia, ANTYLOPA_SILA, ANTYLOPA_INICJATYWA);
        this.setZasieg(ANTYLOPA_ZASIEG);
        this.setSzansaNaRuch(ANTYLOPA_SZANSA);
        setKolor(new Color(208, 180, 143));
    }

    @Override
    public String TypOrganizmuToString() {
        return "Antylopa";
    }

    @Override
    public boolean DzialaniePodczasAtaku(Organizm napastnik, Organizm ofiara) {
        Random rand = new Random();
        int tmpLos = rand.nextInt(100);
        if(tmpLos < 50)
        {
            if(this == napastnik)
            {
                Komentarze.DodajKomentarz(OrganizmToString() + " ucieka od " + ofiara.OrganizmToString());
                Punkt tmpPoz = LosujWolnePole(ofiara.getPozycja());
                if(!tmpPoz.equals(ofiara.getPozycja())) WykonajRuch(tmpPoz);
            }
            else if(this==ofiara)
            {
                Komentarze.DodajKomentarz(OrganizmToString() + " ucieka od " + napastnik.OrganizmToString());
                Punkt tmpPoz = this.getPozycja();
                WykonajRuch(LosujWolnePole(this.getPozycja()));
                if(getPozycja().equals(tmpPoz))
                {
                    getSwiat().UsunOrganizm(this);
                    Komentarze.DodajKomentarz(napastnik.OrganizmToString() + " zabija " + OrganizmToString());
                }
                napastnik.WykonajRuch(tmpPoz);
            }
            return true;
        }
        return false;
    }
}
