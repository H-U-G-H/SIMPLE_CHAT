import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClientA
{
    JTextArea incoming; // ���� ������
    JTextField outgoing; // ���� �����
    BufferedReader reader; // ������� �����
    PrintWriter writer; // �������� �����
    Socket socket; // �����

    public static void main(String[] args) // �������� �����
    {
        new SimpleChatClientA().go(); // ������ �������
    }

    public void go()
    {
        JFrame frame = new JFrame("������� ���-������"); // �������� ����
        JPanel mainPanel = new JPanel(); // �������� ������

        incoming = new JTextArea(15, 50); // ���� ������
        incoming.setLineWrap(true); // ������� �����
        incoming.setWrapStyleWord(true); // ������� �� ������
        incoming.setEditable(false); // ������ �� ��������������
        JScrollPane scroller = new JScrollPane(incoming); // �������� ��� ���� ������
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // ������������ ���������
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // �������������� ���������

        outgoing = new JTextField(20); // ������������� ���������� ���� ����
        JButton sendButton = new JButton("���������"); // ������ ��� ����������� ��������� � ���
        sendButton.addActionListener(new SendButtonListener()); // ���������� ��������� ��� ������
        mainPanel.add(scroller); // �������� �������� �� ������
        mainPanel.add(outgoing); // �������� ��������� ���� ���� �� ������� ������
        mainPanel.add(sendButton); // �������� ������ �� ������� ������
        setUpNetworking(); // ����� ��������� ����

        Thread readerThread = new Thread(new IncomingReader()); // ����� ����� ��� ������
        readerThread.start(); // ������ ������

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel); // ���������� ������ � ����� ����
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���������� ��������� ��� �������� ����
        frame.setSize(400, 500); // ������ ��������� ����
        frame.setVisible(true); // ����� ��������� ���� �� �����
    }

    private void setUpNetworking() // ��������� ������� ����������
    {
        try
        {
            socket = new Socket("127.0.0.1", 5000); // ��������� ������
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream()); // ����� ����������
            reader = new BufferedReader(streamReader); // ������ �����
            writer = new PrintWriter(socket.getOutputStream()); // ���������� �������
            System.out.println("���� �������"); // ����� ��������� �� ������� ������
        }
        catch(IOException exception) // ��������� ���������� �����/������
        {
            System.out.println("�������� �� ����� �������� ����������"); // ����� ��������� �� ������
            exception.printStackTrace(); // ������ ���� ����������
        }
    } // OUT OF METHOD

    public class SendButtonListener implements ActionListener // ��������� ������
    {
        public void actionPerformed(ActionEvent event) // �������� ��� ����������� �������
        {
            try
            {
                writer.println(outgoing.getText()); // �������� � ����� ����� �� ���� ����
                writer.flush(); // ��������� ���������� ������ �� ������
            }
            catch (Exception exception) // ��������� ���� ����������
            {
                exception.printStackTrace(); // ������ ���� ����������
            }

            outgoing.setText(""); // �������� ��������� ����
            outgoing.requestFocus(); // ���������� ������ � ��������� ����
        }
    } // OUT OF INNER CLASS

    public class IncomingReader implements Runnable // ������
    {
        public void run() // ������������ ����� ���������� RUNNABLE
        {
            String message; // ��������� ���������� ��� �������� �������� ���������

            try
            {
                while ((message = reader.readLine()) != null) // ��� ������� ������, �������� Ũ � MESSAGE
                {
                    System.out.println("������: " + message); // ���������� ����� ��� ���������
                    incoming.append(message + "\n"); // APPEND() - ��������� ����� � ��� ��������� � ���� TEXTAREA
                }
            }
            catch(Exception exception) {exception.printStackTrace();} // ��������� ����������
        }
    }
}

