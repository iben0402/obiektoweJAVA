package com.company.zwierzeta;

import com.company.*;

import java.awt.*;

public class Zolw extends Zwierze {
    private static final int ZOLW_ZASIEG = 1;
    private static final double ZOLW_SZANSA = 0.25;
    private static final int ZOLW_SILA = 2;
    private static final int ZOLW_INICJATYWA = 1;

    public Zolw(Swiat swiat, Punkt pozycja, int turaUrodzenia)
    {
        super(Typ.ZOLW, swiat, pozycja, turaUrodzenia, ZOLW_SILA, ZOLW_INICJATYWA);
        this.setSzansaNaRuch(ZOLW_SZANSA);
        this.setZasieg(ZOLW_ZASIEG);
        this.setKolor(new Color(9, 58, 9));
    }

    @Override
    public String TypOrganizmuToString() {
        return "Zolw";
    }

    @Override
    public boolean DzialaniePodczasAtaku(Organizm napastnik, Organizm ofiara) {
        if(this == ofiara)
        {
            if(napastnik.getSila() < 5 && napastnik.CzyJestZwierzeciem())
            {
                Komentarze.DodajKomentarz(OrganizmToString() + " odpiera atak " + napastnik.OrganizmToString());
                return true;
            }
            else return false;
        }
        else
        {
            if(napastnik.getSila() >= ofiara.getSila()) return false;
            else
            {
                if(ofiara.getSila() < 5 && ofiara.CzyJestZwierzeciem())
                {
                    Komentarze.DodajKomentarz(OrganizmToString() + " odpiera atak " + ofiara.OrganizmToString());
                    return true;
                }
                else return false;
            }
        }
    }
}
