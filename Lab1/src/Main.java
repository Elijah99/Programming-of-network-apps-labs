import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException
    {
        ArrayList<Manager> arr= new ArrayList<Manager>();
        arr = readFile();
        while (true) {
            System.out.println("Что делаем ?\n " +
                            "1 - Выводим список\n " +
                            "2 - Добавляем запись\n " +
                            "3 - Удаляем запись\n " +
                            "4 - Считаем средний возраст\n " +
                            "5 - Считаем кол-во справляющихся\n " +
                            "0 - Завершаем программу");
            DataInputStream dis = new DataInputStream(System.in);
            int choose = Integer.parseInt(dis.readLine());
            switch (choose) {
                case 1:
                    showAll(arr);
                    break;
                case 2:
                    addManager(arr);
                    writeFile(arr);
                    break;
                case 3:
                    dropManager(arr);
                    writeFile(arr);
                    break;
                case 4:
                    averageAge(arr);
                    break;
                case 5:
                    quantityCope(arr);
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Следует ввести релевантное значение");
                    Main.main(args);
            }
        }
    }

    public static void showAll(ArrayList<Manager> arr)
    {
        if(arr.isEmpty()) System.out.println("Список пуст");
        for(Manager man: arr)
        {
            System.out.println((arr.lastIndexOf(man) +1)+ "   " + man.toString());
        }
    }
    public static void addManager(ArrayList<Manager> arr) throws IOException
    {
        DataInputStream dis = new DataInputStream(System.in);
        Manager man = new Manager();
        System.out.println("Введите фамилию: ");
        man.surname = dis.readLine();
        System.out.println("Введите возраст: ");
        man.age = Integer.parseInt(dis.readLine());
        System.out.println("Справляется ли с обязанностями ?(Y/N): ");
        String str = dis.readLine();
        while(man.cope == null) {
            if (str.equalsIgnoreCase("Y")) man.cope = true;
            else if (str.equalsIgnoreCase("N")) man.cope = false;
            else {
                System.out.println("Введите релевантное значение");
                System.out.println("Справляется ли с обязанностями ?(Y/N): ");
                str = dis.readLine();
            }
        }
        arr.add(man);
    }
    public static void writeFile(ArrayList<Manager> arr) throws IOException
    {
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("Manager.dat") );
        for(Manager man: arr)
        {
            oos.writeObject(man);
        }
        oos.flush();
        oos.close();
    }
    public static ArrayList<Manager> readFile() throws IOException
    {
        ArrayList<Manager> arr = new ArrayList<>();

        ObjectInputStream ois;
        ois = new ObjectInputStream(
                new FileInputStream("Manager.dat"));
        Manager manager = new Manager();
       while(true)
       {
           try {
               arr.add((Manager) ois.readObject());
           }
           catch(Exception ex) {
               // System.out.println(ex.getMessage());
               break;
           }
       }

        return arr;
    }

    public static float averageAge(ArrayList<Manager> arr)
    {
        float avgAge = 0; int i = 0;
        for( i = 0; i < arr.size(); i++)
        {
            avgAge += arr.get(i).age;
        }
        avgAge /= i;
        System.out.println("Средний возраст работников: " + avgAge);
        return avgAge;
    }
    public static int quantityCope(ArrayList<Manager> arr)
    {
        int count = 0;
        for(int i = 0; i < arr.size(); i++)
            if(arr.get(i).cope) count++;
        System.out.println("Кол-во справляющихся: " + count);
        return count;
    }
    public static void dropManager(ArrayList<Manager> arr) throws IOException
    {
        DataInputStream dis = new DataInputStream(System.in);
        System.out.print("Введите номер записи: ");
        int index = Integer.parseInt(dis.readLine());
        arr.remove(index-1);
    }
}
