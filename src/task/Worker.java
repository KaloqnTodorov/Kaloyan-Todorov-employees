package task;

import java.time.LocalDate;
import java.util.Objects;

public class Worker {
    private int empID;
    private int projectID;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    Worker(int empID, int projectID, LocalDate dateFrom, LocalDate dateTo) {
        this.empID = empID;
        this.projectID = projectID;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo == null ? LocalDate.now() : dateTo;
    }

    public int getEmpID() {
        return empID;
    }

    public int getProjectID() {
        return projectID;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "empID=" + empID +
                ", projectID=" + projectID +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
       Worker worker = (Worker) o;
        return empID == worker.empID && projectID == worker.projectID && Objects.equals(dateFrom, worker.dateFrom) && Objects.equals(dateTo, worker.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empID, projectID, dateFrom, dateTo);
    }
}
