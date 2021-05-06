package backend.service;

import backend.controller.OrderController;
import backend.repository.OrdersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Loads data from database to repositories
 */
@Component
public class OrderDatabaseLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrdersRepository repository;

    @Autowired
    OrderDatabaseLoader(OrdersRepository repository) { this.repository = repository; }

    @Override
    public void run(String... args) {
        log.info("Preloading Order Data");

        log.info("Numbers of loaded orders: " + this.repository.count());


    }


}
