import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class View {
    public static Boolean beggin (){
        System.out.println("Load file (Y/N):");
        Scanner sc = new Scanner(System.in);
        String carregar = sc.nextLine();
        if (carregar.equals("Y")||carregar.equals("y"))
        {
            return true;
        }
        return false;
    }

    public static int MenuInicial(LocalDate date)
    {
        StringBuilder sb = new StringBuilder("\n-----------Main Menu----------- "+date+"\n");
        sb.append("1  - Save state\n");
        sb.append("2  - House Menu\n");
        sb.append("3  - Provider Menu\n");
        sb.append("4  - Advance n days\n");
        sb.append("5  - Advance to date\n");
        sb.append("6  - House that payed the most\n");
        sb.append("7  - House that consumed the most\n");
        sb.append("8  - Provider with the biggest volume\n");
        sb.append("9  - Bills issued by provider\n");
        sb.append("10 - Order the biggest consumers in a period\n");
        sb.append("11 - Simulation commands from file\n");
        sb.append("0  - Exit\n\n");
        sb.append("Option: ");
        System.out.println(sb.toString());
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static int HouseMenu(){
        StringBuilder sb = new StringBuilder("\n-----------House Menu-----------\n");
        sb.append("1 - Add house\n");
        sb.append("2 - View/Edit house\n");
        sb.append("3 - View all houses\n");
        sb.append("0 - Return to main menu\n\n");
        sb.append("Option: ");
        System.out.println(sb.toString());
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static void anounceHouses(){
        System.out.println("\n----------------All Houses----------------");
    }
    public static void printHouses(int id,String owner,String provider){
        System.out.println("ID: "+id+"\n");
        System.out.println("Owner: "+owner+"\n");
        System.out.println("Provider: "+provider);
        System.out.println("------------------------------------------");
    }

    public static int houseId(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nHouse ID:");
        return sc.nextInt();
    }

    public static int houseProvider() {
        Scanner sc = new Scanner(System.in);
        StringBuilder edithouse = new StringBuilder("\nChose the provider:\n\n");
        edithouse.append("1 - EDP Comercial\n");
        edithouse.append("2 - Galp Energia\n");
        edithouse.append("3 - Iberdrola\n");
        edithouse.append("4 - Endesa\n");
        edithouse.append("5 - MEO Energia\n\n");
        edithouse.append("Option: ");
        System.out.println(edithouse);
        return sc.nextInt();
    }

    public static String ownerName(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Owner name: ");
        return sc.nextLine().trim();
    }

    public static String ownerNif()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Owner NIF: ");
        return sc.nextLine().trim();
    }

    public static int editHouseMenu(){
        StringBuilder edithouse = new StringBuilder("\n-----------View/Edit House-----------\n");
        edithouse.append("1 - View house\n");
        edithouse.append("2 - Add division\n");
        edithouse.append("3 - Add device\n");
        edithouse.append("4 - Modify device\n");
        edithouse.append("5 - Turn all devices in a room on or off\n");
        edithouse.append("6 - Change Energy Provider\n");
        edithouse.append("0 - Return to house menu\n\n");
        edithouse.append("Option: ");
        System.out.println(edithouse.toString());
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static void housestate(Map<Integer, SmartDevice> devices ,Map<String, Set<Integer>> location, String energyProvider)
    {
        int i = 0;
        System.out.println("\n--------------House constitution------------");
        System.out.println("\nEnergy Provider: " + energyProvider + "\n");
        for (String s : location.keySet())
        {
            if (i==0)
            {
                System.out.println("--------------------------------------------");
                i++;
            }
            System.out.println("Division: "+s);
            System.out.println("\n");
            for(Integer id: location.get(s)){
                SmartDevice sd = devices.get(id);
                System.out.println(sd);
                System.out.println("\n");
            }
            System.out.println("--------------------------------------------");
        }
    }

    public static String askdivision(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Division: ");
        return sc.nextLine().trim();
    }

    public static int device(){
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder("\n--------Device to add-------\n");
        sb.append("1 - SmartCamera\n");
        sb.append("2 - SmartBulb\n");
        sb.append("3 - SmartSpeaker\n");
        sb.append("0 - Return\n\n");
        sb.append("Option: ");
        System.out.println(sb.toString());
        return sc.nextInt();
    }

    public static boolean onOroff(){
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder("\n-----On or Off ------\n");
        sb.append("1 - On\n");
        sb.append("2 - Off\n");
        sb.append("\nOption:");
        System.out.println(sb);
        if(sc.nextInt()==1){return true;}
        else return false;
    }

    public static int deviceID()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Device ID:");
        return sc.nextInt();
    }

    public static int resolution_w()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nResolution width:");
        return sc.nextInt();
    }

    public static int resolution_h()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Resolution height:");
        return sc.nextInt();
    }

    public static double filesize()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("File size: ");
        return sc.nextDouble();
    }

    public static double dimension(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Dimension:");
        return sc.nextDouble();
    }

    public static double dailyintake(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Fixed daily intake:");
        return sc.nextDouble();
    }

    public static int volume(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nVolume:");
        return sc.nextInt();
    }

    public static String radio(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Radio:");
        return sc.nextLine().trim();
    }

    public static String brand(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Brand:");
        return sc.nextLine().trim();
    }
    public static int ProviderMenu(){
        StringBuilder sb = new StringBuilder("\n-----------Energy Provider Menu-----------\n");
        sb.append("1 - Change tax\n");
        sb.append("2 - Change base value\n");
        sb.append("3 - Providers\n");
        sb.append("0 - Return to main menu\n\n");
        sb.append("Option: ");
        System.out.println(sb.toString());
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
    public static void anounceproviders(){
        System.out.println("\n---------------Providers---------------");
    }
    public static void printProvider(String provider,double base ,double tax){
        System.out.println(provider+"\nBase Value: "+base +"\nTax: "+tax+"\n");
    }
    public static double askTax(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Tax: ");
        return sc.nextDouble();
    }

    public static double baseValue(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Base value: ");
        return sc.nextDouble();
    }
    public static String pressEnter(){
        System.out.println("\nPress enter to continue...");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    public static String askFile(){
        System.out.println("File name: ");
        Scanner sc = new Scanner(System.in);
        String file = sc.nextLine().trim();
        return file;
    }

    public static int ndays(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nDays you want to advance:");
        return sc.nextInt();
    }

    public static LocalDate newDate(){
        Scanner sc = new Scanner(System.in);
        System.out.println("New date (yyyy-mm-dd):");
        return LocalDate.parse(sc.nextLine());
    }

    public static void mostExpensiveHouse(int houseid,String ownername,String nif,EnergyProvider provider,double money){
        StringBuilder housespent = new StringBuilder("\nHouse that payed the most: "+houseid+" (ID)");
        housespent.append("\nHouse owner: " + ownername);
        housespent.append("\nOwner NIF: " + nif);
        housespent.append("\nEnergy provider: " + provider);
        housespent.append("\nMoney spent: " + (double) Math.round(money*100)/100 + " €");
        System.out.println(housespent.toString());
    }

    public static void mostConsumeHouse(int houseid,String ownername,String nif,EnergyProvider provider,double energy){
        StringBuilder housespent = new StringBuilder("\nHouse that consumed the most: "+houseid+" (ID)");
        housespent.append("\nHouse owner: " + ownername);
        housespent.append("\nOwner NIF: " + nif);
        housespent.append("\nEnergy provider: " + provider);
        housespent.append("\nEnergy spent: " + (double) Math.round(energy*100)/100 + " kWh");
        System.out.println(housespent.toString());
    }

    public static void printProvider(EnergyProvider provider)
    {
        StringBuilder prov = new StringBuilder("\nThe provider with biggest volume is: ");
        prov.append(provider.toString());
        prov.append(" (");
        prov.append((double) Math.round(provider.getFactoringVolume()*100)/100);
        prov.append(" €)");
        System.out.println(prov.toString());
    }

    public static void printBill(List<Bill> list){
        System.out.println("\n-----------------------Bills-----------------------\n");
        for(Bill b : list){
            System.out.println(b.toString());
            System.out.println("\n");
        }
    }

    public static int SmartSpeakerMenu(){
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder("\n--------SmartSpeaker modification-------\n");
        sb.append("1 - Change volume\n");
        sb.append("2 - Change radio\n");
        sb.append("3 - Turn on or off the device\n");
        sb.append("0 - Return\n\n");
        sb.append("Option: ");
        System.out.println(sb.toString());
        return sc.nextInt();
    }

    public static int SmartBulbMenu(){
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder("\n--------SmartBulb modification-------\n");
        sb.append("1 - Change tone\n");
        sb.append("2 - Change dimension\n");
        sb.append("3 - Change fixed daily intake\n");
        sb.append("4 - Turn on or off the device\n");
        sb.append("0 - Return\n\n");
        sb.append("Option: ");
        System.out.println(sb.toString());
        return sc.nextInt();
    }

    public static int tone(){
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder("\nChoose the tone:\n");
        sb.append("1 - Warm\n");
        sb.append("2 - Neutral\n");
        sb.append("3 - Cold\n");
        sb.append("\nOption: ");
        System.out.println(sb);
        return sc.nextInt();
    }

    public static int SmartCameraMenu(){
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder("\n--------SmartCamera modification-------\n");
        sb.append("1 - Change resolution width\n");
        sb.append("2 - Change resolution height\n");
        sb.append("3 - Change file size\n");
        sb.append("4 - Turn on or off the device\n");
        sb.append("0 - Return\n\n");
        sb.append("Option: ");
        System.out.println(sb.toString());
        return sc.nextInt();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void houseExist(){
        System.out.println("House does not exist\n");
    }

    public static void error(Exception e){
        System.out.println(e);
        System.out.println("\n");
    }

    public static LocalDate initDate(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Insert inicial date(yyyy-mm-dd):");
        LocalDate date = LocalDate.parse(sc.nextLine());
        return date;
    }

    public static LocalDate finalDate(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Insert final date(yyyy-mm-dd):");
        LocalDate date = LocalDate.parse(sc.nextLine());
        return date;
    }

    public static void BiggestConsumers(LocalDate i,LocalDate f){
        System.out.println("\nBiggest consumers between "+i+" and "+f+":\n");
    }

    public static void printBigConsumers(int id,double consume)
    {
        System.out.println("ID: " + id);
        System.out.println("Consume: " + (double) Math.round(consume*100)/100 + " kWh");
        System.out.print("------------------------------------------\n");
    }

}