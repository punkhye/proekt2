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
            combobox-ovete
             */
            int selectedIndex = tab.getSelectedIndex();
            System.out.println(selectedIndex);
            int index = 0;
            if(index == selectedIndex){
                tab.removeAll();

                screeningPanel = new ScreeningPanel();
                searchPanel = new SearchPanel();
                updateInfoPanel = new UpdateInfoPanel();

                tab.add(screeningPanel, "Прожекции");
                tab.add(cinemaPanel,"Кина");
                tab.add(moviePanel,"Филми");
                tab.add(searchPanel,"Търсене...");
                tab.add(updateInfoPanel,"Актуализиране на информация...");

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
