import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatClient
{
    JTextArea incoming; // онке бшбндю
    JTextField outgoing; // онке ббндю
    BufferedReader reader; // бундмни онрнй
    PrintWriter writer; // бшундмни онрнй
    Socket socket; // янйер

    public static void main(String[] args) // нямнбмни лернд
    {
        new ChatClient().go(); // гюосяй йкхемрю
    }

    public void go()
    {
        JFrame frame = new JFrame("оПНЯРНИ ВЮР-ЙКХЕМР"); // нямнбмне нймн
        JPanel mainPanel = new JPanel(); // нямнбмюъ оюмекэ

        incoming = new JTextArea(15, 50); // онке бшбндю
        incoming.setLineWrap(true); // оепемня ярпнй
        incoming.setWrapStyleWord(true); // оепемня он якнбюл
        incoming.setEditable(false); // гюопер мю педюйрхпнбюмхе
        JScrollPane scroller = new JScrollPane(incoming); // яйпнккеп дкъ онкъ бшбндю
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // бепрхйюкэмши яйпнккхмц
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // цнпхгнмрюкэмши яйпнккхмц

        outgoing = new JTextField(20); // хмхжхюкхгюжхъ рейярнбнцн онкъ вюрю
        JButton sendButton = new JButton("нРОПЮБХРЭ"); // ймнойю дкъ нропюбкемхъ яннаыемхи б вюр
        sendButton.addActionListener(new SendButtonListener()); // днаюбкемхе яксьюрекъ дкъ ймнойх
        mainPanel.add(scroller); // днаюбхрэ яйпнккеп мю оюмекэ
        mainPanel.add(outgoing); // днаюбхрэ рейярнбне онке вюрю мю цкюбмсч оюмекэ
        mainPanel.add(sendButton); // днаюбхрэ ймнойс мю цкюбмсч оюмекэ
        setUpNetworking(); // лернд мюярпнийх яерх

        Thread readerThread = new Thread(new IncomingReader()); // мнбши онрнй дкъ времхъ
        readerThread.start(); // гюосяй онрнйю

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel); // днаюбкемхе оюмекх б жемрп нймю
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // гюбепьемхе опнцпюллш опх гюйпшрхх нймю
        frame.setSize(400, 500); // пюглеп нямнбмнцн нймю
        frame.setVisible(true); // бшбнд нямнбмнцн нймю мю щйпюм
    }

    private void setUpNetworking() // мюярпнийю яеребшу опнрнйнкнб
    {
        try
        {
            socket = new Socket("127.0.0.1", 5000); // сярюмнбйх янйерю
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream()); // онрнй янедхмемхъ
            reader = new BufferedReader(streamReader); // жеомни онрнй
            writer = new PrintWriter(socket.getOutputStream()); // янедхмемхе онрнйнб
            System.out.println("яерэ янгдюмю..."); // бшбнд яннаыемхе на сдювмнл хяунде
        }
        catch(IOException exception) // напюанрйю хяйкчвемхи ббндю/бшбндю
        {
            System.out.println("опнакелю мю щрюое яеребнцн янедхмемхъ..."); // бшбнд яннаыемхъ на ньхайе
            exception.printStackTrace(); // онкмши осрэ хяйкчвемхъ
        }
    } // OUT OF METHOD

    public class SendButtonListener implements ActionListener // яксьюрекэ ймнойх
    {
        public void actionPerformed(ActionEvent event) // деиярбхъ опх мюярсокемхх янашрхъ
        {
            try
            {
                writer.println(outgoing.getText()); // днаюбхрэ б онрнй рейяр хг онкъ вюрю
                writer.flush(); // нропюбхрэ яндепфхлне онрнйю мю яепбеп
            }
            catch (Exception exception) // напюанрйю бяеу хяйкчвемхи
            {
                exception.printStackTrace(); // онкмши осрэ хяйкчвемхъ
            }

            outgoing.setText(""); // нвхярхрэ рейярнбне онке
            outgoing.requestFocus(); // сярюмнбхрэ йспянп б рейярнбне онке
        }
    } // OUT OF INNER CLASS

    public class IncomingReader implements Runnable // гюдювю
    {
        public void run() // едхмярбеммши лернд хмрептеияю RUNNABLE
        {
            String message; // бпелеммюъ оепелеммюъ дкъ упюмемхъ бундъыху яннаыемхи

            try
            {
                while ((message = reader.readLine()) != null) // опх мюкхвхх ярпнйх, днаюбхрэ е╗ б MESSAGE
                {
                    System.out.println("времхе: " + message); // йнмянкэмши бшбнд дкъ деаюцхмцю
                    incoming.append(message + "\n"); // APPEND() - днаюбкъер рейяр б сфе хлечыхияъ с онкъ TEXTAREA
                }
            }
            catch(Exception exception) {exception.printStackTrace();} // напюанрйю хяйкчвемхи
        } // OUT OF METHOD
    } // OUT OF INNER CLASS
} // OUT OF OUTER CLASS

