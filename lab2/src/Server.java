import java.io.*;
import java.net.*;


public  class Server
{
    public static void main(String[] args) throws IOException, InterruptedException {
        new Server();
    }
    double sum1=0,sum2=0;
    int port = 0;
    DatagramSocket datagramSocket;
    byte [] buf=new byte[100];
    DatagramPacket datagramPacket =new DatagramPacket(buf,100);

    Server()throws IOException, InterruptedException{
        datagramSocket =new DatagramSocket(12345);
        listen();
    }

    private void listen() throws IOException, InterruptedException
    {
        int a,b,c;

        datagramSocket.receive(datagramPacket);
        port = datagramPacket.getPort();
        String str = new String(datagramPacket.getData());
        str = str.substring( 0, str.indexOf('\n'));
        System.out.println( "number " + str + " recieved as a");
        a=Integer.parseInt(str);

        datagramSocket.receive(datagramPacket);
        str = new String(datagramPacket.getData());
        str = str.substring(0, str.indexOf('\n'));
        System.out.println("number "+str+" recieved as b");
        b = Integer.parseInt(str);

        datagramSocket.receive(datagramPacket);
        str = new String(datagramPacket.getData());
        str = str.substring(0, str.indexOf('\n'));
        System.out.println("number "+str+" recieved as c");
        c = Integer.parseInt(str);

        Thread thread1 = new Thread(new Runnable()
        {
            public void run()
            {
                for(int i = a; i <= b; i++)
                {
                    sum1 += Math.pow(i, i);
                }
            }
        })
                ,thread2=new Thread(new Runnable()
        {
            public void run()
            {
                for(int i = b;i <= c; i++)
                {
                    sum2 += (double)(2*i)/(i+1);
                }
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        sendBack();
    }
    private void sendBack() throws IOException
    {
        String str=String.valueOf(sum1 - sum2);
        byte [] send=str.getBytes();
        datagramPacket =new DatagramPacket(send, send.length, InetAddress.getByName("localhost"),port);
        datagramSocket.send(datagramPacket);
        datagramSocket.close();
    }
}