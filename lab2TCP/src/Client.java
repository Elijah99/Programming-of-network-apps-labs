
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.*;
class ClientTCP extends Frame implements ActionListener, WindowListener {
    TextField  tf2, tf3;
    TextArea ta;
    Label la2, la3, la4;
    static Socket sock = null;
    InputStream is = null;
    OutputStream os = null;
    static String ip = "127.0.0.1";
    static String host = "1024";
    public static void main(String args[]) {
        ClientTCP c = new ClientTCP();
        actionPerformed();
        c.GUI();
    }
    private void GUI() {
// super("Клиент");
        setTitle("КЛИЕНТ");
      //  tf = new TextField("127.0.0.1");//ip adress клиента
      //  tf1 = new TextField("1024");// port клиента
        tf2 = new TextField();
        tf3 = new TextField();
        ta = new TextArea();
       // la = new Label("IP ADRESS");
        //la1 = new Label("port");
        la2 = new Label("sending date");
        la3 = new Label("result ");
        la4 = new Label(" ");
      //  Button btn = new Button("connect ");
        Button btn1 = new Button("send ");
     //   tf.setBounds(200, 50, 70, 25);
     //   tf1.setBounds(330, 50, 70, 25);
        tf2.setBounds(150, 200, 50, 25);
        tf3.setBounds(210, 200, 50, 25);
        ta.setBounds(150, 300, 300, 100);
    //    btn.setBounds(50, 50, 70, 25);
        btn1.setBounds(50, 200, 70, 25);
      //  la.setBounds(130, 50, 150, 25);
        //la1.setBounds(280, 50, 150, 25);
        la2.setBounds(150, 150, 150, 25);
        la3.setBounds(160, 250, 150, 25);
        la4.setBounds(600, 10, 150, 25);
     //   add(tf);
    //    add(tf1);
        add(tf2);
        add(tf3);
    //    add(btn);
        add(btn1);
        add(ta);
      //  add(la);
      //  add(la1);
        add(la2);
        add(la3);
        add(la4);
        setSize(600, 600);
        setVisible(true);
        addWindowListener(this);
      //  btn.addActionListener(al);
        btn1.addActionListener(this);
        tf2.getText();
        tf3.getText();
    }
    public void windowClosing(WindowEvent we) {
        if (sock != null && !sock.isClosed()) {
            try {
                sock.close();
            } catch (IOException e) {
            }
        }
        this.dispose();
    }
    public void windowActivated(WindowEvent we) {}   ;
    public void windowClosed(WindowEvent we) {};
    public void windowDeactivated(WindowEvent we) {};
    public void windowDeiconified(WindowEvent we) {}   ;
    public void windowIconified(WindowEvent we) {};
    public void windowOpened(WindowEvent we) { } ;
    public void actionPerformed(ActionEvent e) {
        if (sock == null) {
            return;
        }
        try {
            is = sock.getInputStream();
            os = sock.getOutputStream();
            String numbers = "";
            numbers += tf2.getText() + " ";
            numbers += tf3.getText() + " ";
            os.write(numbers.getBytes());
            byte[] bytes = new byte[1024];
            is.read(bytes);
            String str = new String(bytes, "UTF-8");
            ta.append("Greatest common factor: " + str + "\n");

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                os.close();
                is.close();
                sock.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


        public static void actionPerformed() {
            try {
                sock = new Socket(InetAddress.getByName(ip), Integer.parseInt(host));
            } catch (NumberFormatException e) {
            } catch (UnknownHostException e) {
            } catch (IOException e) {
            }}
}

