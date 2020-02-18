
import java.net.*;
import java.io.*;

class Server {
    static int countclients = 0;
    public static void main(String args[]) throws IOException {
        ServerSocket sock = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            sock = new ServerSocket(1024);
            while (true) {
                Socket client = sock.accept();
                countclients++;
                System.out.println("=======================================");
                System.out.println("Client " + countclients + " connected");
                is = client.getInputStream();
                os = client.getOutputStream();
                boolean flag = true;
                while (flag) {
                    byte[] bytes = new byte[1024];
                    is.read(bytes);
                    String str = new String(bytes, "UTF-8");
                    str.trim();
                    String[] numbers = str.split(" ");
                    String m = "";
                    bytes = new byte[1024];
                    if(numbers.length >= 2) {
                        int a = Integer.parseInt(numbers[0]);
                        int b = Integer.parseInt(numbers[1]);

                        System.out.println("клиент прислал число " + a);
                        System.out.println("клиент прислал число " + b);

                        m += findGCF(a, b) + " ";
                        bytes = m.getBytes();
                        os.write(bytes);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error " + e.toString());
        } finally {
            is.close();
            os.close();
            sock.close();
            System.out.println("Client " + countclients + " disconnected");
        }
    }
    public static int findGCF(int a,int b)
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
}