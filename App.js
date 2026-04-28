import React, { useState, useEffect } from "react";

function App() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [user, setUser] = useState(null);
  const [isSignup, setIsSignup] = useState(false);

  // LOGIN
  const login = () => {
    fetch(`http://localhost:8080/users/login?email=${email}&password=${password}`, {
      method: "POST"
    })
      .then(res => {
        if (!res.ok) throw new Error();
        return res.json();
      })
      .then(data => setUser(data))
      .catch(() => alert("Invalid credentials"));
  };

  // SIGNUP
  const signup = () => {
    fetch("http://localhost:8080/users/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        name: email.split("@")[0],
        email: email,
        password: password,
        role: "JOB_SEEKER",
        skills: "Java"
      })
    })
      .then(res => res.json())
      .then(() => {
        alert("Signup successful! Please login.");
        setIsSignup(false);
      });
  };

  // LOGIN / SIGNUP PAGE
  if (!user) {
    return (
      <div style={{ padding: "20px" }}>
        <h2>{isSignup ? "Sign Up" : "Login"}</h2>

        <input placeholder="Email" onChange={(e) => setEmail(e.target.value)} />
        <br /><br />

        <input placeholder="Password" type="password"
               onChange={(e) => setPassword(e.target.value)} />
        <br /><br />

        {isSignup ? (
          <>
            <button onClick={signup}>Sign Up</button>
            <br /><br />
            <button onClick={() => setIsSignup(false)}>Go to Login</button>
          </>
        ) : (
          <>
            <button onClick={login}>Login</button>
            <br /><br />
            <button onClick={() => setIsSignup(true)}>New User? Sign Up</button>
          </>
        )}
      </div>
    );
  }

  return <Dashboard user={user} setUser={setUser} />;
}

// ================= DASHBOARD =================

function Dashboard({ user, setUser }) {
  return (
    <div style={{ padding: "20px" }}>
      <h2>Welcome {user.name}</h2>

      <button onClick={() => setUser(null)}>Logout</button>

      <hr />

      {user.role === "ADMIN"
        ? <AdminPanel user={user} />
        : <UserPanel user={user} />}
    </div>
  );
}

// ================= ADMIN PANEL =================

function AdminPanel({ user }) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [location, setLocation] = useState("");
  const [salary, setSalary] = useState("");
  const [skills, setSkills] = useState("");
  const [expiryDate, setExpiryDate] = useState("");

  const [jobId, setJobId] = useState("");
  const [candidates, setCandidates] = useState([]);

  // CREATE JOB
  const createJob = () => {
    fetch("http://localhost:8080/jobs/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        title,
        description,
        location,
        salary: parseFloat(salary),
        requiredSkills: skills,
        expiryDate,
        user: { id: user.id }
      })
    })
      .then(res => res.json())
      .then(() => alert("Job Created!"));
  };

  // VIEW CANDIDATES
  const fetchCandidates = () => {
    fetch(`http://localhost:8080/applications/top-candidates?jobId=${jobId}`)
      .then(res => res.json())
      .then(data => setCandidates(data));
  };

  return (
    <div>
      <h3>Admin Panel</h3>

      <h4>Create Job</h4>

      <input placeholder="Title" onChange={(e) => setTitle(e.target.value)} /><br /><br />
      <input placeholder="Description" onChange={(e) => setDescription(e.target.value)} /><br /><br />
      <input placeholder="Location" onChange={(e) => setLocation(e.target.value)} /><br /><br />
      <input placeholder="Salary" type="number" onChange={(e) => setSalary(e.target.value)} /><br /><br />
      <input placeholder="Skills" onChange={(e) => setSkills(e.target.value)} /><br /><br />
      <input type="date" onChange={(e) => setExpiryDate(e.target.value)} /><br /><br />

      <button onClick={createJob}>Post Job</button>

      <hr />

      <h4>View Candidates</h4>

      <input placeholder="Enter Job ID"
             onChange={(e) => setJobId(e.target.value)} />

      <button onClick={fetchCandidates}>Get Candidates</button>

      {candidates.map(c => (
        <div key={c.id} style={{ border: "1px solid black", margin: "10px", padding: "10px" }}>
          <p><b>User:</b> {c.user.name}</p>
          <p><b>Match Score:</b> {c.matchScore}</p>
        </div>
      ))}
    </div>
  );
}

// ================= USER PANEL =================

function UserPanel({ user }) {
  const [jobs, setJobs] = useState([]);
  const [message, setMessage] = useState("");
  const [applications, setApplications] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/jobs/active")
      .then(res => res.json())
      .then(data => setJobs(data));
  }, []);

  // APPLY
  const applyJob = (jobId) => {
    fetch(`http://localhost:8080/applications/apply?userId=${user.id}&jobId=${jobId}`, {
      method: "POST"
    })
      .then(res => res.text())
      .then(data => setMessage(data));
  };

  // VIEW MY APPLICATIONS
 const fetchApplications = () => {
  fetch(`http://localhost:8080/applications/user-applications?userId=${user.id}`)
    .then(res => res.json())
    .then(data => {
      console.log("Applications:", data); // 👈 debug
      setApplications(Array.isArray(data) ? data : []);
    })
    .catch(() => alert("Error fetching applications"));
};

  return (
    <div>
      <h3>User Dashboard</h3>

      {message && <p style={{ color: "green" }}>{message}</p>}

      <button onClick={fetchApplications}>View My Applications</button>

      {applications.length > 0 ? (
  applications.map(app => (
    <div
      key={app.id}
      style={{
        border: "1px solid #ccc",
        padding: "10px",
        margin: "10px",
        borderRadius: "8px"
      }}
    >
      <p><b>Job:</b> {app.jobTitle}</p>
      <p><b>Match Score:</b> {app.matchScore}%</p>
    </div>
  ))
) : (
  <p>No applications found</p>
)}

      <hr />

      {jobs.map(job => (
        <div key={job.id} style={{ border: "1px solid black", margin: "10px", padding: "10px" }}>
          <h4>{job.title}</h4>
          <p>{job.description}</p>
          <p><b>Skills:</b> {job.requiredSkills}</p>

          <button onClick={() => applyJob(job.id)}>Apply</button>
        </div>
      ))}
    </div>
  );
}

export default App;