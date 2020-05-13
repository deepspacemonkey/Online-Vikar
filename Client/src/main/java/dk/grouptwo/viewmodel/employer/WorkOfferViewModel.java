package dk.grouptwo.viewmodel.employer;

import dk.grouptwo.model.EmployerModel;
import dk.grouptwo.model.WorkerModel;
import dk.grouptwo.model.objects.Address;
import dk.grouptwo.model.objects.Job;
import dk.grouptwo.model.objects.Worker;
import dk.grouptwo.utility.WorkTableData;
import dk.grouptwo.utility.WorkersTableData;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class WorkOfferViewModel {
    private EmployerModel model;
    private StringProperty title;
    private DoubleProperty salary;
    private IntegerProperty startHour;
    private IntegerProperty startMinutes;
    private ObjectProperty<LocalDate> startDate;
    private IntegerProperty endHour;
    private IntegerProperty endMinutes;
    private ObjectProperty<LocalDate> endDate;
    private StringProperty country;
    private StringProperty postCode;
    private StringProperty city;
    private StringProperty street;
    private StringProperty description;
    private IntegerProperty workersNeeded;

    private ObservableList<WorkersTableData> list;
    private StringProperty workerDateOfBirth;
    private StringProperty workerGender;
    private StringProperty workerLanguages;
    private StringProperty workerDescription;
    private StringProperty workerLicenses;

    private StringProperty error;

    private Job job;
    private WorkTableData data;

    public WorkOfferViewModel(EmployerModel model) {
        this.model = model;
        title = new SimpleStringProperty("");
        salary = new SimpleDoubleProperty(0);
        startHour = new SimpleIntegerProperty(0);
        startMinutes = new SimpleIntegerProperty(0);
        startDate = new SimpleObjectProperty<>(null);
        endHour = new SimpleIntegerProperty(0);
        endMinutes = new SimpleIntegerProperty(0);
        endDate = new SimpleObjectProperty<>(null);
        country = new SimpleStringProperty("");
        city = new SimpleStringProperty("");
        street = new SimpleStringProperty("");
        postCode = new SimpleStringProperty("");
        description = new SimpleStringProperty("");
        workersNeeded = new SimpleIntegerProperty(0);

        workerDateOfBirth = new SimpleStringProperty("");
        workerGender = new SimpleStringProperty("");
        workerLanguages = new SimpleStringProperty("");
        workerDescription = new SimpleStringProperty("");
        workerLicenses = new SimpleStringProperty("");
        list = createList();

        error = new SimpleStringProperty("");
    }

    public boolean validData() {
        //todo
        return true;
    }

    public boolean save() {
        try {
            if(validData()) {
                job.setJobTitle(title.get());
                job.setSalary(salary.get());
                job.setShiftStart(LocalDateTime.of(startDate.get(), LocalTime.of(startHour.get(), startMinutes.get())));
                job.setShiftEnd(LocalDateTime.of(endDate.get(), LocalTime.of(endHour.get(), endMinutes.get())));
                job.setLocation(new Address(country.get(), city.get(), street.get(), postCode.get()));
                job.setDescription(description.get());
                job.setWorkersNeeded(workersNeeded.get());
                job.setSelectedWorkers(getSelectedWorkers());
                model.updateWorkOffer(job);
                data.update(job); //updates the table data system-wide
                return true;
            }
            return false;
        } catch (Exception e) {
            error.set(e.getMessage());
            return false;
        }
    }

    public ArrayList<Worker> getSelectedWorkers() {
        return new ArrayList<Worker>();//todo
    }

    public void reset(WorkTableData data) {
        title.set(data.jobTitleProperty().get());
        salary.set(data.salaryProperty().get());
        startHour.set(data.startTimeProperty().get().getHour());
        startMinutes.set(data.startTimeProperty().get().getMinute());
        startDate.set(data.startTimeProperty().get().toLocalDate());
        endHour.set(data.endTimeProperty().get().getHour());
        endMinutes.set(data.endTimeProperty().get().getMinute());
        endDate.set(data.endTimeProperty().get().toLocalDate());
        country.set(data.getAddress().getCountry());
        city.set(data.getAddress().getCity());
        postCode.set(data.getAddress().getZip());
        street.set(data.getAddress().getStreet());
        description.set(data.getDescription());
        workersNeeded.set(data.numberOfWorkersProperty().get());
        error.set("");
        job = model.getJobById(data.getJobId());
        this.data = data;
    }

    public ObservableList<WorkersTableData> createList()
    {
        ObservableList<WorkersTableData> list = FXCollections.observableArrayList();
        ArrayList<Worker> workers = job.getApplicants();

        for (Worker worker: workers)
        {
            list.add(new WorkersTableData(worker));
        }
        return list;
    }

    public void selectWorker(WorkersTableData workersTableData)
    {
        Worker worker = model.getJobById()
    }

    public StringProperty titleProperty() {
        return title;
    }

    public DoubleProperty salaryProperty() {
        return salary;
    }

    public IntegerProperty startHourProperty() {
        return startHour;
    }

    public IntegerProperty startMinutesProperty() {
        return startMinutes;
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public IntegerProperty endHourProperty() {
        return endHour;
    }

    public IntegerProperty endMinutesProperty() {
        return endMinutes;
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty countryProperty() {
        return country;
    }

    public StringProperty postCodeProperty() {
        return postCode;
    }

    public StringProperty cityProperty() {
        return city;
    }

    public StringProperty streetProperty() {
        return street;
    }

    public IntegerProperty workersNeededProperty() {
        return workersNeeded;
    }

    public StringProperty errorProperty() {
        return error;
    }
}
