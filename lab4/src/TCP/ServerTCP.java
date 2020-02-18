package TCP;

import java.net.*;
import java.util.StringTokenizer;
import java.io.*;

class ServerTCP{
    private static class ClientHandler implements Runnable
    {
        private int clientid=0;
        private InputStream is;
        private OutputStream os;
        private int a=0,b=0,gcf=0;
        private String sign="";
        private Socket client;
        public ClientHandler(int id,Socket s)
        {
            clientid=id;
            client=s;
        }
        public void run()
        {
            try
            {
                os=client.getOutputStream();
                is=client.getInputStream();
                boolean flag=true;
                while(flag==true){
                    int k;
                    byte clientMessage[]=new byte[100];
                    try
                    {
                        k=is.read(clientMessage);
                    }
                    catch(Exception e)
                    {
                        break;
                    }
                    String tempString=new String(clientMessage,0,k);
                    tempString=tempString.trim();
                    if(tempString.compareTo("disconnect")==0) {
                        System.out.println("message \"disconnect\" recieved from client");
                        flag=false;
                    }
                    else {
                        try
                        {
                            String[] s = tempString.split(" ");
                            a=Integer.parseInt(s[0]);
                            b=Integer.parseInt(s[1]);
                            gcf = findGCF(a,b);
                            String a = "" + gcf;
                            byte[] send = a.getBytes();
                            os.write(send);
                            System.out.println(gcf);
                        } catch(Exception e)
                        {
                            os.write(("Wrong message. "+e.toString()).getBytes());
                        }}}
                is.close();
                os.close();
                client.close();
            }catch(Exception e)
            {

            }
            System.out.println("Client "+clientid+" disconnected");
        }}
    static int countclients=0;
    private static int findGCF(int a,int b)
    {
        int gfc = 1;
        for(int i = 1; i < a || i < b; i++)
        {
            if(a%i == 0 && b%i == 0)
            {
                gfc = i;
            }
        }
        return gfc;
    }
    public static void main(String args[]){
        ServerSocket sock;

        try{
            sock=new ServerSocket(1024);

            while(true){
                Socket client=sock.accept();
                countclients++;

                System.out.println("Client  "+countclients+"  connected");
                new Thread(new ClientHandler(countclients,client)).start();
            }}
        catch(Exception e){
            System.out.println("Error "+ e.toString());
        }}}