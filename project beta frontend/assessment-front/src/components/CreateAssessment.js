import React, { useState } from "react";
import axios from "axios";

function CreateAssessment() {
  const [setName, setSetName] = useState("");
  const [domain, setDomain] = useState("");
  const [questions, setQuestions] = useState([
    { question_description: "", optionsdtoList: [{ answer: "", suggestion: "" }] },
  ]);

  const handleQuestionChange = (index, field, value) => {
    const newQuestions = [...questions];
    newQuestions[index][field] = value;
    setQuestions(newQuestions);
  };

  const handleOptionChange = (qIndex, oIndex, field, value) => {
    const newQuestions = [...questions];
    newQuestions[qIndex].optionsdtoList[oIndex][field] = value;
    setQuestions(newQuestions);
  };

  const addQuestion = () => {
    setQuestions([...questions, { question_description: "", optionsdtoList: [{ answer: "", suggestion: "" }] }]);
  };
  // Remove a question
  const removeQuestion = (index) => {
    const newQuestions = questions.filter((_, i) => i !== index);
    setQuestions(newQuestions);
  };

  const addOption = (qIndex) => {
    const newQuestions = [...questions];
    newQuestions[qIndex].optionsdtoList.push({ answer: "", suggestion: "" });
    setQuestions(newQuestions);
  };
  // Remove an option from a specific question
  const removeOption = (qIndex, oIndex) => {
    const newQuestions = [...questions];
    newQuestions[qIndex].optionsdtoList = newQuestions[qIndex].optionsdtoList.filter((_, i) => i !== oIndex);
    setQuestions(newQuestions);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const newAssessment = {
      setName,
      domain,
      questionList: questions,
    };

    axios
      .post("http://localhost:9000/assessment", newAssessment)
      .then((response) => {
        alert("Assessment created successfully!");
        setSetName("");
        setDomain("");
        setQuestions([{ question_description: "", optionsdtoList: [{ answer: "", suggestion: "" }] }]);
      })
      .catch((error) => {
        console.error("There was an error creating the assessment!", error);
      });
  };

  return (
    <div className="max-w-md mx-auto bg-white shadow-md rounded-lg p-6">
      <h2 className="text-2xl font-bold mb-4">Create Assessment</h2>
      <form onSubmit={handleSubmit}>
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
          <label className="block text-gray-700">Domain</label>
          <input
            type="text"
            value={domain}
            onChange={(e) => setDomain(e.target.value)}
            className="w-full px-3 py-2 border rounded-lg"
            required
          />
        </div>

        {questions.map((question, qIndex) => (
          <div key={qIndex} className="mb-6">
            <div className="mb-4">
              <label className="block text-gray-700">Question Description</label>
              <input
                type="text"
                value={question.question_description}
                onChange={(e) => handleQuestionChange(qIndex, "question_description", e.target.value)}
                className="w-full px-3 py-2 border rounded-lg"
                required
              />
            </div>
            <label className="block text-gray-700 mb-2">Options</label>
            {question.optionsdtoList.map((option, oIndex) => (
              <div key={oIndex} className="mb-4">
                <input
                  type="text"
                  placeholder="Answer"
                  value={option.answer}
                  onChange={(e) => handleOptionChange(qIndex, oIndex, "answer", e.target.value)}
                  className="w-full px-3 py-2 border rounded-lg mb-2"
                  required
                />
                <input
                  type="text"
                  placeholder="Suggestion"
                  value={option.suggestion}
                  onChange={(e) => handleOptionChange(qIndex, oIndex, "suggestion", e.target.value)}
                  className="w-full px-3 py-2 border rounded-lg"
                />
                <button
                  type="button"
                  onClick={() => removeOption(qIndex, oIndex)}
                  className="text-red-500 hover:underline"
                >
                  Remove Option
                </button>

              </div>
            ))}
            <button
              type="button"
              onClick={() => addOption(qIndex)}
              className="text-blue-500 hover:underline mb-4"
            >
              Add Option
            </button>
            <div className="flex right">
            <button
              type="button"
              onClick={() => removeQuestion(qIndex)}
              className="bg-red-500 text-white py-2 px-4 rounded-lg hover:bg-red-600 mb-4"
            >
              Remove Question
            </button>
            </div>
            
          </div>
        ))}
        <button
          type="button"
          onClick={addQuestion}
          className="bg-green-500 text-white py-2 px-4 rounded-lg hover:bg-green-600 mb-4"
        >
          Add Question
        </button>
        <br></br>
        <div className="flex justify-center">
          <button
            type="submit"
            className="bg-blue-500 text-white py-2 px-4 rounded-lg hover:bg-blue-600"
          >
            Submit
          </button>
        </div>
      </form>
    </div>
  );
}

export default CreateAssessment;
