package com.company;

import java.util.Random;

public abstract class Roslina extends Organizm{

    protected Roslina(Typ typ, Swiat swiat, Punkt pozycja, int turaUrodzenia, int sila, int inicjatywa)
    {
        super(typ, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setSzansaRozmnazania(0.3);
    }

    @Override
    public void Akcja() {
        Random rand = new Random();
        int tmpLos = rand.nextInt(100);
        if(tmpLos < getSzansaRozmnazania() * 100) Rozprzestrzenianie();
    }

    @Override
    public boolean CzyJestZwierzeciem() {
        return false;
    }

    protected void Rozprzestrzenianie()
    {
        Punkt tmpPunkt1 = this.LosujWolnePole(getPozycja());
        if(tmpPunkt1.equals(getPozycja())) return;
        else
        {
            Organizm tmpOrganizm = KreatorOrganizmów.StworzOrganizm(getTyp(), getSwiat(), tmpPunkt1);
            Komentarze.DodajKomentarz("Wzrasta nowa roslina " + tmpOrganizm.OrganizmToString());
            getSwiat().DodajOrganizm(tmpOrganizm);
        }
    }

    @Override
    public void Kolizja(Organizm other) {
//        NIE JEST POTRZEBNA - funkcja Dzialanie podczas ataku
    }
}
