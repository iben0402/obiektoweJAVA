package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import com.company.zwierzeta.Czlowiek;

public class Swiat {
    private int sizeX;
    private int sizeY;
    private int tura;
    private ArrayList<Organizm> organizmy;
    private Organizm[][] plansza;
    private boolean czlowiekZyje;
    private boolean koniec;
    private boolean pauza;
    private Czlowiek czlowiek;
    private GUI swiatGui;

    public Swiat(GUI swiatGui)
    {
        this.sizeX = 0;
        this.sizeY =0;
        tura = 0;
        czlowiekZyje = true;
        koniec = false;
        pauza = true;
        organizmy = new ArrayList<>();
        this.swiatGui = swiatGui;
    }

    public Swiat(int sizeX, int sizeY, GUI swiatGui)
    {
        this.sizeX = sizeX;
        this.sizeY =sizeY;
        tura = 0;
        czlowiekZyje = true;
        koniec = false;
        pauza = true;
        plansza = new Organizm[sizeY][sizeX];
        for(int i=0; i<sizeY; i++)
        {
            for(int j=0; j<sizeX; j++)
            {
                plansza[i][j]= null;
            }
        }
        organizmy = new ArrayList<>();
        this.swiatGui = swiatGui;
    }

    //TODO Zapis swiata

    //TODO Odtwarzanie swiata

    public void GenerujSwiat(double zapelnienie)
    {
        int liczbaOrganizmow = (int) Math.floor(sizeX*sizeY*zapelnienie);

        //CZLOWIEK
        Punkt pozycja = WylosujWolnePole();
        Organizm tmpOrganizm = KreatorOrganizmów.StworzOrganizm(Organizm.Typ.CZLOWIEK, this, pozycja);
        DodajOrganizm(tmpOrganizm);
        czlowiek = (Czlowiek) tmpOrganizm;

        for (int i = 0; i < liczbaOrganizmow -1; i++) {
            pozycja = WylosujWolnePole();
            if(pozycja!= new Punkt(-1, -1))
            {
                DodajOrganizm(KreatorOrganizmów.StworzOrganizm(Organizm.LosujTyp(), this, pozycja));
            }else return;
        }
    }

    public void WykonajTure()
    {
        if(koniec) return;
        tura++;
        Komentarze.DodajKomentarz("\nTURA " + tura);

        System.out.println(tura + " ");
        System.out.println(organizmy.size() + "\n");

        SortujOrganizmy();
        for (int i = 0; i < organizmy.size(); i++) {
            if(organizmy.get(i).getTuraUrodzenia() != tura && organizmy.get(i).isCzyUmarl() == false)
            {
                organizmy.get(i).Akcja();
            }
        }

        for (int i = 0; i < organizmy.size(); i++) {
            if(organizmy.get(i).isCzyUmarl()==true)
            {
                organizmy.remove(i);
                i--;
            }
        }

        for (int i = 0; i < organizmy.size(); i++) {
            organizmy.get(i).setCzyRozmnazalSie(false);
        }
    }

    private void SortujOrganizmy() {
        Collections.sort(organizmy, new Comparator<Organizm>() {
            @Override
            public int compare(Organizm o1, Organizm o2) {
                if (o1.getInicjatywa() != o2.getInicjatywa())
                    return Integer.valueOf(o2.getInicjatywa()).compareTo(o1.getInicjatywa());
                else
                    return Integer.valueOf(o1.getTuraUrodzenia()).compareTo(o2.getTuraUrodzenia());
            }
        });
    }

    public Punkt WylosujWolnePole()
    {
        Random rand = new Random();
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                if(plansza[i][j] == null)
                {
                    while(true)
                    {
                        int x= rand.nextInt(sizeX);
                        int y=rand.nextInt(sizeY);
                        if(plansza[y][x]==null) return new Punkt(x, y);
                    }
                }

            }

        }
        return new Punkt(-1, -1);
    }

    public boolean CzyPoleJestZajete(Punkt pole)
    {
        if(plansza[pole.getY()][pole.getX()] == null) return false;
        return true;
    }

    public Organizm CoZnajdujeSieNaPolu(Punkt pole) {return plansza[pole.getY()][pole.getX()];}

    public void DodajOrganizm(Organizm organizm)
    {
        organizmy.add(organizm);
        plansza[organizm.getPozycja().getY()][organizm.getPozycja().getX()] = organizm;
    }

    public void UsunOrganizm(Organizm organizm)
    {
        plansza[organizm.getPozycja().getY()][organizm.getPozycja().getX()] = null;
        organizm.setCzyUmarl(true);
        if(organizm.getTyp()==Organizm.Typ.CZLOWIEK)
        {
            czlowiekZyje = false;
            czlowiek = null;
        }
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public int getTura() {
        return tura;
    }

    public void setTura(int tura) {
        this.tura = tura;
    }

    public ArrayList<Organizm> getOrganizmy() {
        return organizmy;
    }

    public Organizm[][] getPlansza() {
        return plansza;
    }

    public void setPlansza(Organizm[][] plansza) {
        this.plansza = plansza;
    }

    public boolean isCzlowiekZyje() {
        return czlowiekZyje;
    }

    public void setCzlowiekZyje(boolean czlowiekZyje) {
        this.czlowiekZyje = czlowiekZyje;
    }

    public boolean isKoniec() {
        return koniec;
    }

    public void setKoniec(boolean koniec) {
        this.koniec = koniec;
    }

    public boolean isPauza() {
        return pauza;
    }

    public void setPauza(boolean pauza) {
        this.pauza = pauza;
    }

    public Czlowiek getCzlowiek() {
        return czlowiek;
    }

    public void setCzlowiek(Czlowiek czlowiek) {
        this.czlowiek = czlowiek;
    }

    public GUI getSwiatGui() {
        return swiatGui;
    }

    public void setSwiatGui(GUI swiatGui) {
        this.swiatGui = swiatGui;
    }
}


