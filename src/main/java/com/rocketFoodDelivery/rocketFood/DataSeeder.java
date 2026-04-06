package com.rocketFoodDelivery.rocketFood;

import com.rocketFoodDelivery.rocketFood.models.*;
import com.rocketFoodDelivery.rocketFood.repository.*;
// import com.rocketFoodDelivery.rocketFood.service.*;

import jakarta.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("manual-seeding")
public class DataSeeder {
        // Dependency Injections
        private final AddressRepository addressRepository;
        private final UserRepository userRepository;
        private final CustomerRepository customerRepository;
        // private final RestaurantService restaurantService;

        // Constructor
        public DataSeeder(
                // RestaurantService restaurantService,
                AddressRepository addressRepository,
                UserRepository userRepository, 
                CustomerRepository customerRepository
        ) {
                // this.restaurantService = restaurantService;
                this.addressRepository = addressRepository;
                this.userRepository = userRepository;
                this.customerRepository = customerRepository;
        }
         
        // Seed method that runs automatically after bean construction. 
        @PostConstruct
        public void seedData() {
                System.out.println("🌱 Starting database seeding...");
                
                // Comment/uncomment the seeds you want to run
                seedAddresses();
                seedUsers();
                seedCustomers();
                // seedRestaurants(); // Uncomment after creating RestaurantService
                
                System.out.println("\n✅ Database seeding completed!");
        }

        // =====================================
        // SEED ADDRESSES
        // =====================================
        private void seedAddresses() {
                if (addressRepository.count() > 0) {
                        System.out.println("⚠️  Addresses table already contains data. Skipping address seeding.");
                        return;
                }

                System.out.println("\n🏠 Seeding addresses...");
                
                String[] streets = {
                        "123 Main Street", "456 Oak Avenue", "789 Pine Road",
                        "321 Elm Boulevard", "654 Maple Drive", "987 Cedar Lane",
                        "147 Birch Street", "258 Walnut Court", "369 Cherry Way",
                        "741 Spruce Terrace"
                };

                String[] cities = {
                        "New York", "Los Angeles", "Chicago", "Houston", "Phoenix",
                        "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose"
                };

                String[] postalCodes = {
                        "10001", "90001", "60601", "77001", "85001",
                        "19101", "78201", "92101", "75201", "95101"
                };

                for (int i = 0; i < streets.length; i++) {
                        Address address = Address.builder()
                                        .streetAddress(streets[i])
                                        .city(cities[i])
                                        .postalCode(postalCodes[i])
                                        .build();
                        addressRepository.save(address);
                }

                System.out.println("✅ Seeded " + streets.length + " addresses");
        }

        // =====================================
        // SEED USERS
        // =====================================
        private void seedUsers() {
                if (userRepository.count() > 0) {
                        System.out.println("⚠️  Users table already contains data. Skipping user seeding.");
                        return;
                }

                System.out.println("\n👤 Seeding users...");

                String[] userNames = {"John Both", "John Customer", "John Courier"};
                String[] userEmails = {"john@both.com", "john@customer.com", "john@courier.com"};
                String[] userPasswords = {"password", "password", "password"};

                for (int i = 0; i < userNames.length; i++) {
                        User user = User.builder()
                                        .name(userNames[i])
                                        .email(userEmails[i])
                                        .password(userPasswords[i])
                                        .build();
                        userRepository.save(user);
                }

                System.out.println("✅ Seeded " + userNames.length + " users");
        }

        // =====================================
        // SEED CUSTOMERES
        // =====================================

        private void seedCustomers() {
                if (customerRepository.count() > 0) {
                        System.out.println("⚠️  Customers table already contains data. Skipping customer seeding.");
                        return;
                }

                System.out.println("\n🛒 Seeding customers...");
                
                List<User> users = userRepository.findAll();
                List<Address> addresses = addressRepository.findAll();

                if (users.isEmpty() || addresses.isEmpty()) {
                        System.out.println("⚠️  Cannot seed customers: Users or Addresses are missing. Run those seeds first.");
                        return;
                }

                String[] customerPhones = {"555-0101", "555-0102", "555-0103"};
                String[] customerEmails = {"customer1@rocketfood.com", "customer2@rocketfood.com", "customer3@rocketfood.com"};

                for (int i = 0; i < users.size(); i++) {
                        Customer customer = Customer.builder()
                                        .user(users.get(i))
                                        .address(addresses.get(i % addresses.size()))
                                        .phone(customerPhones[i % customerPhones.length])
                                        .email(customerEmails[i % customerEmails.length])
                                        .build();
                        customerRepository.save(customer);
                }

                System.out.println("✅ Seeded " + users.size() + " customers");
        }

        // =====================================
        // SEED RESTAURANTS
        // =====================================
        // private void seedRestaurants() {
        //         if (restaurantService.findAll().size() > 0) {
        //                 System.out.println("⚠️  Restaurants table already contains data. Skipping restaurant seeding.");
        //                 return;
        //         }

        //         System.out.println("\n🍔 Seeding restaurants...");
                
        //         List<User> users = userRepository.findAll();
        //         List<Address> addresses = addressRepository.findAll();

        //         if (users.isEmpty() || addresses.isEmpty()) {
        //                 System.out.println("⚠️  Cannot seed restaurants: Users or Addresses are missing. Run those seeds first.");
        //                 return;
        //         }

        //         String[] restaurantNames = {
        //                 "Pizza Palace", "Sushi House", "Curry Corner",
        //                 "Taco Town", "Steakhouse Prime", "Pasta Paradise"
        //         };

        //         String[] restaurantPhones = {
        //                 "(555) 123-4567", "(555) 234-5678", "(555) 345-6789",
        //                 "(555) 456-7890", "(555) 567-8901", "(555) 678-9012"
        //         };

        //         String[] restaurantEmails = {
        //                 "info@pizzapalace.com", "contact@sushihouse.com", "hello@currycorner.com",
        //                 "info@tacotown.com", "reservations@steakhouseprime.com", "info@pastaparadise.com"
        //         };

        //         int[] priceRanges = {2, 3, 1, 1, 3, 2};
        //         boolean[] activeStatus = {true, true, true, false, true, false};

        //         for (int i = 0; i < restaurantNames.length; i++) {
        //                 Restaurant restaurant = Restaurant.builder()
        //                         .name(restaurantNames[i])
        //                         .phone(restaurantPhones[i])
        //                         .email(restaurantEmails[i])
        //                         .user(users.get(i % users.size()))
        //                         .address(addresses.get(i % addresses.size()))
        //                         .priceRange(priceRanges[i])
        //                         .active(activeStatus[i])
        //                         .build();
        //                 restaurantService.save(restaurant);
        //         }

        //         System.out.println("✅ Seeded " + restaurantNames.length + " restaurants");
        // }
        
}