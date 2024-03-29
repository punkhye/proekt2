package Panels;

import Panels.PopUpFrames.PopUpDateSearchFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchPanel extends JPanel {

    JButton dateButton = new JButton("Търсене на прожекции по дата");

    JButton movieButton = new JButton("Търсене на филм");


    public SearchPanel(){

        this.add(dateButton);
        dateButton.addActionListener(new SearchDateAction());

        this.add(movieButton);
        movieButton.addActionListener(new SearchMovieAction());
    }

    class SearchDateAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            PopUpDateSearchFrame frame = new PopUpDateSearchFrame();
        }
    }

    class SearchMovieAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }


}
