package com.company;

public class Umiejetnosc {
    private final int COOLDOWN =5;
    private boolean czyMoznaAktywowac;
    private boolean czyJestAktywna;
    private int czasTrwania;
    private int cooldown;

    public Umiejetnosc()
    {
        cooldown=0;
        czasTrwania=0;
        czyJestAktywna=false;
        czyMoznaAktywowac=true;
    }

    public void Warunki()
    {
        if(cooldown>0) cooldown--;
        if (czasTrwania > 0) czasTrwania--;
        if (czasTrwania == 0) Dezaktywuj();
        if (cooldown == 0) czyMoznaAktywowac = true;
    }

    public void Aktywuj()
    {
        if(cooldown==0);
        czyMoznaAktywowac=false;
        czyJestAktywna=true;
        cooldown=2*COOLDOWN;
        czasTrwania=COOLDOWN;
    }

    public void Dezaktywuj()
    {
        czyJestAktywna=false;
    }

    public boolean isCzyMoznaAktywowac() {
        return czyMoznaAktywowac;
    }

    public void setCzyMoznaAktywowac(boolean czyMoznaAktywowac) {
        this.czyMoznaAktywowac = czyMoznaAktywowac;
    }

    public boolean isCzyJestAktywna() {
        return czyJestAktywna;
    }

    public void setCzyJestAktywna(boolean czyJestAktywna) {
        this.czyJestAktywna = czyJestAktywna;
    }

    public int getCzasTrwania() {
        return czasTrwania;
    }

    public void setCzasTrwania(int czasTrwania) {
        this.czasTrwania = czasTrwania;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
