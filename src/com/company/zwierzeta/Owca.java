package com.company.zwierzeta;

import com.company.Punkt;
import com.company.Swiat;
import com.company.Zwierze;

import java.awt.*;

public class Owca extends Zwierze {
    private static final int OWCA_ZASIEG =1;
    private static final int OWCA_SZANSA =1;
    private static final int OWCA_SILA =4;
    private static final int OWCA_INICJATYWA =4;

    public Owca(Swiat swiat, Punkt pozycja, int turaUrodzenia)
    {
        super(Typ.OWCA, swiat, pozycja, turaUrodzenia, OWCA_SILA, OWCA_INICJATYWA);
        this.setZasieg(OWCA_ZASIEG);
        this.setSzansaNaRuch(OWCA_SZANSA);
        this.setKolor(new Color(103, 103, 103));
    }

    @Override
    public String TypOrganizmuToString() {
        return "Owca";
    }
}
