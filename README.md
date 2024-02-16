# eHotel

## Overview

This project implements a simple hotel management system focusing on guest services, buffet management, and breakfast service simulation.

## Features

### Logger Framework
- **ConsoleLogger:** Implements the Logger interface to log messages to the console, including a timestamp, message type (INFO or ERROR), and the message content.

### Hospitality Management
- **GuestService:** Manages guest information and reservations, providing data for guests expected on a specific day.
- **Buffet Management:** Tracks and manages the buffet's meal portions based on freshness, allows for batch refills, and supports consumption and waste collection based on meal durability and freshness criteria.
- **Breakfast Service Simulation:** Manages the breakfast service in cycles, including buffet refill, meal consumption based on guest preferences, and food waste collection.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) for Java-based implementations.
- Any IDE supporting Java (e.g., IntelliJ IDEA, Eclipse, or VS Code with Java extension).

### Installation and Setup
1. Clone the repository to your local machine.
2. Open the project in your IDE.
3. Ensure all dependencies are correctly configured in your project setup.

## Usage

### Logger
- Initialize the `ConsoleLogger` and use it to log informational or error messages throughout the application.

### Guest Service
- Use the `GuestService` interface to manage guest reservations and retrieve guest lists for specific days.

### Buffet Management
- Interact with the `BuffetService` to manage meal portions in the buffet, including adding new portions, consuming the freshest portions, and collecting food waste.

### Breakfast Service
- Utilize the `BreakfastManager` to simulate the breakfast service, managing guest arrivals, meal consumption, and waste collection in cycles.
