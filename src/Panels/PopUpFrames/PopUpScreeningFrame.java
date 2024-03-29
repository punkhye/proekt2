package Panels.PopUpFrames;

import DBconnection.DBConnection;
import Panels.TModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PopUpScreeningFrame extends JFrame {

    String idScreening;
    String day;
    String month;
    String year;
    String time;

    static Connection conn = null;
    static ResultSet resultSet = null;
    static PreparedStatement statement = null;
    static JTable table = new JTable();
    JScrollPane scroll = new JScrollPane(table);

    JLabel label = new JLabel("Double click върху прожекцията, на която искате да промените датата ");


    JButton closeButton = new JButton("Затвори");

    public PopUpScreeningFrame(){
        this.setSize(800,400);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(3,1));

        this.add(label);
        this.add(scroll);
        this.add(closeButton);

        table.addMouseListener(new TableDoubleClick());
        closeButton.addActionListener(new CloseAction());

        buildTable();

        this.setVisible(true);

    }

    void buildTable(){
        conn= DBConnection.getConnection();
        String str;
        str="SELECT Screenings.screening_id, Cinemas.cinema_name, Movies.movie_name, Screenings.screening_day, Screenings.screening_month, Screenings.screening_year, Screenings.screening_time from SCREENINGS,CINEMAS, Movies where Screenings.cinema_id=Cinemas.cinema_id and Screenings.movie=movie_id";

        try {
            statement=conn.prepareStatement(str);
            resultSet=statement.executeQuery();

            String[] colsNames = { "Номер на прожекция", "Кино", "Филм",
                    "Ден", "Месец", "Година",
                    "Час"};
            table.setModel(new TModel(resultSet, colsNames));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    class CloseAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    class TableDoubleClick implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {

                idScreening = null;
                day = null;
                month = null;
                year = null;
                time = null;


                int row = table.getSelectedRow();
                if (row != -1) {

                    Object idObject = table.getValueAt(row, 0);
                    Object dayObject = table.getValueAt(row, 3);
                    Object monthObject = table.getValueAt(row, 4);
                    Object yearObject = table.getValueAt(row, 5);
                    Object timeObject = table.getValueAt(row, 6);

                    idScreening = String.valueOf(idObject);
                    day = String.valueOf(dayObject);
                    month = String.valueOf(monthObject);
                    year = String.valueOf(yearObject);
                    time = String.valueOf(timeObject);



                    System.out.println(idScreening);
                    System.out.println(day);
                    System.out.println(month);
                    System.out.println(year);
                    System.out.println(time);

                }

                UpdateScreeningFrame frame = new UpdateScreeningFrame(idScreening, day, month, year, time);
                System.out.println("double clicked");
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {

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

    public class UpdateScreeningFrame extends JFrame{

        private String id;
        private String day;
        private String month;
        private String year;
        private String time;

        JLabel dayLabel = new JLabel("Ден: ");
        static JSpinner dayPicker = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
        JLabel monthLabel = new JLabel("Месец: ");
        static String[] monthsArr = { "Януари", "Февруари", "Март",
                "Април", "Май", "Юни", "Юли", "Август",
                "Септември", "Октомври", "Ноември", "Декември" };

        JComboBox<String> monthsBox = new JComboBox<>(monthsArr);
        JLabel yearLabel = new JLabel("Година: ");
        static String[] yearsArr = { "2024", "2025", "2026",
                "2027", "2028", "2029", "2030", };

        JComboBox<String> yearsBox = new JComboBox<>(yearsArr);
        JLabel timeLabel = new JLabel("Час: ");
        static String[] timeArr = { "10:30 ч", "12:40 ч", "14:20 ч",
                "15:00 ч", "16:30 ч", "17:50 ч",
                "21:20 ч", "22:20 ч" };

        JComboBox<String> timeBox = new JComboBox<>(timeArr);

        JButton updateButton = new JButton("Ок");
        JButton closeButton = new JButton("Отказ");

        JPanel upPanel = new JPanel(new GridLayout(4,2));

        JPanel downPanel = new JPanel(new FlowLayout());

        public UpdateScreeningFrame(String idUp, String dayUp, String monthUp, String yearUp, String timeUp){
            setId(idUp);
            setDay(dayUp);
            setMonth(monthUp);
            setYear(yearUp);
            setTime(timeUp);
            this.setSize(500,400);
            this.setLocationRelativeTo(null);
            this.setLayout(new GridLayout(2,1));
            this.setTitle("Промяна на датата на прожекция номер " +"'" + id + "'");

            //up panel
            this.add(upPanel);
            upPanel.add(dayLabel);
            dayPicker = new JSpinner(new SpinnerNumberModel(Integer.parseInt(day), 1, 31, 1));
            upPanel.add(dayPicker);
            upPanel.add(monthLabel);
            monthsBox.setSelectedItem(month);
            upPanel.add(monthsBox);
            upPanel.add(yearLabel);
            yearsBox.setSelectedItem(year);
            upPanel.add(yearsBox);
            upPanel.add(timeLabel);
            timeBox.setSelectedItem(time);
            upPanel.add(timeBox);


            //down panel
            this.add(downPanel);

            downPanel.add(updateButton);
            updateButton.addActionListener(new UpdateAction());
            downPanel.add(closeButton);
            closeButton.addActionListener(new CloseAction());

            this.setVisible(true);
        }


        public void setId(String id){

            this.id = id;
        }

        public String getId(){
            return id;
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

        public void setTime(String time){
            this.time = time;
        }

        public String getTime(){
            return time;
        }

        class UpdateAction implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                conn= DBConnection.getConnection();
                String sql="update Screenings set screening_day=?, screening_month=?, screening_year=?, screening_time=? where screening_id=?";

                try {
                    statement= conn.prepareStatement(sql);

                    int screeningDay = (int) dayPicker.getValue();
                    statement.setInt(1, screeningDay);

                    String screeningMonth = (String) monthsBox.getSelectedItem();
                    statement.setString(2, screeningMonth);

                    String temp = (String) yearsBox.getSelectedItem();
                    int screeningYear = Integer.parseInt(temp);
                    statement.setInt(3, screeningYear);

                    String screeningTime = (String) timeBox.getSelectedItem();
                    statement.setString(4, screeningTime);

                    int updatedId = Integer.parseInt(id);
                    statement.setInt(5, updatedId);

                    statement.execute();

                    dispose();


                    System.out.println("бе май има промяна");
                    buildTable();


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

    }

}
