# 🏠 Smart Home Automation System

This project is a comprehensive **Smart Home Simulation** developed as part of the **Software Design Patterns** course. It focuses on object-oriented design, architectural layering, and state management within a Java environment, now featuring real-time voice command integration.

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
* **`logic`**: Manages the "Business Logic," including AI processing and the Java-Python bridge.
* **`ui`**: The presentation layer built with **Java Swing**, decoupled from the data logic.

### 2. Behavioral Logic: AI & Voice Integration
The project features an **AI Assistant (Jarvis)** that demonstrates advanced interaction:

* **Real-time Voice Recognition:** Integrated via a **Python bridge** using the `SpeechRecognition` library. It captures live audio and converts it to text for the Java backend to process.
* **NLP Command Parsing:** Uses **Regex-based** parsing to translate natural language into system actions (Supports both English and Turkish keywords).
* **Try This:** Click the **LISTEN** button and say `"Turn on the Lamp in the Living Room"` or `"Set Kitchen Light to 80%"`.

### 3. Data Persistence (Serialization)
To demonstrate the **Persistence** of system states, the project utilizes **Java Object Serialization**:

* The entire state (Users and Devices) is saved into `users.dat` and `devices.dat`.
* **State Recovery:** If these files are missing, the system initializes with a default configuration. All changes are automatically saved upon exit.

---

## 🛠 Features & Functionality

* **Voice-Activated Control:** Hands-free device management via the Python-Java bridge.
* **Admin Sidebar:** Dynamic panel for **Device Registration** and **User Management**.
* **Live UI Feedback:** JSliders synchronized with real-time status labels and device icons.
* **Dual-Logging System:** Activity is tracked via a **Matrix-style** terminal and recorded in `system_logs.txt`.

## 💻 Tech Stack

* **Languages:** Java (JDK 11+) & Python 3.x
* **UI Framework:** Java Swing
* **AI Bridge:** Java `ProcessBuilder` (Communication layer)
* **Python Libraries:** `SpeechRecognition`, `PyAudio`
* **Data Handling:** Binary Object Serialization
