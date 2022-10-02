import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer
{
    ArrayList clientOutputStreams;

    public class ClientHandler implements Runnable
    {
        BufferedReader reader;
        Socket socket;

        public ClientHandler(Socket clientSocket)
        {
            try
            {
                socket = clientSocket;
                InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(streamReader);
            }
            catch (Exception exception) {exception.printStackTrace();}
        } // OUT OF CONSTRUCTOR

        public void run()
        {
            String message;
            try
            {
                while ((message = reader.readLine()) != null)
                {
                    System.out.println("◊“≈Õ»≈: " + message);
                    tellEveryOne(message);
                }
            }
            catch (Exception exception) {exception.printStackTrace();}
        } // OUT OF METHOD
    } // OUT OF INNER CLASS

    public static void main(String[] args)
    {
        new ChatServer().go();
    } // OUT OF MAIN METHOD

    public void go()
    {
        clientOutputStreams = new ArrayList();
        try
        {
            ServerSocket serverSocket = new ServerSocket(5000);

            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);

                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
                System.out.println("—Œ≈ƒ»Õ≈Õ»≈ ”—“¿ÕŒ¬À≈ÕŒ...");
            }
        }
        catch (Exception exception) {exception.printStackTrace();}
    }

    public void tellEveryOne(String message)
    {
        Iterator iterator = clientOutputStreams.iterator();
        while (iterator.hasNext())
        {
            try
            {
                PrintWriter writer = (PrintWriter) iterator.next();
                writer.println(message);
                writer.flush();
            }
            catch (Exception exception) {exception.printStackTrace();}
        } // OUT OF LOOP
    } // OUT OF METHOD


}
