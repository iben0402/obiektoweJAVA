package com.company.rosliny;

import com.company.*;

import java.awt.*;
import java.util.Random;

public class BarszczSosnowskiego extends Roslina {
    private final static int BARSZCZ_SILA = 10;
    private final static int BARSZCZ_INICJATYWA = 0;

    public BarszczSosnowskiego(Swiat swiat, Punkt pozycja, int turaUrodzenia)
    {
        super(Typ.BARSZCZ_SOSNOWSKIEGO, swiat,pozycja, turaUrodzenia, BARSZCZ_SILA, BARSZCZ_INICJATYWA);
        setKolor(new Color(82, 94, 43));
        setSzansaRozmnazania(0.05);
    }

    @Override
    public void Akcja() {
        int pozX = getPozycja().getX();
        int pozY = getPozycja().getY();
        LosujDowolnePole(getPozycja());

        for (int i = 0; i < 4; i++) {
            Organizm tmpOrg = null;
            if(i==0 && !CzyKierunekZablokowany(Kierunek.LEWO))
                tmpOrg = getSwiat().CoZnajdujeSieNaPolu(new Punkt(pozX -1, pozY));
            else if(i==1 && !CzyKierunekZablokowany(Kierunek.PRAWO))
                tmpOrg = getSwiat().CoZnajdujeSieNaPolu(new Punkt(pozX +1, pozY));
            else if(i==2 && !CzyKierunekZablokowany(Kierunek.GORA))
                tmpOrg = getSwiat().CoZnajdujeSieNaPolu(new Punkt(pozX, pozY-1));
            else if(i==3 && !CzyKierunekZablokowany(Kierunek.DOL))
                tmpOrg = getSwiat().CoZnajdujeSieNaPolu(new Punkt(pozX, pozY+1));

            if(tmpOrg != null && tmpOrg.CzyJestZwierzeciem())
            {
                getSwiat().UsunOrganizm(tmpOrg);
                Komentarze.DodajKomentarz(OrganizmToString() + " zabija " + tmpOrg.OrganizmToString());
            }
        }

        Random rand = new Random();
        int tmpLos = rand.nextInt(100);
        if (tmpLos < getSzansaRozmnazania() * 100) Rozprzestrzenianie();
    }

    @Override
    public String TypOrganizmuToString() {
        return "Barszcz Sosnowskiego";
    }

    @Override
    public boolean DzialaniePodczasAtaku(Organizm napastnik, Organizm ofiara) {
        if(napastnik.getSila()>=BARSZCZ_SILA)
        {
            getSwiat().UsunOrganizm(this);
            Komentarze.DodajKomentarz(napastnik.OrganizmToString() + " zjada " + this.OrganizmToString());
            ofiara.WykonajRuch(ofiara.getPozycja());
        }
        if(napastnik.CzyJestZwierzeciem() || napastnik.getSila() <10)
        {
            getSwiat().UsunOrganizm(napastnik);
            Komentarze.DodajKomentarz(this.OrganizmToString() + " zabija " + napastnik.OrganizmToString());
        }
        return true;
    }
}
