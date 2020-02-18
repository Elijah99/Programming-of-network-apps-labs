package TCP;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientTCP{
    public static void main(String args[]){
        Connect client1 = new Connect(), client2 = new Connect();
        new Thread(()-> {
            client1.connect(81, 27);
        }).start();
        new Thread(()-> {
            client2.connect(26, 16);
        }).start();
     }}
class Connect{
    static final String options[]={"send","disconnect"};
    public static void connect(int a, int b){
        char c=0;
        String sign="";
        Socket sock;
        InputStream is;
        OutputStream os;
        try{
            sock=new Socket(InetAddress.getByName("localhost"),1024);

            is=sock.getInputStream();
            os=sock.getOutputStream();
            boolean cont=true;
            Scanner scanner = new Scanner(System.in);
            while(cont){
                String s = a + " " + b;
                byte sendmessage[];
                sendmessage = s.getBytes();
                os.write(sendmessage);

                byte readmessage[]=new byte[20];
                int k=is.read(readmessage);
                System.out.println("Greatest common factor for " + a + " and " + b + " : " + new String(readmessage,0,k));
                System.out.println("--------------------------");

                cont = false;
            }
            is.close();
            os.close();
            sock.close();
        }
        catch(Exception e){
            System.out.println("Error "+ e.toString());
        }}
}