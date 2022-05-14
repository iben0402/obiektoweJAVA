package com.company;

import com.company.zwierzeta.*;
import com.company.rosliny.*;

public class KreatorOrganizmow {

    public static Organizm StworzOrganizm (Organizm.Typ typ, Swiat swiat, Punkt pozycja)
    {
        return switch (typ) {
            case WILK -> new Wilk(swiat, pozycja, swiat.getTura());
            case LIS -> new Lis(swiat, pozycja, swiat.getTura());
            case OWCA -> new Owca(swiat, pozycja, swiat.getTura());
            case ZOLW -> new Zolw(swiat, pozycja, swiat.getTura());
            case MLECZ -> new Mlecz(swiat, pozycja, swiat.getTura());
            case TRAWA -> new Trawa(swiat, pozycja, swiat.getTura());
            case GUARANA -> new Guarana(swiat, pozycja, swiat.getTura());
            case ANTYLOPA -> new Antylopa(swiat, pozycja, swiat.getTura());
            case CZLOWIEK -> new Czlowiek(swiat, pozycja, swiat.getTura());
            case WILCZE_JAGODY -> new WilczeJagody(swiat, pozycja, swiat.getTura());
            case BARSZCZ_SOSNOWSKIEGO -> new BarszczSosnowskiego(swiat, pozycja, swiat.getTura());
        };
    }
}
