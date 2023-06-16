import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class ParserLogsTest
{
    public static void main(String[] args) throws ObjectAlreadyCreatedException, ObjectUndefinedException, IOException
    {
        Simulation sim = new Simulation(LocalDate.now());
        ParserLogs.parseLogs(sim);
        Scanner sc = new Scanner(System.in);
        System.out.println("Output file path:");
        String path = sc.next();
        sim.serialize(path);
    }
}
