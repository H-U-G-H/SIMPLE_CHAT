import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClientA
{
    JTextField outgoing; // ��������� ���� ����
    PrintWriter writer; // �������� �����
    Socket socket; // �����

    public void go()
    {
        JFrame frame = new JFrame("������� ���-������"); // �������� ����
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���������� ��������� ��� �������� ����
        JPanel mainPanel = new JPanel(); // �������� ������
        outgoing = new JTextField(20); // ������������� ���������� ���� ����
        JButton sendButton = new JButton("���������"); // ������ ��� ����������� ��������� � ���
        sendButton.addActionListener(new SendButtonListener()); // ���������� ��������� ��� ������
        mainPanel.add(outgoing); // �������� ��������� ���� ���� �� ������� ������
        mainPanel.add(sendButton); // �������� ������ �� ������� ������
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel); // ���������� ������ � ����� ����
        setUpNetworking(); // ����� ��������� ����
        frame.setSize(400, 500); // ������ ��������� ����
        frame.setVisible(true); // ����� ��������� ���� �� �����
    }

    private void setUpNetworking() // ��������� ������� ����������
    {
        try
        {
            socket = new Socket("127.0.0.1", 5000); // ��������� ������
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

    public static void main(String[] args) // �������� �����
    {
        new SimpleChatClientA().go(); // ������ �������
    }
}

