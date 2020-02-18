import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket=new DatagramSocket();
        DatagramPacket datagramPacket = null;
        InetAddress localhost = InetAddress.getByName("localhost");

        byte [] buf = new byte[100];
        char c = 'a';

        for (int i = 0; i < 3; i++)
        {
            System.out.print("Введите "+ (char)(c+i) +": ");
            int k=System.in.read(buf);
            datagramPacket=new DatagramPacket(buf, k, localhost,12345 );
            datagramSocket.send(datagramPacket);
        }

        datagramPacket=new DatagramPacket(buf,100);
        datagramSocket.receive(datagramPacket);

        double result =Double.parseDouble( new String(datagramPacket.getData()).trim());
        result = (double) Math.round(result * 100000d)/100000d;
        System.out.println("the answer is " + result);

        datagramSocket.close();
    }
}