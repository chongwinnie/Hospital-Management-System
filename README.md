# 🏥 Hospital Management System (HMS)

A **Java-based hospital management application** developed to organize hospital records and demonstrate object-oriented programming (OOP) through both a **console interface** and a **JavaFX desktop interface**.

The project provides modules for doctors, patients, medicines, laboratories, facilities, and staff, with sample data and role-based application views.

---

## ✨ Key Features

| Module | Functionality |
| --- | --- |
| 👨‍⚕️ Doctors | View and manage doctor profiles, specializations, schedules, qualifications, and rooms. |
| 🧑‍🤝‍🧑 Patients | View and manage patient records, diagnoses, admission status, and ages. |
| 💊 Medicines | Manage medicine information, manufacturers, expiry dates, costs, and stock counts. |
| 🧪 Laboratories | Maintain laboratory services and associated costs. |
| 🏥 Facilities | Maintain hospital facility records. |
| 👩‍⚕️ Staff | Manage staff records, including nurses, pharmacists, and security personnel. |
| 🔐 Login | Demonstration login flow with admin and staff roles. |
| 🔎 JavaFX Controls | Add, edit, remove, search, and sort records in desktop interface modules. |

---

## 🛠️ Technologies Used

- **Java** — application logic and object-oriented programming
- **JavaFX** — desktop graphical user interface (GUI)
- **Java Collections and Arrays** — in-memory record handling and UI data lists
- **UML Class Diagram** — object-oriented structure and relationships
- **Eclipse IDE** — development environment used for coursework

---

## 🧩 Object-Oriented Design

The implementation demonstrates:

- **Encapsulation:** Data fields and methods within domain classes such as `Doctor`, `Patient`, and `Medicine`.
- **Inheritance:** `Nurses`, `Pharmacists`, and `Security` extend the `Staff` class.
- **Abstraction:** `Staff` is an abstract class with a role-specific `getRole()` method.
- **Polymorphism:** Different staff subclasses provide their own implementations of `getRole()`.
- **Separation of responsibilities:** `Authenticate` handles console login, `InputHandling` provides input helpers, and application classes coordinate system menus and views.

---

## 🏗️ UML Class Diagram

The UML class diagram illustrates the system's main classes, their attributes and methods, and the inheritance relationships between staff roles.

<div align="center">

<img src="./OOP%20UML.jpg" alt="Hospital Management System UML Class Diagram" width="100%">

*Hospital Management System UML Class Diagram*

</div>

---

## 📁 Repository Structure

```text
Hospital-Management-System/
├── Authenticate.java
├── Doctor.java
├── Facility.java
├── HospitalManagement.java
├── InputHandling.java
├── Lab.java
├── Medicine.java
├── Nurses.java
├── OOP UML.jpg
├── Patient.java
├── Pharmacists.java
├── README.md
├── Security.java
├── Staff.java
└── hmsApp.java
```

---

## ⚙️ Installation & Setup

### Prerequisites

- Java Development Kit (**JDK**)
- For the graphical application: **JavaFX SDK** configured with your JDK/IDE
- An IDE such as Eclipse (optional)

### Option A — Run the Console Application

1. Clone or download this repository.
2. Open a terminal in the repository root.
3. Compile the console application classes:

   ```bash
   javac -d out Authenticate.java Doctor.java Facility.java HospitalManagement.java InputHandling.java Lab.java Medicine.java Nurses.java Patient.java Pharmacists.java Security.java Staff.java
   ```

4. Run the console application:

   ```bash
   java -cp out HospitalManagement
   ```

### Option B — Run the JavaFX Desktop Application

1. Open/import the Java source files into a Java project in Eclipse or another Java IDE.
2. Install a compatible JavaFX SDK and configure the JavaFX libraries and runtime module path in your IDE.
3. Run **`hmsApp.java`** as the JavaFX application entry point.

> **Note:** The repository includes Java source files but does not include a bundled JavaFX SDK or preconfigured build automation. JavaFX setup depends on your installed JDK and IDE.

---

## 🖥️ Application Flow

1. Open either the console (`HospitalManagement`) or JavaFX (`hmsApp`) application.
2. Use the demonstration login screen.
3. Navigate to one of the six hospital record modules.
4. Review existing sample records or use the available management functions.
5. In JavaFX, use the forms, tables, search, and sorting controls to manage records.

> **Prototype note:** Records are handled in memory using arrays and JavaFX collections. This repository does **not** provide a production database or persistent hospital records system. 

---

## 🎬 Project Demonstration

Watch the Hospital Management System demonstration on YouTube.

<div align="center">

<a href="https://youtu.be/5X39Zv0jny8">
  <img src="https://img.youtube.com/vi/5X39Zv0jny8/hqdefault.jpg" alt="Hospital Management System project demonstration thumbnail" width="600">
</a>

<br>

▶️ [Watch the Full Project Demo on YouTube](https://youtu.be/5X39Zv0jny8)

</div>

---
