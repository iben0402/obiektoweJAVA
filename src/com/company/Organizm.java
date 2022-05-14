package com.company;

import java.awt.*;
import java.util.Random;

public abstract class Organizm {
    public enum Typ
    {
        CZLOWIEK,
        WILK,
        OWCA,
        LIS,
        ZOLW,
        ANTYLOPA,
        TRAWA,
        MLECZ,
        GUARANA,
        WILCZE_JAGODY,
        BARSZCZ_SOSNOWSKIEGO;
    }

    public enum Kierunek
    {
        LEWO(0),
        PRAWO(1),
        GORA(2),
        DOL(3),
        BRAK_KIERUNKU(4);

        private final int value;
        private Kierunek(int value) {this.value=value;}
        public int getValue() {return value;}
    }

    private int sila;
    private int inicjatywa;
    private int turaUrodzenia;
    private Color kolor;
    private boolean czyUmarl;
    private boolean[] kierunek;
    private boolean czyRozmnazalSie;
    private Swiat swiat;
    private Punkt pozycja;
    private Typ typ;
    private double szansaRozmnazania;
    private static final int LICZBA_GATUNKOW=11;

    public abstract String TypOrganizmuToString();
    public abstract void Akcja();
    public abstract void Kolizja(Organizm other);
    public abstract boolean CzyJestZwierzeciem();

    public Organizm(Typ typ, Swiat swiat, Punkt pozycja, int turaUrodzenia, int sila, int inicjatywa)
    {
        this.typ = typ;
        this.swiat=swiat;
        this.pozycja=pozycja;
        this.turaUrodzenia=turaUrodzenia;
        this.sila=sila;
        this.inicjatywa=inicjatywa;
        czyUmarl=false;
        kierunek = new boolean[]{true, true, true, true};
    }

    public boolean DzialaniePodczasAtaku(Organizm napastnik, Organizm ofiara) {return false;}

    public String OrganizmToString()
    {
        return (TypOrganizmuToString() + " x[" + pozycja.getX() + "] y[" + pozycja.getY()+ "] sila[" + sila + "]");
    }

    public void WykonajRuch(Punkt przyszlaPozycja)
    {
        int x = przyszlaPozycja.getX();
        int y = przyszlaPozycja.getY();
        swiat.getPlansza()[pozycja.getY()][pozycja.getX()] = null;
        swiat.getPlansza()[y][x] = this;
        pozycja.setX(x);
        pozycja.setY(y);
    }

    static Typ LosujTyp()
    {
        Random rand = new Random();
        int tmp = rand.nextInt(LICZBA_GATUNKOW-1) * 10;
        if(tmp<15) return Typ.ANTYLOPA;
        else if(tmp<30) return Typ.WILK;
        else if (tmp < 45) return Typ.LIS;
        else if (tmp < 60) return Typ.OWCA;
        else if (tmp < 75) return Typ.ZOLW;
        else if (tmp < 80) return Typ.MLECZ;
        else if (tmp < 85) return Typ.WILCZE_JAGODY;
        else if (tmp < 90) return Typ.BARSZCZ_SOSNOWSKIEGO;
        else if (tmp < 95) return Typ.GUARANA;
        return Typ.TRAWA;   //95-100
    }

    public Punkt LosujDowolnePole(Punkt pozycja)
    {
        OdblokujWszystkieKierunki();

        int x = pozycja.getX();
        int y = pozycja.getY();
        int sizeX = swiat.getSizeX();
        int sizeY = swiat.getSizeY();
        int mozliweKierunki=0;

        //BLOKOWANIE KIERUNKOW
        if(x==0) ZablokujKierunek(Kierunek.LEWO);
        else mozliweKierunki++;
        if(x==sizeX-1) ZablokujKierunek(Kierunek.PRAWO);
        else mozliweKierunki++;
        if(y==0) ZablokujKierunek(Kierunek.GORA);
        else mozliweKierunki++;
        if(y==sizeY-1) ZablokujKierunek(Kierunek.DOL);
        else mozliweKierunki++;

        if(mozliweKierunki==0) return pozycja;
        while(true)
        {
            Random rand = new Random();
            int upper = 100;
            int tmpLos = rand.nextInt(upper);

            //LEWO
            if(tmpLos <25 && !CzyKierunekZablokowany(Kierunek.LEWO)) return new Punkt(x-1, y);
            //PRAWO
            if(tmpLos <50 && !CzyKierunekZablokowany(Kierunek.PRAWO)) return new Punkt(x+1, y);
            //GORA
            if(tmpLos <75 && !CzyKierunekZablokowany(Kierunek.GORA)) return new Punkt(x, y-1);
            //DOL
            if(tmpLos <100 && !CzyKierunekZablokowany(Kierunek.DOL)) return new Punkt(x, y+1);

        }
    }

