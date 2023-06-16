import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ParserCommands {

        public static void parseCommandsFile(Simulation sim, String file_path) throws ObjectAlreadyCreatedException, ObjectUndefinedException, PreviousDateException
        {
            int i = 0;
            List<String> linhas = lerFicheiro(file_path);
            String[] linhaPartida;
            ArrayList<String []> commands = new ArrayList<>();
            LocalDate firstDate = null, lastExtractedDate = null;
            for (String linha : linhas)
            {
                linhaPartida = linha.split(",", 8);
                if (!linhaPartida[0].trim().equals(""))
                {
                    if (i == 0)
                    {
                        firstDate = LocalDate.parse(linhaPartida[0].trim());
                        lastExtractedDate = firstDate;
                        commands.add(linhaPartida);
                    }
                    else
                    {
                        lastExtractedDate = LocalDate.parse(linhaPartida[0].trim());
                        if (firstDate.equals(lastExtractedDate))
                        {
                            commands.add(linhaPartida);
                        }
                        else
                        {
                            parseSameDateCommands(sim, commands);
                            commands = new ArrayList<>();
                            commands.add(linhaPartida);
                            firstDate = lastExtractedDate;
                        }
                    }
                    i++;
                }
            }
            if (commands.size() > 0)
            {
                parseSameDateCommands(sim, commands);
            }
        }

        public static List<String> lerFicheiro(String nomeFich)
        {
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

        public static void parseSameDateCommands(Simulation sim, List<String []> commandsList) throws ObjectAlreadyCreatedException, ObjectUndefinedException, PreviousDateException
        {
            String command;
            LocalDate newDate = LocalDate.parse(((commandsList.get(0))[0]).trim());
            for (String[] linhaPartida : commandsList)
            {
                command = linhaPartida[1].trim().toLowerCase();
                switch(command)
                {
                    case "new house":
                        parseNewHouse(sim, linhaPartida);
                        break;
                    case "new division":
                        parseNewDivision(sim, linhaPartida);
                        break;
                    case "new smartbulb":
                        parseNewSmartDevice(sim, linhaPartida);
                        break;
                    case "new smartcamera":
                        parseNewSmartDevice(sim, linhaPartida);
                        break;
                    case "new smartspeaker":
                        parseNewSmartDevice(sim, linhaPartida);
                        break;
                    case "change device":
                        parseChangeDevice(sim, linhaPartida);
                        break;
                    case "change energy provider":
                        parseChangeEnergyProvider(sim, linhaPartida);
                        break;
                    case "change tax":
                        parseChangeTax(sim, linhaPartida);
                        break;
                    case "change base value":
                        parseChangeBaseValue(sim, linhaPartida);
                        break;
                    case "turn devices on/off":
                        parseTurnDevicesOnOrOff(sim, linhaPartida);
                        break;
                    default:
                        System.out.println("Linha inválida.");
                        break;
                }
            }
            sim.advanceToDate(newDate);
        }

        public static void parseNewHouse(Simulation sim, String[] linhaPartida) throws ObjectAlreadyCreatedException, ObjectUndefinedException
        {
            int house_id = sim.houses_number()+1;
            String ep = read_EnergyProvider_String(linhaPartida[2]);
            String nome = linhaPartida[3].trim();
            String nif = linhaPartida[4].trim();
            sim.addHouse(house_id, ep, nome, nif);
        }

        public static void parseNewDivision(Simulation sim, String[] linhaPartida) throws ObjectAlreadyCreatedException, ObjectUndefinedException
        {
            int house_id = parseInt(linhaPartida[2]);
            String location = linhaPartida[3].trim();
            sim.addLocationToHouse(location, house_id);
        }

        public static boolean onOrOff(String state)
        {
            boolean r;
            if (state.toLowerCase().equals("on"))
            {
                r = true;
            }
            else
            {
                r = false;
            }
            return r;
        }

        public static void parseNewSmartDevice(Simulation sim, String[] linhaPartida) throws ObjectAlreadyCreatedException, ObjectUndefinedException
        {
            int device_id = sim.devices_number()+1;
            int house_id = parseInt(linhaPartida[2]);
            String location = linhaPartida[3].trim();
            boolean on = onOrOff(linhaPartida[4].trim());
            switch(linhaPartida[1].trim().toLowerCase())
            {
                case "new smartbulb":
                    Tone tone = Tone.getTone(linhaPartida[5].trim());
                    double dimension = Double.parseDouble(linhaPartida[6]);
                    double fixedDailyIntake = Double.parseDouble(linhaPartida[7]);
                    SmartBulb sb = new SmartBulb(device_id, on, tone, dimension, fixedDailyIntake);
                    sim.addDeviceToHouse(sb, location, house_id);
                    break;

                case "new smartcamera":
                    int resolution_width = parseInt(linhaPartida[5]);
                    int resolution_height = parseInt(linhaPartida[6]);
                    double fileSize = Double.parseDouble(linhaPartida[7]);
                    SmartCamera sc = new SmartCamera(device_id, on, resolution_width, resolution_height, fileSize);
                    sim.addDeviceToHouse(sc, location, house_id);
                    break;

                case "new smartspeaker":
                    int volume = parseInt(linhaPartida[5]);
                    String radio = linhaPartida[6].trim();
                    String brand = linhaPartida[7].trim();
                    SmartSpeaker ss = new SmartSpeaker(device_id, on, volume, radio, brand);
                    sim.addDeviceToHouse(ss, location, house_id);
                    break;

                default:
                    break;
            }
        }

        public static void parseChangeDevice(Simulation sim, String[] linhaPartida) throws ObjectUndefinedException
        {
            int house_id = parseInt(linhaPartida[2]);
            int device_id = parseInt(linhaPartida[3]);
            String param = linhaPartida[4].trim().toLowerCase();
            if (param.equals("on/off"))
            {
                boolean value1 = onOrOff(linhaPartida[5].trim());
                sim.getDevice(house_id, device_id).setOn(value1);
            }
            else if (param.equals("tone"))
            {
                Tone value2 = Tone.getTone(linhaPartida[5].trim());
                ((SmartBulb)sim.getDevice(house_id, device_id)).setTone(value2);
            }
            else if(param.equals("dimension"))
            {
                double dimension = Double.parseDouble(linhaPartida[5]);
                ((SmartBulb)sim.getDevice(house_id, device_id)).setDimension(dimension);
            }
            else if(param.equals("daily intake"))
            {
                double fixedDailyIntake = Double.parseDouble(linhaPartida[5]);
                ((SmartBulb)sim.getDevice(house_id, device_id)).setFixedDailyIntake(fixedDailyIntake);
            }
            else if(param.equals("resolution width"))
            {
                int resolution_width = parseInt(linhaPartida[5]);
                ((SmartCamera)sim.getDevice(house_id, device_id)).setResolution_width(resolution_width);
            }
            else if(param.equals("resolution height"))
            {
                int resolution_height = parseInt(linhaPartida[5]);
                ((SmartCamera)sim.getDevice(house_id, device_id)).setResolution_height(resolution_height);
            }
            else if(param.equals("file size"))
            {
                double fileSize = Double.parseDouble(linhaPartida[5]);
                ((SmartCamera)sim.getDevice(house_id, device_id)).setSaveFileSize(fileSize);
            }
            else if(param.equals("volume"))
            {
                int volume = parseInt(linhaPartida[5]);
                ((SmartSpeaker)sim.getDevice(house_id, device_id)).setVolume(volume);
            }
            else if(param.equals("radio"))
            {
                String radio = linhaPartida[5].trim();
                ((SmartSpeaker)sim.getDevice(house_id, device_id)).setRadio(radio);
            }
            else if(param.equals("brand"))
            {
                String brand = linhaPartida[5].trim();
                ((SmartSpeaker)sim.getDevice(house_id, device_id)).setBrand(brand);
            }
        }

        public static String read_EnergyProvider_String(String energy_Prov)
        {
            String new_EP;
            String energyProvider = energy_Prov.trim().toLowerCase();
            if (energyProvider.equals("edp") || energyProvider.equals("edp comercial"))
            {
                new_EP = new EDP().toString();
            }
            else if (energyProvider.equals("iberdrola"))
            {
                new_EP = new Iberdrola().toString();
            }
            else if (energyProvider.equals("galp") || energyProvider.equals("galp energia"))
            {
                new_EP = new Galp().toString();
            }
            else if (energyProvider.equals("endesa"))
            {
                new_EP = new Endesa().toString();
            }
            else if (energyProvider.equals("meo") || energyProvider.equals("meo energia"))
            {
                new_EP = new MEOEnergia().toString();
            }
            else
            {
                new_EP = energy_Prov;
            }
            return new_EP;
        }

        public static void parseChangeEnergyProvider(Simulation sim, String[] linhaPartida) throws ObjectUndefinedException
        {
            int house_id = parseInt(linhaPartida[2]);
            String energyProvider = read_EnergyProvider_String(linhaPartida[3]);
            sim.changeHouseProvider(house_id, energyProvider);
        }

        public static void parseChangeTax(Simulation sim, String[] linhaPartida) throws ObjectUndefinedException
        {
            String energyProvider = read_EnergyProvider_String(linhaPartida[2]);
            double tax = Double.parseDouble(linhaPartida[3]);
            sim.changeTax(energyProvider, tax);
        }

        public static void parseChangeBaseValue(Simulation sim, String[] linhaPartida) throws ObjectUndefinedException
        {
            String energyProvider = read_EnergyProvider_String(linhaPartida[2]);
            double baseValue = Double.parseDouble(linhaPartida[3]);
            sim.changeBaseValue(energyProvider, baseValue);
        }

        public static void parseTurnDevicesOnOrOff(Simulation sim, String[] linhaPartida) throws ObjectUndefinedException
        {
            int house_id = parseInt(linhaPartida[2]);
            String location = linhaPartida[3].trim();
            boolean on = onOrOff(linhaPartida[4].trim());
            sim.onAllDevicesInLocation(house_id, location, on);
        }
}
