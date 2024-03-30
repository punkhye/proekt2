package Panels.PopUpFrames;

import DBconnection.DBConnection;
import Panels.TModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PopUpCinemaSearchFrame extends JFrame {

    static Connection conn = null;
    static PreparedStatement statement = null;

    static ResultSet result = null;

    JTable tableMovie = new JTable();

    JScrollPane scrollMovie = new JScrollPane(tableMovie);

    JTable tableScreenings = new JTable();

    JScrollPane scrollScreenings = new JScrollPane(tableScreenings);

    JLabel label = new JLabel("Въведете името на филми, който търсите: ");

   JTextField nameTF = new JTextField("", 20);

    JButton closeButton = new JButton("Отказ");
    JButton searchButton = new JButton("Търси...");

    JPanel upPanel = new JPanel();
    JPanel downPanel = new JPanel();



     public PopUpCinemaSearchFrame() {

        this.setSize(600, 200);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(2, 1));

        //up panel
         upPanel.setLayout(new FlowLayout());
         upPanel.add(label);
         upPanel.add(nameTF);
         this.add(upPanel);



        //down panel
        this.add(downPanel);
        downPanel.add(searchButton);
        searchButton.addActionListener(new SearchAction());
        downPanel.add(closeButton);
        closeButton.addActionListener(e -> dispose());

        this.setVisible(true);

    }


    class SearchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            ShowInfo frameShowInfo = new ShowInfo();
        }
    }


    public class ShowInfo extends JFrame {

        JLabel label = new JLabel("Намерени филми: ");
        JLabel label1 = new JLabel("Намерени прожекции с този филм") ;



        JPanel upPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel downPanel = new JPanel();

        JButton doneButton = new JButton("Ок");

        ShowInfo() {
            this.setSize(500, 400);
            this.setLocationRelativeTo(null);
            this.setLayout(new GridLayout(3, 1));


            //up panel
            this.add(upPanel);
            upPanel.add(label);
            upPanel.add(scrollMovie);

            //center panel
            this.add(centerPanel);
            centerPanel.setLayout(new GridLayout(2,1));
            centerPanel.add(label1);
            buildScreeningsTable();
            centerPanel.add(scrollScreenings);

            //down panel
            this.add(downPanel);
            downPanel.add(doneButton);

            doneButton.addActionListener(e -> dispose());

            if (!buildTable()) {
                this.dispose();
                JFrame test = new JFrame();
                test.setSize(200, 120);
                test.setLocationRelativeTo(null);
                test.setLayout(new FlowLayout());
                JLabel label = new JLabel("Няма намерени филми");
                test.add(label);
                JButton but = new JButton("ok");
                but.addActionListener(e -> test.dispose());
                test.add(but);
                test.setVisible(true);
            } else {


                this.setVisible(true);
            }



        }


    }



    boolean buildTable() {


        conn = DBConnection.getConnection();
        String str;
        str = "SELECT movie_id, movie_name from Movies where movie_name=?";
        try {
            statement = conn.prepareStatement(str);
            String name = nameTF.getText();
            statement.setString(1, name);
            result = statement.executeQuery();


            String[] colsNames = {"ID","Име"};

            tableMovie.setModel(new TModel(result, colsNames));
            if (tableMovie.getRowCount() == 0  ) {
                System.out.println("nqma filmi s tova ime");
                return false;
            } else {

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    void buildScreeningsTable(){
        conn = DBConnection.getConnection();
         String str;
        str = "SELECT Screenings.screening_id, Cinemas.cinema_name, Movies.movie_name, Screenings.screening_day, Screenings.screening_month, Screenings.screening_year, Screenings.screening_time from SCREENINGS,CINEMAS,Movies where Screenings.cinema_id=Cinemas.cinema_id and Screenings.movie=movie_id and movie_name=?";
        try {
            statement = conn.prepareStatement(str);
            String name = nameTF.getText();
            statement.setString(1, name);

            result = statement.executeQuery();


            String[] colsNames = {"Номер на прожекция", "Кино", "Филм",
                    "Ден", "Месец", "Година",
                    "Час"};

            tableScreenings.setModel(new TModel(result, colsNames));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

