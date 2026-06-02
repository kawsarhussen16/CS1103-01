# Weather Information App

## Overview

The Weather Information App is a Java Swing-based desktop application that provides real-time weather information for a specified city. The application integrates with the OpenWeatherMap API to retrieve current weather conditions and displays the information through a user-friendly graphical interface.

This project demonstrates core software engineering and Java concepts, including:

- API integration and HTTP requests
- Java Swing GUI development
- Object-Oriented Programming (OOP) principles
- File handling and persistence
- Error handling and input validation
- Unit conversion utilities
- Dynamic UI background rendering
- Search history tracking

---

## Features

### Current Weather Information

The application displays:

- City name
- Temperature
- Humidity
- Wind speed
- Weather condition
- Weather icon

---

### 🖥️ User-Friendly GUI

Users can:

- Enter a city name
- Fetch real-time weather data
- Switch between temperature units
- View search history
- Interact with a clean desktop interface

---

### 🌡️ Unit Conversion

Supports temperature conversion:

- Celsius (°C)
- Fahrenheit (°F)

> Note: Wind speed conversion is not implemented.

---

### 🕘 Search History

- Stores all searched cities
- Includes timestamp for each search
- Saves data to a local file (`history.txt`)
- Loads previous history on application startup

---

### 🎨 Dynamic Backgrounds

The background image changes based on time of day:

- Morning
- Afternoon
- Evening/Night

---

### ⚠️ Error Handling

The application handles:

- Empty city input
- Invalid city names
- Network/API failures
- File I/O issues

---

## 🧱 Project Structure
src
└── main
└── java
└── com.weatherapp
├── WeatherApp.java
├── WeatherUI.java
├── WeatherService.java
├── WeatherData.java
├── ForecastData.java
├── HistoryManager.java
├── UnitConverter.java
└── BackgroundManager.java


---

## 🛠️ Technologies Used

- Java 17+
- Java Swing (GUI)
- OpenWeatherMap API
- HTTPURLConnection
- Java File I/O
- Object-Oriented Programming (OOP)

---

## Security Notes

- API keys are currently hardcoded for educational purposes.
- In production applications, API keys should be stored securely using:
  - Environment variables
  - Configuration files (excluded from version control)
  - Secret management services

---

## API Setup

### Step 1: Create Account
https://openweathermap.org

### Step 2: Generate API Key
Go to:
My Account → API Keys

### Step 3: Update Code

private static final String API_KEY = "YOUR_API_KEY";

# How to Run
Step 1: Open Project

Open the project folder in VS Code.

Step 2: Compile

``` javac -d out src/main/java/com/weatherapp/*.java ```

Step 3: Run

``` java -cp out com.weatherapp.WeatherApp ```

Example Usage
Search Weather
Enter city: Atlanta
Click: Get Weather
View results in UI
View History

Click: View History

#3 Example Output
City: Atlanta
Temperature: 27.50
Humidity: 65%
Wind Speed: 4.20 m/s
Condition: Clear Sky