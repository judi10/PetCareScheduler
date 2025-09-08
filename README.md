# PetCareScheduler

PetCareScheduler is a Java console application that helps manage pets and their appointments. It allows users to register pets, schedule visits (vet, grooming, vaccination), and generate care reports.

## Features

- Register pets with owner information
- Add and view appointments
- Display all pets and their scheduled visits
- Report upcoming appointments (next 7 days)
- Identify pets overdue for vet visits (no visit in past 6 months)

## Technologies

- Java
- LocalDate, LocalTime, DateTimeFormatter
- HashMap, ArrayList
- Object serialization for saving/loading data

## How to Run

```bash
javac PetCareScheduler.java Pet.java Appointment.java
java PetCareScheduler


