import React from "react";
import { BrowserRouter as Router, Routes, Route,Link } from "react-router-dom";
import CreateAssessment from "./components/CreateAssessment";
import Dashboard from "./components/Dashboard";
import CreateSurvey from "./components/CreateSurvey";

function App() {
  return (
    <Router>
      <div className="container mx-auto p-4">
        <h1 className="text-3xl font-bold text-center mb-8">Assessment Service</h1>
        <nav className="mb-8">
          <Link to="/" className="mr-4 text-blue-500 hover:underline">Dashboard</Link>
          <Link to="/dashboard" className="mr-4 text-blue-500 hover:underline">AssessmentDashboard</Link>
          <Link to="/create" className="text-blue-500 hover:underline">Create Assessment</Link>
        </nav>
        <Routes>
          <Route path="/create" element={<CreateAssessment />} />
          <Route path="/dashboard" element={<Dashboard />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
