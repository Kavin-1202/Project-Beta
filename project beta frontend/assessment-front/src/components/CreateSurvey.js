import React, { useState } from 'react';
import axios from 'axios';

function CreateSurvey() {
  const [domain, setDomain] = useState('');
  const [status, setStatus] = useState('PENDING');
  const [email, setEmail] = useState(['']);
  const [companyName, setCompanyName] = useState('');
  const [setName, setSetName] = useState('');

  const handleEmailChange = (index, value) => {
    const newEmail = [...email];
    newEmail[index] = value;
    setEmail(newEmail);
  };

  const addEmailField = () => {
    setEmail([...email, '']);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const newSurvey = {
      domain,
      status,
      email,
      companyName,
      setName,
    };

    axios
      .post('http://localhost:9001/survey', newSurvey)
      .then((response) => {
        alert('Survey created successfully!');
        setDomain('');
        setStatus('PENDING');
        setEmail(['']);
        setCompanyName('');
        setSetName('');
      })
      .catch((error) => {
        console.error('There was an error creating the survey!', error);
      });
  };

  return (
    <div className="max-w-md mx-auto bg-white shadow-md rounded-lg p-6">
      <h2 className="text-2xl font-bold mb-4">Create Survey</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label className="block text-gray-700">Domain</label>
          <input
            type="text"
            value={domain}
            onChange={(e) => setDomain(e.target.value)}
            className="w-full px-3 py-2 border rounded-lg"
            required
          />
        </div>

        <div className="mb-4">
          <label className="block text-gray-700">Company Name</label>
          <input
            type="text"
            value={companyName}
            onChange={(e) => setCompanyName(e.target.value)}
            className="w-full px-3 py-2 border rounded-lg"
            required
          />
        </div>

        <div className="mb-4">
          <label className="block text-gray-700">Set Name</label>
          <input
            type="text"
            value={setName}
            onChange={(e) => setSetName(e.target.value)}
            className="w-full px-3 py-2 border rounded-lg"
            required
          />
        </div>

        <div className="mb-4">
          <label className="block text-gray-700">Emails</label>
          {email.map((email, index) => (
            <div key={index} className="mb-2">
              <input
                type="email"
                value={email}
                onChange={(e) => handleEmailChange(index, e.target.value)}
                className="w-full px-3 py-2 border rounded-lg"
                required
              />
            </div>
          ))}
          <button
            type="button"
            onClick={addEmailField}
            className="text-blue-500 hover:underline"
          >
            + Add Email
          </button>
        </div>

        <button
          type="submit"
          className="bg-blue-500 text-white py-2 px-4 rounded-lg hover:bg-blue-600"
        >
          Create Survey
        </button>
      </form>
    </div>
  );
}

export default CreateSurvey;
