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

public class PopUpDateSearchFrame extends JFrame {

    static Connection conn = null;
    static PreparedStatement statement = null;

    static ResultSet result = null;

    JTable table = new JTable();

    JScrollPane scroll = new JScrollPane(table);

    String day = null;

    String month = null;
    String year = null;
    JLabel label = new JLabel("Въведете датата, на която искате да проверите прожекциите: ");
    JLabel dayLabel = new JLabel("Ден: ");
    static JSpinner dayPicker = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
    JLabel monthLabel = new JLabel("Месец: ");
    static String[] monthsArr = {"Януари", "Февруари", "Март",
            "Април", "Май", "Юни", "Юли", "Август",
            "Септември", "Октомври", "Ноември", "Декември"};

    JComboBox<String> monthsBox = new JComboBox<>(monthsArr);
    JLabel yearLabel = new JLabel("Година: ");
    static String[] yearsArr = {"2024", "2025", "2026",
            "2027", "2028", "2029", "2030",};

    JComboBox<String> yearsBox = new JComboBox<>(yearsArr);

    JButton closeButton = new JButton("Отказ");
    JButton searchButton = new JButton("Търси...");

    JPanel upPanel = new JPanel();
    JPanel centerPanel = new JPanel();

    JPanel downPanel = new JPanel();


    public PopUpDateSearchFrame() {
        this.setSize(400, 250);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(3, 1));

        //up panel
        this.add(upPanel);
        upPanel.add(label);


        //center panel
        this.add(centerPanel);
        centerPanel.setLayout(new GridLayout(3, 2));
        centerPanel.add(dayLabel);
        centerPanel.add(dayPicker);
        centerPanel.add(monthLabel);
        centerPanel.add(monthsBox);
        centerPanel.add(yearLabel);
        centerPanel.add(yearsBox);


        //down panel
        this.add(downPanel);
        downPanel.add(searchButton);
        searchButton.addActionListener(new SearchAction());
        downPanel.add(closeButton);
        closeButton.addActionListener(new CloseAction());

        this.setVisible(true);

    }

    class CloseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    class SearchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            ShowInfo frameShowInfo = new ShowInfo();
        }
    }


    public class ShowInfo extends JFrame {

        JLabel label = new JLabel("Намерени прожекции: ");


        JPanel upPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel downPanel = new JPanel();

        JButton doneButton = new JButton("Ок");

        ShowInfo() {
            this.setSize(500, 250);
            this.setLocationRelativeTo(null);
            this.setLayout(new GridLayout(3, 1));


            //up panel
            this.add(upPanel);
            upPanel.add(label);

            //center panel
            this.add(centerPanel);
            centerPanel.add(scroll);

            //down panel
            this.add(downPanel);
            downPanel.add(doneButton);
            doneButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });

            if (!buildTable()) {
                this.dispose();
                JFrame test = new JFrame();
                test.setSize(100, 120);
                test.setLocationRelativeTo(null);
                test.setLayout(new FlowLayout());
                JLabel label = new JLabel("Няма прожекции");
                test.add(label);
                JButton but = new JButton("ok");
                but.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        test.dispose();
                    }
                });
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
        str = "SELECT Screenings.screening_id, Cinemas.cinema_name, Movies.movie_name, Screenings.screening_day, Screenings.screening_month, Screenings.screening_year, Screenings.screening_time from SCREENINGS,CINEMAS,Movies where Screenings.cinema_id=Cinemas.cinema_id and Screenings.movie=movie_id and screening_day=? and screening_month=? and screening_year=? ";

        try {
            statement = conn.prepareStatement(str);
            int screeningDay = (int) dayPicker.getValue();
            statement.setInt(1, screeningDay);
            String string1 = (String) monthsBox.getSelectedItem();
            statement.setString(2, string1);
            String string = (String) yearsBox.getSelectedItem();
            int temp2 = Integer.parseInt(string);
            statement.setInt(3, temp2);
            result = statement.executeQuery();


            String[] colsNames = {"Номер на прожекция", "Кино", "Филм",
                    "Ден", "Месец", "Година",
                    "Час"};

            table.setModel(new TModel(result, colsNames));
            if (table.getRowCount() == 0) {
                System.out.println("nqma projekcii");
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setDay(String day){

        this.day = day;
    }

    public String getDay(){
        return day;
    }

    public void setMonth(String month){

        this.month = month;
    }

    public String getMonth(){
        return month;
    }

    public void setYear(String year){

        this.year = year;
    }

    public String getYear(){
        return year;
    }


}
