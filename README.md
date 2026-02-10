# RevHire – Job Portal Management System (Console-Based)

RevHire is a Java-based console application that connects job seekers and employers through a simple and modular job portal system. The application allows job seekers to search and apply for jobs, while employers can post job listings and manage applications. The project follows a layered modular architecture and is designed to be scalable for future web and microservices-based extensions.

---

## 📌 Features

### 👤 Job Seeker
- User registration and login  
- Create and manage profile  
- Build and manage resume  
- Search for jobs  
- Apply for jobs  
- View application status  
- Receive notifications  

### 🏢 Employer
- Employer registration and login  
- Create and manage company profile  
- Post job listings  
- View applicants  
- Shortlist or reject candidates  
- Manage posted jobs  

### ⚙️ System
- Role-based access (Job Seeker & Employer)  
- CRUD operations on core entities  
- Exception handling and input validation  
- Modular layered architecture  
- Database-backed persistent storage  

---

## 🏗️ Architecture

The application follows a **layered modular architecture**:

- **UI Layer** – Console-based user interaction  
- **Service Layer** – Business logic  
- **DAO Layer** – Database access (JDBC)  
- **Model Layer** – Entity classes (User, Job, Application, Resume, etc.)  
- **Database Layer** – Relational Database (MySQL / Oracle)  

This architecture makes the system maintainable and easily extendable to:
- Web application (Spring Boot / REST APIs)  
- Microservices-based architecture  
- Frontend integration (React / Angular)  

---

## 🧩 Modules

- Authentication Module  
- Job Seeker Management  
- Employer Management  
- Job Listing Management  
- Job Application Management  
- Resume Management  
- Notification Module  

---

## 🛠️ Tech Stack

- **Language:** Java  
- **Database:** MySQL  
- **Connectivity:** JDBC  
- **IDE:** Eclipse  
- **Version Control:** Git & GitHub  
- **Architecture:** Layered Modular Architecture  

---

## 🗃️ Database Design (Entities)

- Users  
- JobSeekers  
- Employers  
- EmployerProfiles  
- JobListings  
- Resumes  
- Applications  
- Notifications  

(ER Diagram and schema available in project documentation)

