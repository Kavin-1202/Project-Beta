import React, { useState } from "react";
import "../src/App.css"

function App() {
  const [ctc, setCtc] = useState("");
  const [basicSalary, setBasicSalary] = useState(0);
  const [allowances, setAllowances] = useState(0);
  const [bonus, setBonus] = useState(0);
  const [pf, setPf] = useState(0);
  const [nps, setNps] = useState(0);
  const [professionalTax, setProfessionalTax] = useState(0);
  const [inHandSalary, setInHandSalary] = useState(0);
  const [variablePay, setVariablePay] = useState(0);
  const [totalSalaryAllocations, setTotalSalaryAllocations] = useState(0);
  const [totalCuttings, setTotalCuttings] = useState(0);

  const calculatePayslip = () => {
    // 5% Variable Pay
    const annualVariablePay = (ctc * 5) / 100;

    // 95% Fixed Pay
    const fixedPay = (ctc * 95) / 100;

    // Monthly Fixed Pay
    const monthlyFixedPay = fixedPay / 12;

    // 10% Cuttings and 90% Salary from Fixed Pay
    const cuttings = (monthlyFixedPay * 10) / 100;
    const salary = (monthlyFixedPay * 90) / 100;

    // Cutting Allocations
    const monthlyPf = (cuttings * 55) / 100;
    const monthlyNps = (cuttings * 35) / 100;
    const monthlyProfessionalTax = (cuttings * 10) / 100;

    // Salary Allocations
    const monthlyBasicSalary = (salary * 33) / 100;
    const monthlyAllowances = (salary * 50) / 100;
    const monthlyBonus = (salary * 17) / 100;

    // In-Hand Salary Calculation
    const monthlyInHandSalary =
      monthlyBasicSalary + monthlyAllowances + monthlyBonus - (monthlyPf + monthlyNps + monthlyProfessionalTax);

    // Total Salary Allocations and Cuttings
    const totalSalaryAllocations = monthlyBasicSalary + monthlyAllowances + monthlyBonus;
    const totalCuttings = monthlyPf + monthlyNps + monthlyProfessionalTax;

    // Update State
    setBasicSalary(monthlyBasicSalary.toFixed(2));
    setAllowances(monthlyAllowances.toFixed(2));
    setBonus(monthlyBonus.toFixed(2));
    setPf(monthlyPf.toFixed(2));
    setNps(monthlyNps.toFixed(2));
    setProfessionalTax(monthlyProfessionalTax.toFixed(2));
    setInHandSalary(monthlyInHandSalary.toFixed(2));
    setVariablePay((annualVariablePay / 12).toFixed(2));
    setTotalSalaryAllocations(totalSalaryAllocations.toFixed(2));
    setTotalCuttings(totalCuttings.toFixed(2));
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>Payslip Calculator</h1>
      <div style={{ marginBottom: "10px" }}>
        <label>Total CTC: </label>
        <input
          type="number"
          value={ctc}
          onChange={(e) => setCtc(e.target.value)}
        />
      </div>
      <button onClick={calculatePayslip}>Calculate Payslip</button>
      {inHandSalary > 0 && (
        <div style={{ marginTop: "20px" }}>
          <h2>Payslip Details</h2>
          <div style={{ display: "flex", justifyContent: "space-between" }}>
            <div style={{ width: "45%" }}>
              <h3>Salary Allocations</h3>
              <p>Basic Salary: ₹{basicSalary}</p>
              <p>Allowances: ₹{allowances}</p>
              <p>Bonus: ₹{bonus}</p>
              <hr />
              <p><strong>Total Salary Allocations: ₹{totalSalaryAllocations}</strong></p>
            </div>
            <div style={{ width: "45%" }}>
              <h3>Cuttings</h3>
              <p>PF (Provident Fund): ₹{pf}</p>
              <p>NPS Scheme: ₹{nps}</p>
              <p>Professional Tax: ₹{professionalTax}</p>
              <hr />
              <p><strong>Total Cuttings: ₹{totalCuttings}</strong></p>
            </div>
          </div>
          <hr />
          <p><strong>In-Hand Salary: ₹{inHandSalary}</strong></p>
        </div>
      )}
    </div>
  );
}

export default App;
