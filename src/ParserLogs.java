import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParserLogs {

    public static void parseLogs(Simulation sim) throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        int id_devices = 1, id_casa = 0;
        List<String> linhas = lerFicheiro("logs.txt");
        String[] linhaPartida;
        String divisao = null;
        House casaMaisRecente = null;
        for (String linha : linhas) {
            linhaPartida = linha.split(":", 2);
            switch(linhaPartida[0])
            {
                case "Fornecedor":
                    sim.addProvider(linhaPartida[1]);
                    break;
                case "Casa":
                    id_casa++;
                    parseCasa(id_casa, linhaPartida[1], sim);
                    break;
                case "Divisao":
                    divisao = linhaPartida[1];
                    parseDivisao(id_casa, divisao, sim);
                    break;
                case "SmartBulb":
                    parseSmartBulb(id_devices, linhaPartida[1], sim, divisao, id_casa);
                    id_devices++;
                    break;
                case "SmartSpeaker":
                    parseSmartSpeaker(id_devices, linhaPartida[1], sim, divisao, id_casa);
                    id_devices++;
                    break;
                case "SmartCamera":
                    parseSmartCamera(id_devices, linhaPartida[1], sim, divisao, id_casa);
                    id_devices++;
                    break;
                default:
                    System.out.println("Linha inválida.");
                    break;
            }
        }
        System.out.println("Logs file parsing done!\n");
    }

    public static List<String> lerFicheiro(String nomeFich) {
        List<String> lines;
        try
        {
            lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8);
        }
        catch(IOException exc)
        {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public static void parseCasa(int id, String linha, Simulation sim) throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        String[] params;
        params = linha.split(",", 3);
        sim.addHouse(id, params[2], params[0], params[1]);
    }

    public static void parseDivisao(int id_casa, String linha, Simulation sim) throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        sim.addLocationToHouse(linha, id_casa);
    }

    public static void parseSmartBulb(int id_device, String linha, Simulation sim, String location, int house_id) throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        String[] params;
        params = linha.split(",", 3);
        Tone tone = Tone.getTone(params[0]);
        SmartBulb sb;
        if (id_device % 2 == 0)
        {
            int length = (int) (Math.log10(Double.parseDouble(params[2])) + 1);
           sb = new SmartBulb(id_device, false, tone, Double.parseDouble(params[1]), Double.parseDouble(params[2]) / Math.pow(10,length));
        }
        else
        {
            int length = (int) (Math.log10(Double.parseDouble(params[2])) + 1);
            sb = new SmartBulb(id_device, true, tone, Double.parseDouble(params[1]), Double.parseDouble(params[2]) / Math.pow(10,length));
        }
        sim.addDeviceToHouse(sb, location, house_id);
    }

    public static void parseSmartSpeaker(int id_device, String linha, Simulation sim, String location, int house_id) throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        String[] params;
        params = linha.split(",", 4);
        SmartSpeaker ss;
        if (id_device % 2 == 0)
        {
            ss = new SmartSpeaker(id_device, false, Integer.parseInt(params[0]), params[1], params[2]);
        }
        else
        {
            ss = new SmartSpeaker(id_device, true, Integer.parseInt(params[0]), params[1], params[2]);
        }
        sim.addDeviceToHouse(ss, location, house_id);
    }

    public static void parseSmartCamera(int id_device, String linha, Simulation sim, String location, int house_id) throws ObjectAlreadyCreatedException, ObjectUndefinedException {
        String[] params;
        String[] params_resolution;
        params = linha.split(",", 3);
        params_resolution = params[0].split("x", 2);
        SmartCamera sc;
        if (id_device % 2 == 0)
        {
            sc = new SmartCamera(id_device, false, Integer.parseInt(params_resolution[0]), Integer.parseInt(params_resolution[1]), Integer.parseInt(params[1]));
        }
        else
        {
            sc = new SmartCamera(id_device, true, Integer.parseInt(params_resolution[0]), Integer.parseInt(params_resolution[1]), Integer.parseInt(params[1]));
        }
        sim.addDeviceToHouse(sc, location, house_id);
    }
}