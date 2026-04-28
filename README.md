# 💼 Job Portal Application (Full Stack)

A full-stack job portal system where admins can post jobs and users can apply with intelligent features like skill matching, skill gap analysis, and candidate ranking.

---

## 🚀 Features

### 👤 User

* Sign up & Login
* View available jobs
* Apply for jobs
* Get match score based on skills
* View missing skills (Skill Gap Analysis)
* Track applied jobs

### 🧑‍💼 Admin

* Login as Admin
* Post new jobs with full details
* View applicants for each job
* See ranked candidates based on match score

---

## ⚡ Unique Features

* 🔥 Skill Match Score Calculation
* 🔥 Skill Gap Analysis (missing skills shown)
* 🔥 Candidate Ranking (based on match score)
* 🔥 Role-based access (Admin / User)

---

## 🧱 Tech Stack

### Backend

* Java
* Spring Boot
* Spring Data JPA
* MySQL

### Frontend

* React.js
* JavaScript
* HTML / CSS

---

## 🧠 Architecture

Frontend → Controller → Service → Repository → Database

* **Controller** → Handles API requests
* **Service** → Business logic (match score, validation)
* **Repository** → Database operations

---

## 📂 Project Structure

Job-portal/
├── backend/   (Spring Boot application)
└── frontend/  (React application)

---

## 🔧 How to Run

### Backend

1. Open in STS / IntelliJ
2. Configure MySQL in `application.properties`
3. Run Spring Boot application

---

### Frontend

```bash
cd frontend
npm install
npm start
```

---

## 🧪 Sample Flow

1. User signs up & logs in
2. User applies for a job
3. System calculates:

   * Match Score
   * Missing Skills
4. Admin views candidates ranked by score

---

## 🐛 Challenges Solved

* Fixed database column mismatch issues
* Handled duplicate user login problem
* Resolved CORS issues between frontend & backend
* Managed API response mismatches
* Debugged real-time backend errors

---

## 🚀 Future Improvements

* JWT Authentication (secure login)
* Resume upload feature
* Job recommendations using AI
* Improved UI/UX design

---

## 💬 Author

**Preethi K Setty**
