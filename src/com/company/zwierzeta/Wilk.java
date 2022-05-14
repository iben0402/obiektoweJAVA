package com.company.zwierzeta;

import com.company.Punkt;
import com.company.Swiat;
import com.company.Zwierze;

import java.awt.*;

public class Wilk extends Zwierze {
    private static final int WILK_ZASIEG =  1;
    private static final int WILK_SZANSA =  1;
    private static final int WILK_SILA =  9;
    private static final int WILK_INICJATYWA =  5;

    public Wilk(Swiat swiat, Punkt pozycja, int turaUrodzenia)
    {
        super(Typ.WILK, swiat, pozycja, turaUrodzenia ,WILK_SILA, WILK_INICJATYWA);
        this.setZasieg(WILK_ZASIEG);
        this.setSzansaNaRuch(WILK_SZANSA);
        setKolor(new Color(49, 32, 32));
    }

    @Override
    public String TypOrganizmuToString() {
        return "Wilk";
    }
}
