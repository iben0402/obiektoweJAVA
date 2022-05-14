package com.company.zwierzeta;

import com.company.Organizm;
import com.company.Punkt;
import com.company.Swiat;
import com.company.Zwierze;

import java.awt.*;
import java.util.Random;

public class Lis extends Zwierze {
    private static final int LIS_ZASIEG = 1;
    private static final int LIS_SILA = 3;
    private static final int LIS_SZANSA = 1;
    private static final int LIS_INICJATYWA = 7;

    public Lis(Swiat swiat, Punkt pozycja, int turaUrodzenia)
    {
        super(Typ.LIS, swiat, pozycja, turaUrodzenia, LIS_SILA, LIS_INICJATYWA);
        this.setSzansaNaRuch(LIS_SZANSA);
        this.setZasieg(LIS_ZASIEG);
        setKolor(new Color(203, 122, 59));
    }

    @Override
    public String TypOrganizmuToString() {
        return "Lis";
    }

    @Override
    public Punkt LosujDowolnePole(Punkt pozycja) {
        OdblokujWszystkieKierunki();
        int pozX = pozycja.getX();
        int pozY = pozycja.getY();
        int sizeX = getSwiat().getSizeX();
        int sizeY = getSwiat().getSizeY();
        int kierunki = 0;
        Organizm tmpOrg;

        if(pozX ==0) ZablokujKierunek(Kierunek.LEWO);
        else
        {
            tmpOrg = getSwiat().getPlansza()[pozY][pozX-1];
            if(tmpOrg != null && tmpOrg.getSila() > this.getSila()) ZablokujKierunek(Kierunek.LEWO);
            else kierunki++;
        }

        if(pozX ==sizeX-1) ZablokujKierunek(Kierunek.PRAWO);
        else
        {
            tmpOrg = getSwiat().getPlansza()[pozY][pozX+1];
            if(tmpOrg != null && tmpOrg.getSila() > this.getSila()) ZablokujKierunek(Kierunek.PRAWO);
            else kierunki++;
        }

        if(pozY ==0) ZablokujKierunek(Kierunek.GORA);
        else
        {
            tmpOrg = getSwiat().getPlansza()[pozY-1][pozX];
            if(tmpOrg != null && tmpOrg.getSila() > this.getSila()) ZablokujKierunek(Kierunek.GORA);
            else kierunki++;
        }

        if(pozY ==sizeY-1) ZablokujKierunek(Kierunek.DOL);
        else
        {
            tmpOrg = getSwiat().getPlansza()[pozY+1][pozX];
            if(tmpOrg != null && tmpOrg.getSila() > this.getSila()) ZablokujKierunek(Kierunek.DOL);
            else kierunki++;
        }

        if(kierunki==0) return pozycja;
        while(true)
        {
            Random rand = new Random();
            int tmpLos = rand.nextInt(100);
            if(tmpLos <25 && !CzyKierunekZablokowany(Kierunek.LEWO)) return new Punkt(pozX-1, pozY);
            //PRAWO
            else if(tmpLos <50 && !CzyKierunekZablokowany(Kierunek.PRAWO)) return new Punkt(pozX+1, pozY);
            //GORA
            else if(tmpLos <75 && !CzyKierunekZablokowany(Kierunek.GORA)) return new Punkt(pozX, pozY-1);
            //DOL
            else if(tmpLos <100 && !CzyKierunekZablokowany(Kierunek.DOL)) return new Punkt(pozX, pozY+1);
        }
    }
}
