package task;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {


    public static void main(String[] args) throws IOException {

        HashMap<Integer, HashSet<Worker>> workers = new HashMap<>();
        int empId1 = 0;
        int empId2 = 0;
        long longestPeriodInDays = 0L;
        File file = new File("CSVFile.txt");
        Util.FromCSVToMap(file, workers);
        Util.printMapWorkers(workers);

        for (Map.Entry<Integer, HashSet<Worker>> entry : workers.entrySet()) {
            if (entry.getValue().size() < 2) {
                continue;
            }
            if (entry.getValue().size() == 2) {
                ArrayList<Worker> workerArrayList = new ArrayList<>(entry.getValue());
                Worker one = workerArrayList.get(0);
                Worker two = workerArrayList.get(1);
                long daysTogether = Util.calculateTimeSpentTogetherOfTwoWorkers(one, two, entry.getKey());
                //comparing to the longest time spend together
                if (daysTogether > longestPeriodInDays) {
                    empId1 = one.getEmpID();
                    empId2 = two.getEmpID();
                    longestPeriodInDays = daysTogether;
                }
            }
            if (entry.getValue().size() > 2) {
                ArrayList<Worker> workerArrayList = new ArrayList<>(entry.getValue());
                for (int i = 0; i < workerArrayList.size(); i++) {
                    for (int j = i + 1; j < workerArrayList.size(); j++) {
                        long daysTogether = Util.calculateTimeSpentTogetherOfTwoWorkers(workerArrayList.get(i), workerArrayList.get(j), entry.getKey());
                        //comparing to the longest time spend together
                        if (daysTogether > longestPeriodInDays) {
                            empId1 = workerArrayList.get(i).getEmpID();
                            empId2 = workerArrayList.get(j).getEmpID();
                            longestPeriodInDays = daysTogether;
                        }
                    }
                }
            }
        }

        System.out.println("People who worked together for the longest period of days ");
        System.out.println(empId1 + ", " + empId2 + ", " + longestPeriodInDays);
    }

}
