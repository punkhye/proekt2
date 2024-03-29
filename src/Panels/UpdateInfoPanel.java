package Panels;

import Panels.PopUpFrames.PopUpCinemaFrame;
import Panels.PopUpFrames.PopUpMovieFrame;
import Panels.PopUpFrames.PopUpScreeningFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateInfoPanel extends JPanel {

    JButton updateCinemasButton = new JButton("Промяна на име на кино");
    JButton updateMoviesButton = new JButton("Промяна на име на филм");
    JButton updateScreeningsButton = new JButton("Промяна на дата на прожекция");

    public UpdateInfoPanel(){

        this.setLayout(new GridLayout(3,1));
        this.add(updateCinemasButton);
        updateCinemasButton.addActionListener(new UpdateCinemaAction());
        this.add(updateMoviesButton);
        updateMoviesButton.addActionListener(new UpdateMovieAction());
        this.add(updateScreeningsButton);
        updateScreeningsButton.addActionListener(new UpdateScreeningAction());



    }

    class UpdateCinemaAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            PopUpCinemaFrame frame = new PopUpCinemaFrame();
        }
    }

    class UpdateMovieAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            PopUpMovieFrame frame = new PopUpMovieFrame();
        }
    }

    class UpdateScreeningAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            PopUpScreeningFrame frame = new PopUpScreeningFrame();
        }
    }

}
