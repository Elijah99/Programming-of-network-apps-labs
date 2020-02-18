package DB;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class ClientMenu {
    static int showClientMenu() {
        System.out.println("1 - Add record");
        System.out.println("2 - Get all");
        System.out.println("0 - exit");

        int choice = 0;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            choice = Integer.parseInt(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return choice;
    }
}