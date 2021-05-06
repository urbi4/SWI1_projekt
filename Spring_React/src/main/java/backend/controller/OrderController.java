package backend.controller;

import backend.entity.*;
import backend.repository.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Controls order managing, solves requests from frontend
 */
@RestController
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrdersRepository repository;
    private final AddressRepository addressRepository;
    private final TimeRangeRepository timeRangeRepository;
    private final PersonRepository personRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final VehicleRepository vehicleRepository;
    private final OrdersHasProblemRepository ordersHasProblemRepository;
    private final ProblemRepository problemRepository;

    public OrderController(OrdersRepository repository, AddressRepository addressRepository, TimeRangeRepository timeRangeRepository, PersonRepository personRepository, VehicleTypeRepository vehicleTypeRepository, VehicleRepository vehicleRepository, OrdersHasProblemRepository ordersHasProblemRepository, ProblemRepository problemRepository) {
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.timeRangeRepository = timeRangeRepository;
        this.personRepository = personRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.vehicleRepository = vehicleRepository;
        this.ordersHasProblemRepository = ordersHasProblemRepository;
        this.problemRepository = problemRepository;
    }

    /**
     * Solves request from frontend for available times at selected date
     * @param value Selected date
     * @return Returns times
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/times")
    ArrayList<String> getTimes(@RequestParam(name = "value", required = false, defaultValue = "default") String value) {

        List<Orders> list = this.repository.findAll();

        LocalDateTime ldt = LocalDateTime.parse(value.substring(0, value.indexOf(".")), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        ldt = ldt.plusDays(1);
        String finalDate = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        list.removeIf(e -> !(e.getDate().toString().equals(finalDate)));

        List<TimeRange> trList = timeRangeRepository.findAll();
        ArrayList<String> allTimes = new ArrayList<>();
        for (TimeRange timeRange : trList) {
            allTimes.add(timeRange.getTimeOfStart().toString().substring(0, 5) + " - " + timeRange.getTimeOfEnd().toString().substring(0, 5));
        }
        Set<String> setOfTimes = new HashSet<>();
        for (Orders orders : list) {
            String result = orders.getTimerange().getTimeOfStart().toString().substring(0, 5) + " - " + orders.getTimerange().getTimeOfEnd().toString().substring(0, 5);
            log.info(result);
            if (!setOfTimes.contains(result)) {
                setOfTimes.add(result);
            } else {
                allTimes.remove(allTimes.indexOf(result));
            }
        }

        return allTimes;

    }

    /**
     * Solves post from frontend, checks and creates new order if accepted
     * @param line input from frontend
     * @return Returns result of validation
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/orders")
    ResponseEntity<Boolean> makeNewOrder(@RequestBody String line) {

        JSONObject data = null;
        try {
            data = new JSONObject(line);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        log.info(data.toString());

        boolean accepted = false;

        try {
            Address address = new Address();
            address.setStreet(data.getString("street"));
            address.setCity(data.getString("city"));
            address.setHouseNumber(data.getInt("houseNumber"));
            Person person = new Person();
            person.setAddress(address);
            person.setEmail(data.getString("email"));
            person.setName(data.getString("name"));
            person.setSurname(data.getString("surname"));
            person.setPhoneNumber(data.getString("phone"));

            List<VehicleType> vehicleList = vehicleTypeRepository.findAll();
            int vehicleTypeID = 0;
            for (VehicleType vt : vehicleList) {
                if (vt.getDescription().equals(data.getString("vehicle"))) {
                    vehicleTypeID = vt.getId();
                }
            }

            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleType(vehicleTypeRepository.getOne(vehicleTypeID));
            vehicle.setPlateNumber(data.getString("plate"));
            Orders order = new Orders();
            order.setPerson(person);
            order.setVehicle(vehicle);
            String dateString = String.valueOf(data.getString("date"));
            LocalDate date = LocalDate.parse(dateString.substring(0, dateString.indexOf("T")), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            date = date.plusDays(2);
            order.setDate(Date.valueOf(date));

            List<TimeRange> timeRangeList = timeRangeRepository.findAll();
            int timeRangeId = 0;
            for (TimeRange tr : timeRangeList) {
                String time = tr.getTimeOfStart().toString().substring(0, 5) + " - " + tr.getTimeOfEnd().toString().substring(0, 5);
                String timeFromFront = data.getString("time");
                log.info(timeFromFront);
                if (time.equals(timeFromFront)) {
                    timeRangeId = tr.getId();
                }
            }

            TimeRange timeRange = timeRangeRepository.findById(timeRangeId).get();
            order.setTimerange(timeRange);

            JSONArray problems = data.getJSONArray("problems");
            ArrayList<String> problemsList = new ArrayList<>();
            for (int i = 0; i < problems.length(); i++) {
                problemsList.add(problems.get(i).toString());
            }

            List<Problem> problemsListFromRepository = problemRepository.findAll();
            ArrayList<Integer> problemsIDs = new ArrayList<>();
            for (String s : problemsList) {
                for (Problem problem : problemsListFromRepository) {
                    if (s.equals(problem.getDescription())){
                        problemsIDs.add(problem.getId());
                    }
                }
            }

            ArrayList<OrdersHasProblem> ordersHasProblemsToSave = new ArrayList<>();
            for (Integer problemsID : problemsIDs) {
                log.info(String.valueOf(problemsID));
                OrdersHasProblem ordersHasProblem = new OrdersHasProblem();
                ordersHasProblem.setProblem(problemRepository.getOne(problemsID));
                ordersHasProblem.setOrders(order);
                ordersHasProblemsToSave.add(ordersHasProblem);
            }

            Orders newOrder = check(order);

            if (newOrder!=null) {
                log.info(ordersHasProblemsToSave.toString());

                addressRepository.save(address);
                personRepository.save(person);
                vehicleRepository.save(vehicle);
                repository.save(newOrder);
                for (OrdersHasProblem ordersHasProblem : ordersHasProblemsToSave) {
                    ordersHasProblemRepository.save(ordersHasProblem);
                }
                accepted = true;
            }else  {
                accepted = false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(accepted, HttpStatus.ACCEPTED);
    }

    private Orders check(Orders newOrder) {
        String name = newOrder.getPerson().getName();
        String surname = newOrder.getPerson().getSurname();
        String street = newOrder.getPerson().getAddress().getStreet();
        String city = newOrder.getPerson().getAddress().getCity();
        int houseNumber = newOrder.getPerson().getAddress().getHouseNumber();
        String plate = newOrder.getVehicle().getPlateNumber();
        Time startWork = newOrder.getTimerange().getTimeOfStart();
        Time endWork = newOrder.getTimerange().getTimeOfEnd();
        Date date = newOrder.getDate();
        String email = newOrder.getPerson().getEmail();
        String phone = newOrder.getPerson().getPhoneNumber();
        LocalDate timeNow = LocalDate.now();
        ArrayList<Object> results = new ArrayList<>();
        results.add(Collections.addAll(Arrays.asList(name, surname, street, city, houseNumber, plate, startWork, endWork,date,email,phone)));
        results.addAll(Arrays.asList(name, surname, street, city, houseNumber, plate, startWork, endWork,date,email,phone));
        for (Object result : results) {
            if (result == null || result.equals("")) {
                return null;
            }
        }

        if (!email.contains(".cz") && (!email.contains("@")) || (!email.contains(".com") && !email.contains("@"))){
            return null;
        }

        if(startWork.toLocalTime().isAfter(endWork.toLocalTime())){
            return null;
        }

        if(date.toLocalDate().isBefore(timeNow)){
            return null;
        }

        if(plate.isEmpty() || plate.length() < 5 || plate.length() > 8){
            return null;
        }

        if(date == null || date.toString().isEmpty()){
            return null;
        }

        if(houseNumber < 0){
            return null;
        }
        if((phone.isEmpty() || phone.length() != 13) && (!phone.startsWith("+420"))){
            return null;
        }

        if (email.isEmpty() && phone.isEmpty()) {
            return null;
        }

        return newOrder;
    }

}
