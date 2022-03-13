package task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class Util {

    public static void printMapWorkers(Map<Integer, HashSet<Worker>> workers){
        for (Map.Entry<Integer, HashSet<Worker>> entry : workers.entrySet()) {
            System.out.println(entry.getKey());
            for (Worker worker : entry.getValue()) {
                System.out.print("    ");
                System.out.println(worker);
            }
        }
    }

    public static long calculateTimeSpentTogetherOfTwoWorkers(Worker one, Worker two, int projectID) throws IOException {
        if (one.getDateFrom().compareTo(two.getDateFrom()) < 0 && one.getDateTo().compareTo(two.getDateFrom()) > 0 ||
                one.getDateFrom().compareTo(two.getDateTo()) < 0 && one.getDateTo().compareTo(two.getDateTo()) > 0 ||
                one.getDateFrom().compareTo(two.getDateFrom()) < 0 && one.getDateTo().compareTo(two.getDateTo()) > 0 ||
                one.getDateFrom().compareTo(two.getDateFrom()) > 0 && one.getDateTo().compareTo(two.getDateTo()) < 0) {
            //writing down in file the two workers and the time spend together
            LocalDate start;
            LocalDate end;
            if (one.getDateFrom().compareTo(two.getDateFrom()) > 0) {
                start = one.getDateFrom();
            } else {
                start = two.getDateFrom();
            }
            if (one.getDateTo().compareTo(two.getDateTo()) > 0) {
                end = two.getDateTo();
            } else {
                end = one.getDateTo();
            }
            long days = ChronoUnit.DAYS.between(start, end);
            String line = one.getEmpID() + ", " + two.getEmpID() + ", " + projectID + ", " + days + "\n";
            File file2 = new File("CSVResults.txt");
            FileWriter writer = new FileWriter(file2, true);
            writer.write(line);
            writer.flush();


            Scanner scanner = new Scanner(file2);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] parameters = line.split(",");
                int resultEmpId1 = Integer.parseInt(parameters[0].trim());
                int resultEmpId2 = Integer.parseInt(parameters[1].trim());
                int resultProjectId = Integer.parseInt(parameters[2].trim());
                long daysTogether = Integer.parseInt(parameters[3].trim());

                if ((one.getEmpID() == resultEmpId1 || one.getEmpID() == resultEmpId2) &&
                        (two.getEmpID() == resultEmpId1 || two.getEmpID() == resultEmpId2) &&
                        projectID != resultProjectId) {
                    days += daysTogether;
                }
            }
            return days;
        }
        return 0;
    }

    public static void FromCSVToMap(File file,HashMap<Integer,HashSet<Worker>> workers) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        //putting workers from CSV in Map is working
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parameters = line.split(",");
            int empId = Integer.parseInt(parameters[0].trim());
            int projectId = Integer.parseInt(parameters[1].trim());
            LocalDate dateFrom = LocalDate.parse(parameters[2].trim());
            LocalDate dateTo = parameters[3].trim().equals("NULL") ? null : LocalDate.parse(parameters[3].trim());
            Worker worker = new Worker(empId, projectId, dateFrom, dateTo);

            if (!workers.containsKey(projectId)) {
                workers.put(projectId, new HashSet<Worker>());
            }
            workers.get(projectId).add(worker);
        }
    }
}
