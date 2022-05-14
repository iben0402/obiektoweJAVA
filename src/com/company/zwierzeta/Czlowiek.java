package com.company.zwierzeta;

import com.company.Zwierze;
import com.company.Organizm;
import com.company.Komentarze;
import com.company.Swiat;
import com.company.Punkt;
import com.company.Umiejetnosc;

import java.awt.*;


public class Czlowiek extends Zwierze {
    private static final int CZLOWIEK_ZASIEG = 1;
    private static final int CZLOWIEK_SZANSA = 1;
    private static final int CZLOWIEK_SILA = 5;
    private static final int CZLOWIEK_INICJATYWA =4;
    private Kierunek kierunekRuchu;
    private Umiejetnosc umiejetnosc;

    public Czlowiek(Swiat swiat, Punkt pozycja, int turaUrodzenia)
    {
        super(Typ.CZLOWIEK, swiat, pozycja, turaUrodzenia, CZLOWIEK_SILA, CZLOWIEK_INICJATYWA);
        this.setZasieg(CZLOWIEK_ZASIEG);
        this.setSzansaNaRuch(CZLOWIEK_SZANSA);
        kierunekRuchu= Kierunek.BRAK_KIERUNKU;
        setKolor(Color.MAGENTA);
        umiejetnosc = new Umiejetnosc();
    }

    //TODO umiejetnosc


    @Override
    protected Punkt ZaplanujRuch() {
        int x=getPozycja().getX();
        int y=getPozycja().getY();

        LosujDowolnePole(getPozycja()); //BLOKOWANIE KEIRUNKOW

        if(kierunekRuchu==Kierunek.BRAK_KIERUNKU || CzyKierunekZablokowany(kierunekRuchu)) return getPozycja();
        else
        {
            if(kierunekRuchu==Kierunek.GORA) return new Punkt(x, y-1);
            if(kierunekRuchu==Kierunek.DOL) return new Punkt(x, y+1);
            if(kierunekRuchu==Kierunek.PRAWO) return new Punkt(x+1, y);
            if(kierunekRuchu==Kierunek.LEWO) return new Punkt(x-1, y);
            else return new Punkt(x, y);
        }
    }

    @Override
    public void Akcja() {
        if(umiejetnosc.isCzyJestAktywna())
        {
            Komentarze.DodajKomentarz(OrganizmToString() + ": umiejetnosc jest uruchomiona, pozostaly czas " + umiejetnosc.getCzasTrwania() + " tur");
        }

        for(int i=0; i<getZasieg(); i++)
        {
            Punkt nastepnaPozycja = ZaplanujRuch();

            if(getSwiat().CzyPoleJestZajete(nastepnaPozycja) && getSwiat().CoZnajdujeSieNaPolu(nastepnaPozycja) != this)
            {
                Kolizja(getSwiat().CoZnajdujeSieNaPolu(nastepnaPozycja));
                break;
            }
            else if(getSwiat().CoZnajdujeSieNaPolu(nastepnaPozycja) != this) WykonajRuch(nastepnaPozycja);
        }
        kierunekRuchu=Kierunek.BRAK_KIERUNKU;
        umiejetnosc.Warunki();
    }

    @Override
    public String TypOrganizmuToString() {
        return "Czlowiek";
    }

    public Umiejetnosc getUmiejetnosc() {
        return umiejetnosc;
    }

    public void setKierunekRuchu(Kierunek kierunekRuchu) {
        this.kierunekRuchu = kierunekRuchu;
    }
}
