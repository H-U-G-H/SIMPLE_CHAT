import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClientA
{
    JTextField outgoing; // рейярнбне онке вюрю
    PrintWriter writer; // бшундмни онрнй
    Socket socket; // янйер

    public void go()
    {
        JFrame frame = new JFrame("оПНЯРНИ ВЮР-ЙКХЕМР"); // нямнбмне нймн
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // гюбепьемхе опнцпюллш опх гюйпшрхх нймю
        JPanel mainPanel = new JPanel(); // нямнбмюъ оюмекэ
        outgoing = new JTextField(20); // хмхжхюкхгюжхъ рейярнбнцн онкъ вюрю
        JButton sendButton = new JButton("нРОПЮБХРЭ"); // ймнойю дкъ нропюбкемхъ яннаыемхи б вюр
        sendButton.addActionListener(new SendButtonListener()); // днаюбкемхе яксьюрекъ дкъ ймнойх
        mainPanel.add(outgoing); // днаюбхрэ рейярнбне онке вюрю мю цкюбмсч оюмекэ
        mainPanel.add(sendButton); // днаюбхрэ ймнойс мю цкюбмсч оюмекэ
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel); // днаюбкемхе оюмекх б жемрп нймю
        setUpNetworking(); // лернд мюярпнийх яерх
        frame.setSize(400, 500); // пюглеп нямнбмнцн нймю
        frame.setVisible(true); // бшбнд нямнбмнцн нймю мю щйпюм
    }

    private void setUpNetworking() // мюярпнийю яеребшу опнрнйнкнб
    {
        try
        {
            socket = new Socket("127.0.0.1", 5000); // сярюмнбйх янйерю
            writer = new PrintWriter(socket.getOutputStream()); // янедхмемхе онрнйнб
            System.out.println("яЕРЭ ЯНГДЮМЮ"); // бшбнд яннаыемхе на сдювмнл хяунде
        }
        catch(IOException exception) // напюанрйю хяйкчвемхи ббндю/бшбндю
        {
            System.out.println("оПНАКЕЛЮ МЮ ЩРЮОЕ ЯЕРЕБНЦН ЯНЕДХМЕМХЪ"); // бшбнд яннаыемхъ на ньхайе
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

    public static void main(String[] args) // нямнбмни лернд
    {
        new SimpleChatClientA().go(); // гюосяй йкхемрю
    }
}

