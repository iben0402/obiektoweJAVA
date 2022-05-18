package com.company;

import com.company.zwierzeta.Czlowiek;
import com.company.zwierzeta.Zolw;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUI implements ActionListener, KeyListener {
        private Toolkit toolkit;
        private Dimension dimension;
        private JFrame jframe;
        private JMenu menu;
        private JMenuItem newGame, load, save, exit;
        private PlanszaGraphics plansza = null;
        private KomentatorGraphics komentator = null;
        private Oznaczenia oznaczenia = null;
        private JPanel mainPanel;
        private final int ODST;
        private Swiat swiat;

        public GUI(String title)
        {
            toolkit = Toolkit.getDefaultToolkit();
            dimension=toolkit.getScreenSize();
            ODST = dimension.height /100;
            jframe = new JFrame(title);
            jframe.setBounds((dimension.width-800)/2, (dimension.height-600)/2, 800, 600);
            jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JMenuBar menuBar = new JMenuBar();
            menu = new JMenu("Options");
            newGame = new JMenuItem("New Game");
            load = new JMenuItem("Load");
            save = new JMenuItem("Save");
            exit = new JMenuItem("Exit");

            newGame.addActionListener(this);
            load.addActionListener(this);
            save.addActionListener(this);
            exit.addActionListener(this);

            menu.add(newGame);
            menu.add(load);
            menu.add(save);
            menu.add(exit);

            menuBar.add(menu);
            jframe.setJMenuBar(menuBar);
            jframe.setLayout(new CardLayout());

            mainPanel = new JPanel();
            mainPanel.setBackground(Color.cyan);
            mainPanel.setLayout(null);

            jframe.addKeyListener(this);
            jframe.add(mainPanel);
            jframe.setVisible(true);

        }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newGame)
        {
            Komentarze.WyczyscKomentarz();
            int sizeX= Integer.parseInt(JOptionPane.showInputDialog(jframe, "Podaj szerokosc swiata", "25"));
            int sizeY= Integer.parseInt(JOptionPane.showInputDialog(jframe, "Podaj wysokosc swiata", "25"));
            double zapelnienie = Double.parseDouble(JOptionPane.showInputDialog(jframe, "Podaj zapelnienie swiata od 0 do 1", "0.5"));

            swiat = new Swiat(sizeX, sizeY, this);
            swiat.GenerujSwiat(zapelnienie);
            if(plansza != null) mainPanel.remove(plansza);
            if (komentator != null)
                mainPanel.remove(komentator);
            if (oznaczenia != null)
                mainPanel.remove(oznaczenia);
            startGame();
        }
        if(e.getSource() == load)
        {
            Komentarze.WyczyscKomentarz();
            String nameOfFile = JOptionPane.showInputDialog(jframe, "Podaj nazwe pliku", "save");
            swiat = Swiat.LadowanieSwiata(nameOfFile);
            swiat.setSwiatGui(this);
            plansza = new PlanszaGraphics(swiat);
            komentator = new KomentatorGraphics();
            oznaczenia = new Oznaczenia();

            if(plansza != null) mainPanel.remove(plansza);
            if(komentator != null) mainPanel.remove(komentator);
            if(oznaczenia != null) mainPanel.remove(oznaczenia);

            startGame();
        }
        if(e.getSource() == save)
        {
            String nameOfFile = JOptionPane.showInputDialog(jframe, "Podaj nazwe pliku", "save");
            swiat.ZapisSwiata(nameOfFile);
            Komentarze.DodajKomentarz("SWIAT ZOSTAL ZAPISANY!");
            komentator.odswiezKomentarze();
        }
        if(e.getSource() == exit)
        {
            jframe.dispose();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(swiat != null && swiat.isPauza())
        {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {

            }
            else if(swiat.isCzlowiekZyje())
            {
                if(keyCode==KeyEvent.VK_UP) swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.GORA);
                else if(keyCode==KeyEvent.VK_DOWN) swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.DOL);
                else if(keyCode==KeyEvent.VK_RIGHT) swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.PRAWO);
                else if(keyCode==KeyEvent.VK_LEFT) swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.LEWO);
                else if(keyCode==KeyEvent.VK_SPACE)
                {
                    Umiejetnosc tmpUmiejetnosc = swiat.getCzlowiek().getUmiejetnosc();
                    if(tmpUmiejetnosc.isCzyMoznaAktywowac())
                    {
                        tmpUmiejetnosc.Aktywuj();
                        Komentarze.DodajKomentarz("Umiejetnosc zostala wlaczona. Pozostaly czas trwania wynosi " + tmpUmiejetnosc.getCzasTrwania() + " tur");
                    }
                    else if(tmpUmiejetnosc.isCzyJestAktywna())
                    {
                        Komentarze.DodajKomentarz("Umiejetnosc jest juz aktywna. Pozostaly czas trwania: " + tmpUmiejetnosc.getCzasTrwania() + " tur.");
                        komentator.odswiezKomentarze();
                        return;
                    }
                    else
                    {
                        Komentarze.DodajKomentarz("Umiejetnosc mozna aktywowac za " + tmpUmiejetnosc.getCooldown() + " tur");
                        komentator.odswiezKomentarze();
                        return;
                    }
                }
                else
                {
                    Komentarze.DodajKomentarz("!!! Zly symbol, sprobuj ponownie !!!");
                    komentator.odswiezKomentarze();
                    return;
                }
            }
            else if(!swiat.isCzlowiekZyje() && (keyCode == KeyEvent.VK_UP ||
                    keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT ||
                    keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_P))
            {
                Komentarze.DodajKomentarz("CZLOWIEK NIE ZYJE, NIE MOZESZ NIM JUZ STEROWAC");
                komentator.odswiezKomentarze();
                return;
            }
            else
            {
                Komentarze.DodajKomentarz("!!! Zly symbol, sprobuj ponownie !!!");
                komentator.odswiezKomentarze();
                return;
            }
            Komentarze.WyczyscKomentarz();
            swiat.setPauza(false);
            swiat.WykonajTure();
            odswiezSwiat();
            swiat.setPauza(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private class PlanszaGraphics extends JPanel
    {
        private final int sizeX;
        private final int sizeY;
        private PolePlanszy[][] polaPlanszy;
        private Swiat SWIAT;

        public PlanszaGraphics(Swiat swiat)
        {
            super();
            setBounds(mainPanel.getX() + ODST, mainPanel.getY() + ODST,
                    mainPanel.getHeight()*5/6 - ODST, mainPanel.getHeight() * 5/6 - ODST);

            SWIAT=swiat;
            this.sizeX=swiat.getSizeX();
            this.sizeY=swiat.getSizeY();

            polaPlanszy = new PolePlanszy[sizeY][sizeX];
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    polaPlanszy[i][j] = new PolePlanszy(j, i);
                    polaPlanszy[i][j].addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            if (e.getSource() instanceof PolePlanszy)
                            {
                                PolePlanszy tmpPole = (PolePlanszy) e.getSource();
                                if(tmpPole.isEmpty == true)
                                {
                                    ListaOrg listaOrganizmow = new ListaOrg(tmpPole.getX() + jframe.getX(),
                                            tmpPole.getY() +jframe.getY(), new Punkt(tmpPole.getPozX(), tmpPole.getPozY()));
                                }

                            }
                        }
                    });
                }
            }

            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    this.add(polaPlanszy[i][j]);
                }
            }
            this.setLayout(new GridLayout(sizeY, sizeX));
        }

        private class PolePlanszy extends JButton {
            private boolean isEmpty;
            private Color kolor;
            private final int pozX;
            private final int pozY;

            public PolePlanszy(int X, int Y) {
                super();
                kolor = Color.WHITE;
                setBackground(kolor);
                isEmpty = true;
                pozX = X;
                pozY = Y;
            }

            public boolean isEmpty() {
                return isEmpty;
            }

            public void setEmpty(boolean empty) {
                isEmpty = empty;
            }


            public Color getKolor() {
                return kolor;
            }

            public void setKolor(Color kolor) {
                this.kolor = kolor;
                setBackground(kolor);
            }

            public int getPozX() {
                return pozX;
            }

            public int getPozY() {
                return pozY;
            }
        }

        public void odswiezPlansze()
        {
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    Organizm tmpOrganizm = swiat.getPlansza()[i][j];
                    if (tmpOrganizm != null) {
                        polaPlanszy[i][j].setEmpty(false);
                        polaPlanszy[i][j].setEnabled(false);
                        polaPlanszy[i][j].setKolor(tmpOrganizm.getKolor());
                    } else {
                        polaPlanszy[i][j].setEmpty(true);
                        polaPlanszy[i][j].setEnabled(true);
                        polaPlanszy[i][j].setKolor(Color.WHITE);
                    }
                }
            }
        }

        public int getSizeX() {
            return sizeX;
        }

        public int getSizeY() {
            return sizeY;
        }

        public PolePlanszy[][] getPolaPlanszy() {
            return polaPlanszy;
        }
    }

    private class KomentatorGraphics extends JPanel
    {
        private String tekst;
        private final String info = "Iwona Bendig, 188593\nStrzalki - sterowanie\nSpacja - aktywacja umiejetnosci\nEnter - nowa tura\n";
        private JTextArea textArea;

        public KomentatorGraphics() {
            super();
            setBounds(plansza.getX() + plansza.getWidth() + ODST,
                    mainPanel.getY() + ODST,
                    mainPanel.getWidth() - plansza.getWidth() - ODST * 3,
                    mainPanel.getHeight() * 5 / 6 - ODST);
            tekst = Komentarze.getTekst();
            textArea = new JTextArea(tekst);
            textArea.setEditable(false);
            setLayout(new CardLayout());

            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setMargin(new Insets(5, 5, 5, 5));
            JScrollPane sp = new JScrollPane(textArea);
            add(sp);
        }

        public void odswiezKomentarze() {
            tekst = info + Komentarze.getTekst();
            textArea.setText(tekst);
        }
    }

    private class ListaOrg extends JFrame {
            private String[] listaOrg;
            private Organizm.Typ[] typOrgList;
            private JList jList;

            public ListaOrg(int x, int y, Punkt punkt)
            {
                super("Lista organizmow");
                setBounds(x, y, 100, 300);
                listaOrg = new String[]{"Barszcz Sosnowskiego", "Guarana", "Mlecz", "Trawa", "Wilcze Jagody", "Antylopa", "Lis", "Owca", "Wilk", "Zolw"};
                typOrgList = new Organizm.Typ[]{Organizm.Typ.BARSZCZ_SOSNOWSKIEGO, Organizm.Typ.GUARANA,
                        Organizm.Typ.MLECZ, Organizm.Typ.TRAWA, Organizm.Typ.WILCZE_JAGODY, Organizm.Typ.ANTYLOPA,
                        Organizm.Typ.LIS, Organizm.Typ.OWCA, Organizm.Typ.WILK, Organizm.Typ.ZOLW};

                jList = new JList(listaOrg);
                jList.setVisibleRowCount(listaOrg.length);
                jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                jList.addListSelectionListener(new ListSelectionListener() {
                       @Override
                       public void valueChanged(ListSelectionEvent e) {
                           Organizm tmpOrganizm = KreatorOrganizmow.StworzOrganizm(typOrgList[jList.getSelectedIndex()], swiat, punkt);
                           swiat.DodajOrganizm(tmpOrganizm);
                           Komentarze.DodajKomentarz("Stworzono nowy organizm " + tmpOrganizm.OrganizmToString());
                           odswiezSwiat();
                           dispose();
                       }
                   }

                );

                JScrollPane sp = new JScrollPane(jList);
                add(sp);

                setVisible(true);
            }
    }

    private class Oznaczenia extends JPanel
    {
        private final int ILOSC_TYPOW = 11;
        private JButton[] jButtons;

        public Oznaczenia()
        {
            super();
            setBounds(mainPanel.getX()+ODST, mainPanel.getHeight() *5/6 + ODST, mainPanel.getWidth() - ODST*2, mainPanel.getHeight()/6 - 2*ODST);
            setBackground(Color.WHITE);
            setLayout(new FlowLayout(FlowLayout.CENTER));

            jButtons = new JButton[ILOSC_TYPOW];


            jButtons[0] = new JButton("Barszcz Sosnowskiego");
            jButtons[0].setBackground(new Color(82, 94, 43));

            jButtons[1] = new JButton("Guarana");
            jButtons[1].setBackground(new Color(210, 116, 139));

            jButtons[2] = new JButton("Mlecz");
            jButtons[2].setBackground(new Color(232, 226, 130));

            jButtons[3] = new JButton("Trawa");
            jButtons[3].setBackground(new Color(126, 213, 127));

            jButtons[4] = new JButton("Wilcze jagody");
            jButtons[4].setBackground(new Color(121, 63, 178));

            jButtons[5] = new JButton("Antylopa");
            jButtons[5].setBackground(new Color(208, 180, 143));

            jButtons[6] = new JButton("Czlowiek");
            jButtons[6].setBackground(Color.MAGENTA);

            jButtons[7] = new JButton("Lis");
            jButtons[7].setBackground(new Color(203, 122, 59));

            jButtons[8] = new JButton("Owca");
            jButtons[8].setBackground(new Color(103, 103, 103));

            jButtons[9] = new JButton("Wilk");
            jButtons[9].setBackground(new Color(49, 32, 32));

            jButtons[10] = new JButton("Zolw");
            jButtons[10].setBackground(new Color(9, 58, 9));

            for (int i = 0; i < ILOSC_TYPOW; i++) {
                jButtons[i].setEnabled(false);
                add(jButtons[i]);
            }
        }
    }


    private void startGame() {
            plansza = new PlanszaGraphics(swiat);
            mainPanel.add(plansza);

            komentator = new KomentatorGraphics();
            mainPanel.add(komentator);

            oznaczenia = new Oznaczenia();
            mainPanel.add(oznaczenia);

            odswiezSwiat();
    }

    public void odswiezSwiat()
    {
        plansza.odswiezPlansze();
        komentator.odswiezKomentarze();
        SwingUtilities.updateComponentTreeUI(jframe);
        jframe.requestFocusInWindow();
    }

    public PlanszaGraphics getPlansza() {
        return plansza;
    }

    public KomentatorGraphics getKomentator() {
        return komentator;
    }

    public Swiat getSwiat() {
        return swiat;
    }
}
