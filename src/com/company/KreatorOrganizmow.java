package com.company;

import com.company.zwierzeta.*;

public class KreatorOrganizmow {

    public static Organizm StworzOrganizm (Organizm.Typ typ, Swiat swiat, Punkt pozycja)
    {
        return switch (typ) {
//            case WILK: return new Wilk(swiat, pozycja, swiat.getTura());
//            case LIS: return new Lis(swiat, pozycja, swiat.getTura());
//            case OWCA: return new Owca(swiat, pozycja, swiat.getTura());
//            case ZOLW: return new Zolw(swiat, pozycja, swiat.getTura());
//            case MLECZ: return new Mlecz(swiat, pozycja, swiat.getTura());
//            case TRAWA: return new Trawa(swiat, pozycja, swiat.getTura());
//            case GUARANA: return new Guarana(swiat, pozycja, swiat.getTura());
//            case ANTYLOPA: return new Antylopa(swiat, pozycja, swiat.getTura());
            case CZLOWIEK -> new Czlowiek(swiat, pozycja, swiat.getTura());
//            case WILCZE_JAGODY: return new WilczeJagody(swiat, pozycja, swiat.getTura());
//            case BARSZCZ_SOSNOWSKIEGO: return new BarszczSosnowskiego(swiat, pozycja, swiat.getTura());
            default -> null;
        };
    }
}
