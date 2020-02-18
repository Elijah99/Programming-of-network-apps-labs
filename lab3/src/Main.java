import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.LocalTime.now;

public class Main {

    private final static int workTime = 1; //hours
    static LocalTime endOfWorkTime;
    static AtomicInteger numberClients =  new AtomicInteger(0);


    public static void main(String[] args) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {

            System.out.println("Enter the 1st cashier service speed(mins for 1 client): ");
            //  min  for  1  client
            double firstServiceSpeed = Double.parseDouble(bufferedReader.readLine());

            System.out.println("Enter the 2st cashier service speed(mins for 1 client): ");
            //  min  for  1  client
            double secondServiceSpeed = Double.parseDouble(bufferedReader.readLine());

            System.out.println("Enter the 3st cashier service speed(mins for 1 client): ");
            //  min  for  1  client
            double thirdServiceSpeed = Double.parseDouble(bufferedReader.readLine());

            Cashier cashier1 = new Cashier(1, firstServiceSpeed),
                    cashier2 = new Cashier(2, secondServiceSpeed),
                    cashier3 = new Cashier(3, thirdServiceSpeed);

            Thread cashier1Thread = new Thread(cashier1);
            Thread cashier2Thread = new Thread(cashier2);
            Thread cashier3Thread = new Thread(cashier3);

            endOfWorkTime = now().plusMinutes(workTime);

            cashier1Thread.start();
            cashier2Thread.start();
            cashier3Thread.start();
            try{
            cashier1Thread.join();
            cashier2Thread.join();
            cashier3Thread.join();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            int clientsServedByFirst = cashier1.clientsServedByOne;
            int clientsServedBySecond = cashier2.clientsServedByOne;
            int clientsServedByThird = cashier3.clientsServedByOne;


            System.out.println("-------------------------------------------");
            System.out.println("Clients served by 1st cashier: " + clientsServedByFirst);
            System.out.println("Clients served by 2st cashier: " + clientsServedBySecond);
            System.out.println("Clients served by 3st cashier: " + clientsServedByThird);
            System.out.println("Total clients: " + numberClients);


        } catch (IOException e) {
            System.out.println("Wrong input!");
        }


    }
}

class Cashier implements Runnable
{
    private   double serviceSpeed;
    int clientsServedByOne = 0;
    private int id;
    @Override
    public void run() {
        try {
            while(Main.endOfWorkTime.isAfter(now())){
            Thread.sleep((long) serviceSpeed  * 1000);
            Main.numberClients.incrementAndGet();
            clientsServedByOne++;
            System.out.println("Client served by "+ id + " cashier!");
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    Cashier(int id,double serviceSpeed){
        this.serviceSpeed = serviceSpeed;
        this.id = id;
    }
}