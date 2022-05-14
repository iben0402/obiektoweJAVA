package com.company;

import java.util.Random;

public abstract class Zwierze extends Organizm{

    private int zasieg;
    private double szansaNaRuch;

    public Zwierze(Typ typ, Swiat swiat,
                   Punkt pozycja, int turaUrodzenia, int sila, int inicjatywa) {
        super(typ, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setCzyRozmnazalSie(false);
        setSzansaRozmnazania(0.25);
    }

    @Override
    public void Akcja()
    {
        for (int i = 0; i < zasieg; i++) {
            Punkt nastepnaPozycja = ZaplanujRuch();

            if(getSwiat().CzyPoleJestZajete(nastepnaPozycja) && getSwiat().CoZnajdujeSieNaPolu(nastepnaPozycja) != this)
            {
                Kolizja(getSwiat().CoZnajdujeSieNaPolu(nastepnaPozycja));
                break;
            }
            else if(getSwiat().CoZnajdujeSieNaPolu(nastepnaPozycja)!=this) WykonajRuch(nastepnaPozycja);
        }
    }

    @Override
    public void Kolizja(Organizm other) {
        if(getTyp() == other.getTyp())
        {
            Random rand = new Random();
            int tmpLos = rand.nextInt(100);
            if(tmpLos <getSzansaRozmnazania()*100) Rozmnazanie(other);
        }
        else
        {
            if(other.DzialaniePodczasAtaku(this, other)) return;
            if(DzialaniePodczasAtaku(this, other)) return;

            if(getSila() >= other.getSila())
            {
                getSwiat().UsunOrganizm(other);
                WykonajRuch(other.getPozycja());
                Komentarze.DodajKomentarz(OrganizmToString() + " zabija " + other.OrganizmToString());
            }
            else
            {
                getSwiat().UsunOrganizm(this);
                Komentarze.DodajKomentarz(other.OrganizmToString() + " zabija " + OrganizmToString());
            }
        }
    }

    @Override
    public boolean CzyJestZwierzeciem() {
        return true;
    }

    protected Punkt ZaplanujRuch()
    {
        Random rand = new Random();
        int tmpLos = rand.nextInt(100);
        if(tmpLos >= (int)(szansaNaRuch*100)) return getPozycja();
        else return LosujDowolnePole(getPozycja());
    }

    private void Rozmnazanie(Organizm other)
    {
        if(this.isCzyRozmnazalSie() || other.isCzyRozmnazalSie()) return;
        Punkt tmpPunkt1 = this.LosujWolnePole(getPozycja());
        if(tmpPunkt1.equals(getPozycja()))
        {
            Punkt tmpPunkt2 = other.LosujWolnePole(other.getPozycja());
            if(tmpPunkt2.equals(other.getPozycja())) return;
            else
            {
                Organizm tmpOrganizm = KreatorOrganizmow.StworzOrganizm(getTyp(), getSwiat(), tmpPunkt2);
                Komentarze.DodajKomentarz("Urodzil sie " + tmpOrganizm.OrganizmToString());
                getSwiat().DodajOrganizm(tmpOrganizm);
                setCzyRozmnazalSie(true);
                other.setCzyRozmnazalSie(true);
            }
        }
        else
        {
            Organizm tmpOrganizm = KreatorOrganizmow.StworzOrganizm(getTyp(), getSwiat(), tmpPunkt1);
            Komentarze.DodajKomentarz("Urodzil sie " + tmpOrganizm.OrganizmToString());
            getSwiat().DodajOrganizm(tmpOrganizm);
            setCzyRozmnazalSie(true);
            other.setCzyRozmnazalSie(true);
        }
    }

    public int getZasieg() {
        return zasieg;
    }

    public void setZasieg(int zasieg) {
        this.zasieg = zasieg;
    }

    public double getSzansaNaRuch() {
        return szansaNaRuch;
    }

    public void setSzansaNaRuch(double szansaNaRuch) {
        this.szansaNaRuch = szansaNaRuch;
    }
}
