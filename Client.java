 import java.io.*;
 import java.net.*;
 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.*;
 
 public class Client extends JFrame {
 
     private Socket socket;
     private BufferedReader in;
     private PrintWriter out;
     private JTextArea messageArea;
     private JTextField messageField, hostField, portField;
     private JButton connectButton, sendButton;
 
 
     public Client() {
         setTitle("Client");
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setSize(400, 300);
 
         messageArea = new JTextArea();
         messageArea.setPreferredSize(new Dimension(300, 170));
         JScrollPane scrollPane = new JScrollPane(messageArea);
 
         messageField = new JTextField("type here");
         messageField.setPreferredSize(new Dimension(300, 70));
         hostField = new JTextField("localhost");
         portField = new JTextField("5555");
 
         connectButton = new JButton("Connect");
         sendButton = new JButton("Send");
         sendButton.setEnabled(false);
 
         connectButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 if (connectButton.getText().equals("Connect")) {
                     connectToServer();
                 } else if (connectButton.getText().equals("Disconnect")) {
                     disconnectFromServer();
                 }
             }
         });
 
         sendButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 sendMessage();
             }
         });
 
         JPanel panel = new JPanel();
         panel.setLayout(new GridLayout(2, 2));
         panel.add(hostField);
         panel.add(portField);
         panel.add(connectButton);
         panel.add(sendButton);
 
         add(scrollPane, BorderLayout.SOUTH);
         add(messageField, BorderLayout.CENTER);
         add(panel, BorderLayout.NORTH);
     }
 
     private void connectToServer() {
         try {
             socket = new Socket(hostField.getText(), Integer.parseInt(portField.getText()));
             in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             out = new PrintWriter(socket.getOutputStream(), true);
 
            
             displayMessage("Connected to server");
             connectButton.setText("Disconnect");
             sendButton.setEnabled(true);
 
             new ClientThread().start();
 
         } catch (IOException e) {
             displayMessage("Error connecting to server: " + e.getMessage());
         }
     }
 
     private void disconnectFromServer() {
         try {
             if (socket != null && !socket.isClosed()) {
                 socket.close();
                 in.close();
                 out.close();
                 displayMessage("Disconnected from server.");
                 connectButton.setText("Connect");
                 sendButton.setEnabled(false);
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
     }    
 
     private void sendMessage() {
         String message = messageField.getText();
         // Send message with username prefix
         out.println(message);
         messageField.setText("");
     }
 
     private void displayMessage(String message) {
         SwingUtilities.invokeLater(() -> {
             messageArea.append(message + "\n");
         });
     }
 
     private class ClientThread extends Thread {
         public void run() {
             try {
                 while (true) {
                     String message = in.readLine();
                     if (message == null) {
                         break;
                     }
                     displayMessage(message);
                 }
 
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }
 
     public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
             new Client().setVisible(true);
         });
     }
 }
 
