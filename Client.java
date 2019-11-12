import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;
import java.awt.*;


public class Client {

    Socket client;
    JTextField field;
    JTextArea area;
    BufferedWriter writer;
    BufferedReader reader;
    String message;

        public Client(){
            new Form();
            start();
            new Reader();

        }

            public void start(){
                try {
                    client = new Socket("127.0.0.1", 9000);
                    writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                    reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

                public class Reader extends Thread{

                        Thread  th;

                        public Reader(){
                            th = new Thread(this);
                            th.start();
                            }


                    @Override
                    public void run() {

                        System.out.println("Thread is run");
                            try {
                        while (true){

                                message = reader.readLine();

                                if(message != null) {
                                    System.out.println(message);
                                    area.append(message + "\n");
                                }

                            }
                            } catch (IOException e) {
                                e.printStackTrace();
                        }


                    }
                }



    public class Form{

        public Form(){
            JFrame frame = new JFrame("ShutApp");
            frame.setBounds(120,120,450,590);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            JPanel panel = new JPanel();
            frame.add(panel);
            area = new JTextArea(30,30);
            panel.add(area);
            field = new JTextField(30);
            panel.add(field);
            JButton button = new JButton("Send");
            panel.add(button);

            button.addActionListener(new SendButton());
            field.addKeyListener(new FieldKeySender());
            frame.setVisible(true);
        }

        public class SendButton implements ActionListener{


            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writer.write(field.getText());
                    writer.newLine();
                    writer.flush();

                    field.setText("");
                    field.grabFocus();
                    start();


                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        }

        public class FieldKeySender implements KeyListener{
            @Override
            public void keyTyped(KeyEvent e) {

                }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                try {
                    writer.write(field.getText());
                    writer.newLine();
                    writer.flush();

                    field.setText("");
                    field.grabFocus();
                    start();


                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        }


        }


    public static void main(String[] args) {
        new Client();
    }

}
