package backend.controller;

import backend.entity.*;
import backend.repository.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;

@RestController
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrdersRepository repository;
    private final AddressRepository addressRepository;
    private final TimeRangeRepository timeRangeRepository;
    private final PersonRepository personRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final VehicleRepository vehicleRepository;

    public OrderController(OrdersRepository repository, AddressRepository addressRepository, TimeRangeRepository timeRangeRepository, PersonRepository personRepository, VehicleTypeRepository vehicleTypeRepository, VehicleRepository vehicleRepository) {
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.timeRangeRepository = timeRangeRepository;
        this.personRepository = personRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/times")
    Orders getTimes(@RequestParam(name="value", required = false, defaultValue = "default") String value){

        // tady si vytvarim jen jednoduchy objekt newOrder abych vedel jak vypada vyledny JSON...
        // klidne to potom smazte

        Address newAddress = new Address();
        newAddress.setCity("Ostrava");
        newAddress.setHouseNumber(12);
        newAddress.setStreet("30. Dubna");

        Person newPerson = new Person();
        newPerson.setAddress(newAddress);
        newPerson.setEmail("test@test.cz");
        newPerson.setSurname("Surname");
        newPerson.setPhoneNumber("777777777");
        newPerson.setName("Name");

        TimeRange newTimeRange = new TimeRange();

        newTimeRange.setTimeOfEnd(new Time(1,1,1));
        newTimeRange.setTimeOfStart(new Time(2,3,1));

        VehicleType newVehicleType = new VehicleType();
        newVehicleType.setDescription("test test");

        Vehicle newVehicle = new Vehicle();
        newVehicle.setVehicleType(newVehicleType);
        newVehicle.setPlateNumber("ST-123");

        Orders newOrder = new Orders();
        newOrder.setPerson(newPerson);

        newOrder.setDate(new Date(2021,2,3));
        newOrder.setPerson(newPerson);
        newOrder.setTimerange(newTimeRange);

        //return new ArrayList<>(Arrays.asList("A","B","C"));

        return newOrder;

    }

    @PostMapping("/orders")
    Orders getNewOrder(@RequestBody Orders newOrder) {
        log.info("PostMapping /orders");
        log.info("newOrder " + newOrder);

        // Tohle se da urcite resit efektivneji tim ze se ulozi najednou Order a vsechny navazane objekty, ted to ale
        // nebudeme hrotit a postaci takto jednoduche reseni pres vice repository kde se postupne ulozi jednotlive
        // objekty a potom hlavni objekt Order

        // Abychom mohli ulozit Person, ktery potrebuje znat address_id potrebujeme nejprve ulozit objekt Address,
        // kde pres autoincrement ziskame jeho ID. Jakmile objekt ulozime pres metodu save, dostaneme nacteny objekt
        // z databaze, ktery uz bude mit ID vygenerovane.
        // Toto address_id pote ulozime do Person tak ze jen prepiseme objet Address pres setter.
        // uplne stejne postupujeme u objektu Vehicle, ktery potrebuje znat vehicle_type_id atd...
        // jakmile mame ID pro vsechny objekty nastavime ID do objektu Order a ten ulozime

        Address newAddress = addressRepository.save(newOrder.getPerson().getAddress());
        newOrder.getPerson().setAddress(newAddress);
        Person newPerson = personRepository.save(newOrder.getPerson());

        TimeRange newTimerange = timeRangeRepository.save(newOrder.getTimerange());

        VehicleType vehicleType = vehicleTypeRepository.save(newOrder.getVehicle().getVehicleType());
        newOrder.getVehicle().setVehicleType(vehicleType);
        Vehicle newVehicle = vehicleRepository.save(newOrder.getVehicle());


        newOrder.setPerson(newPerson);
        newOrder.setVehicle(newVehicle);
        newOrder.setTimerange(newTimerange);

        return repository.save(newOrder);
    }

/*
    @PutMapping("/orders/{id}")
    OrdersEntity replaceOrder(@RequestBody OrdersEntity newOrder, @PathVariable Integer id){
        log.info("PutMapping /orders/{id}");
        log.info("Id: " + id);


        OrdersEntity ret = repository.findById(id)
                .map(order -> {
                    order.setPrice(newOrder.getPrice());
                    order.setDate(newOrder.getDate());
                    order.setDescription(newOrder.getDescription());
                    return repository.save(order);
                })
                .orElseGet(() -> {
                    newOrder.setId(id);
                    return repository.save(newOrder);
                });



        log.info("updated or created order: " + ret);


        return ret;
    }
*/
    /*
    @DeleteMapping("/orders/{id}")
    void deleteOrderById(@PathVariable Integer id) {
        log.info("DeleteMapping /orders/{id}");
        log.info("Id: " + id);

        repository.deleteById(id);
    }
*/
}
