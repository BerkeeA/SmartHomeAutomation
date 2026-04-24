# 🏠 Smart Home Automation System

This project is a comprehensive **Smart Home Simulation** developed as part of the **Software Design Patterns** course. It focuses on object-oriented design, architectural layering, and state management within a Java environment.

---

## 🔑 Quick Start

To allow for immediate testing, the login screen is pre-configured with administrative credentials:

* **Admin Username:** `Berke`
* **Password:** `1234`

> **Action:** Simply click the **Login** button to access the main dashboard and administrative tools.

---

## 🏗 Architectural Design & Patterns

### 1. Layered Architecture (Decoupling)
The system is organized into distinct packages to ensure a clean **Separation of Concerns**:

* **`models`**: Contains core entities and domain logic (User, Device, Light, Thermostat).
* **`logic`**: Manages the "Business Logic," including AI simulation and command parsing.
* **`ui`**: The presentation layer built with **Java Swing**, decoupled from the data logic.

### 2. Behavioral Logic: Simulated AI (NLP)
The project features a **Simulated AI Assistant (Jarvis)** that demonstrates command-processing logic:

* **Pattern Matching:** It uses **Regex-based** parsing to translate natural language into system actions.
* **Simulated Interface:** Perfectly simulates the "Text-to-Action" pipeline without physical audio hardware.
* **Try This:** Type `"Turn on the Lamp in the Living Room"` or `"Set Kitchen Light to 80%"` in the command bar.

### 3. Data Persistence (Serialization)
To demonstrate the **Persistence** of system states, the project utilizes **Java Object Serialization**:

* The entire state (Users and Devices) is saved into `users.dat` and `devices.dat`.
* **State Recovery:** If these files are missing, the system initializes with a default configuration. All changes are saved upon exit.

---

## 🛠 Features & Functionality

* **Admin Sidebar:** Dynamic panel for **Device Registration** and **User Management**.
* **Live UI Feedback:** JSliders synchronized with real-time status labels.
* **Dual-Logging System:** Tracked via the **Matrix-style** terminal and recorded in `system_logs.txt`.

## 💻 Tech Stack

* **Language:** Java (JDK 11+)
* **UI Framework:** Java Swing
* **Data Handling:** Binary Object Serialization