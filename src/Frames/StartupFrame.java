package Frames;

import Panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class StartupFrame extends JFrame {

    JTabbedPane tab = new JTabbedPane();
    CinemaPanel cinemaPanel = new CinemaPanel();
    MoviePanel moviePanel = new MoviePanel();
    SearchPanel searchPanel = new SearchPanel();
    ScreeningPanel screeningPanel = new ScreeningPanel();

    UpdateInfoPanel updateInfoPanel = new UpdateInfoPanel();

    public StartupFrame() {

        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLayout(new GridLayout(1,1));

        //tab
        this.add(tab);
        tab.add(screeningPanel, "Прожекции");
        tab.add(cinemaPanel,"Кина");
        tab.add(moviePanel,"Филми");
        tab.add(searchPanel,"Търсене...");
        tab.add(updateInfoPanel,"Актуализиране на информация...");

        tab.addMouseListener(new Refresh());


        this.setVisible(true);
    }



    class Refresh implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {





        }

        @Override
        public void mousePressed(MouseEvent e) {
            /* pri clickvane se opredelq index-a na tab-a
            ako toi e 0(index-a na tab-a na projekciite)
            se mahat starite paneli i se suzdavat novi
            na mqstoto na starite za da se updatenat
            combobox-ovete i tablicata za projekcii ako sa bili promeneni dati na projekcii
             */
            int selectedIndex = tab.getSelectedIndex();
            System.out.println(selectedIndex);

            if(0 == selectedIndex){
                // 0 e index-a na purviq tab

                //mahame vsichki paneli zashtoto sme click-nali na purviq
                tab.removeAll();

                //syzdavat se novi za da se update informaciqta po tablicite
                cinemaPanel = new CinemaPanel();
                moviePanel = new MoviePanel();
                screeningPanel = new ScreeningPanel();
                searchPanel = new SearchPanel();
                updateInfoPanel = new UpdateInfoPanel();

                //otnovo se dobavqt kum tab-a
                tab.add(screeningPanel, "Прожекции");
                tab.add(cinemaPanel,"Кина");
                tab.add(moviePanel,"Филми");
                tab.add(searchPanel,"Търсене...");
                tab.add(updateInfoPanel,"Актуализиране на информация...");

                tab.setVisible(false);
                tab.setVisible(true);

            }

            //refresh na panela za kina
            if(1 == selectedIndex){

                //mahat se ot posleden panel do panela za kinata
                tab.remove(4);
                tab.remove(3);
                tab.remove(2);
                tab.remove(1);


                //updatevat se
                cinemaPanel = new CinemaPanel();
                moviePanel = new MoviePanel();
                searchPanel = new SearchPanel();
                updateInfoPanel = new UpdateInfoPanel();


                //pak se dobavqt
                tab.add(cinemaPanel,"Кина");
                tab.add(moviePanel,"Филми");
                tab.add(searchPanel,"Търсене...");
                tab.add(updateInfoPanel,"Актуализиране на информация...");

                //setva se koi index na tab-a da se otvori (1-ri koito e tab-a za kina)
                tab.setSelectedIndex(1);

                tab.setVisible(false);
                tab.setVisible(true);

            }

            //refresh na panela za filmi
            if(2 == selectedIndex){

                // mahane na tab-ovete na panelite ot posledniq do tozi za filmite
                tab.remove(4);
                tab.remove(3);
                tab.remove(2);


                //suzdavat se novi
                moviePanel = new MoviePanel();
                searchPanel = new SearchPanel();
                updateInfoPanel = new UpdateInfoPanel();

                //otnovo se dobavqt
                tab.add(moviePanel,"Филми");
                tab.add(searchPanel,"Търсене...");
                tab.add(updateInfoPanel,"Актуализиране на информация...");

                //setva se koi index na tab-a da se otvori (2-ri koito e tab-a za filmi)
                tab.setSelectedIndex(2);

                tab.setVisible(false);
                tab.setVisible(true);

            }


        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }





}
