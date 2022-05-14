package com.company.rosliny;

import com.company.Punkt;
import com.company.Roslina;
import com.company.Swiat;

import java.awt.*;

public class Trawa extends Roslina {
    private static final int TRAWA_SILA = 0;
    private static final int TRAWA_INICJATYWA = 0;

    public Trawa(Swiat swiat, Punkt pozycja, int turaUrodzenia)
    {
        super(Typ.TRAWA, swiat, pozycja, turaUrodzenia, TRAWA_SILA, TRAWA_INICJATYWA);
        setKolor(new Color(126, 213, 127));
    }

    @Override
    public String TypOrganizmuToString() {
        return "Trawa";
    }
}