    public Punkt LosujWolnePole(Punkt pozycja)
    {
        OdblokujWszystkieKierunki();

        int x = pozycja.getX();
        int y = pozycja.getY();
        int sizeX = swiat.getSizeX();
        int sizeY = swiat.getSizeY();
        int mozliweKierunki=0;

        if(x==0) ZablokujKierunek(Kierunek.LEWO);
        else{
            if(swiat.CzyPoleJestZajete(new Punkt(x-1, y))==false) mozliweKierunki++;
            else ZablokujKierunek(Kierunek.LEWO);
        }
        if(x==sizeX-1) ZablokujKierunek(Kierunek.PRAWO);
        else {
            if(swiat.CzyPoleJestZajete(new Punkt(x+1, y))==false) mozliweKierunki++;
            else ZablokujKierunek(Kierunek.PRAWO);
        }
        if(y==0) ZablokujKierunek(Kierunek.GORA);
        else {
            if(swiat.CzyPoleJestZajete(new Punkt(x, y-1))==false) mozliweKierunki++;
            else ZablokujKierunek(Kierunek.GORA);
        }
        if(y==sizeY-1) ZablokujKierunek(Kierunek.DOL);
        else {
            if(swiat.CzyPoleJestZajete(new Punkt(x, y+1))==false) mozliweKierunki++;
            else ZablokujKierunek(Kierunek.DOL);
        }

        if(mozliweKierunki==0) return pozycja;
        while(true)
        {
            Random rand = new Random();
            int upper = 100;
            int tmpLos = rand.nextInt(upper);

            //LEWO
            if(tmpLos <25 && !CzyKierunekZablokowany(Kierunek.LEWO)) return new Punkt(x-1, y);
            //PRAWO
            if(tmpLos <50 && !CzyKierunekZablokowany(Kierunek.PRAWO)) return new Punkt(x+1, y);
            //GORA
            if(tmpLos <75 && !CzyKierunekZablokowany(Kierunek.GORA)) return new Punkt(x, y-1);
            //DOL
            if(tmpLos <100 && !CzyKierunekZablokowany(Kierunek.DOL)) return new Punkt(x, y+1);

        }
    }

    protected void ZablokujKierunek(Kierunek kierunek) {this.kierunek[kierunek.getValue()] = false;}

    protected void OdblokujKierunek(Kierunek kierunek) {this.kierunek[kierunek.getValue()] = true;}

    protected void OdblokujWszystkieKierunki()
    {
        OdblokujKierunek(Kierunek.LEWO);
        OdblokujKierunek(Kierunek.DOL);
        OdblokujKierunek(Kierunek.PRAWO);
        OdblokujKierunek(Kierunek.GORA);
    }

    protected boolean CzyKierunekZablokowany(Kierunek kierunek) {return !(this.kierunek[kierunek.getValue()]);}

    public int getSila() {
        return sila;
    }

    public int getInicjatywa() {
        return inicjatywa;
    }

    public int getTuraUrodzenia() {
        return turaUrodzenia;
    }

    public boolean isCzyUmarl() {
        return czyUmarl;
    }

    public boolean isCzyRozmnazalSie() {
        return czyRozmnazalSie;
    }

    public Swiat getSwiat() {
        return swiat;
    }

    public Punkt getPozycja() {
        return pozycja;
    }

    public Typ getTyp() {
        return typ;
    }

    public double getSzansaRozmnazania() {
        return szansaRozmnazania;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    public void setInicjatywa(int inicjatywa) {
        this.inicjatywa = inicjatywa;
    }

    public void setTuraUrodzenia(int turaUrodzenia) {
        this.turaUrodzenia = turaUrodzenia;
    }

    public void setCzyUmarl(boolean czyUmarl) {
        this.czyUmarl = czyUmarl;
    }

    public void setCzyRozmnazalSie(boolean czyRozmnazalSie) {
        this.czyRozmnazalSie = czyRozmnazalSie;
    }

    public void setSwiat(Swiat swiat) {
        this.swiat = swiat;
    }

    public void setPozycja(Punkt pozycja) {
        this.pozycja = pozycja;
    }

    public void setTyp(Typ typ) {
        this.typ = typ;
    }

    public void setSzansaRozmnazania(double szansaRozmnazania) {
        this.szansaRozmnazania = szansaRozmnazania;
    }

    public Color getKolor() {
        return kolor;
    }

    public void setKolor(Color kolor) {
        this.kolor = kolor;
    }




}
