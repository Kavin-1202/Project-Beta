import React, { useEffect, useState } from "react";
import axios from "axios";

function Dashboard() {
  const [assessments, setAssessments] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:9000/assessments")
      .then((response) => {
        setAssessments(response.data);
      })
      .catch((error) => {
        console.error("There was an error fetching the assessments!", error);
      });
  }, []);

  return (
    <div className="max-w-4xl mx-auto bg-white shadow-md rounded-lg p-6">
      <h2 className="text-2xl font-bold mb-4">Assessments Dashboard</h2>
      <table className="min-w-full bg-white border-collapse">
        <thead>
          <tr>
            <th className="px-6 py-3 border-b-2 border-gray-300">Set Name</th>
            <th className="px-6 py-3 border-b-2 border-gray-300">Domain</th>
            <th className="px-6 py-3 border-b-2 border-gray-300">Created By</th>
            <th className="px-6 py-3 border-b-2 border-gray-300">Status</th>
          </tr>
        </thead>
        <tbody>
          {assessments.map((assessment) => (
            <tr key={assessment.setId}>
              <td className="px-6 py-4 border-b border-gray-300">
                {assessment.setName}
              </td>
              <td className="px-6 py-4 border-b border-gray-300">
                {assessment.domain}
              </td>
              <td className="px-6 py-4 border-b border-gray-300">
                {assessment.createdby}
              </td>
              <td className="px-6 py-4 border-b border-gray-300">
                {assessment.status}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Dashboard;
