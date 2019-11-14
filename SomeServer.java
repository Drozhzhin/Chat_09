import java.io.*;
import java.net.Socket;

public class SomeServer extends Thread {
    Socket client;
    BufferedReader reader;
    BufferedWriter writer;
    String message;

    public SomeServer(Socket client) {
        this.client = client;
        try {
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                message = reader.readLine();
                Serv.messages.add(message);
                System.out.println(message);

                synchronized (SomeServer.class) {
                    for (SomeServer someServer : Serv.ss) {
                        someServer.seedless(message);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seedless(String messy) throws IOException {
        writer.write(messy + "\n");
        writer.flush();
    }

}
