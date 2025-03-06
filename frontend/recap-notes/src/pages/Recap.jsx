import React, { useState, useEffect } from "react";

const Recap = () => {
  const [modules, setModules] = useState([]);
  const [selectedModule, setSelectedModule] = useState("");
  const [recap, setRecap] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Fetch modules on component mount
  useEffect(() => {
    const fetchModules = async () => {
      try {
        const response = await fetch("http://localhost:8000/modules");
        const data = await response.json();
        setModules(data);
      } catch (err) {
        setError("Failed to fetch modules");
        console.error(err);
      }
    };
    fetchModules();
  }, []);

  // Fetch recap data when module is selected
  const handleModuleChange = async (moduleId) => {
    setSelectedModule(moduleId);
    if (!moduleId) return;

    setLoading(true);
    try {
      const response = await fetch(
        `http://localhost:8000/modules/${moduleId}/recap`
      );
      if (!response.ok) throw new Error("Failed to fetch recap data");
      const data = await response.json();
      setRecap(data);
      setError(null);
    } catch (err) {
      setError("Failed to fetch recap data");
      setRecap([]);
    } finally {
      setLoading(false);
    }
  };

  // Download CSV file
  const handleDownloadCSV = async () => {
    if (!selectedModule) return;

    try {
      const response = await fetch(
        `http://localhost:8000/modules/${selectedModule}/recap/csv`
      );
      if (!response.ok) throw new Error("Failed to download CSV");

      const blob = await response.blob();
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", `recap-module-${selectedModule}.csv`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (err) {
      setError("Failed to download CSV");
      console.error(err);
    }
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold text-center mb-8">
        Student Grades Recap
      </h1>

      {/* Module Selection */}
      <div className="form-control w-full max-w-xs mx-auto mb-8">
        <label className="label">
          <span className="label-text">Select Module</span>
        </label>
        <select
          className="select select-bordered w-full"
          value={selectedModule}
          onChange={(e) => handleModuleChange(e.target.value)}
        >
          <option value="">Select a module</option>
          {modules.map((module) => (
            <option key={module.id} value={module.id}>
              {module.nom}
            </option>
          ))}
        </select>
      </div>

      {/* Error Display */}
      {error && (
        <div className="alert alert-error mb-4">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="stroke-current shrink-0 h-6 w-6"
            fill="none"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
          <span>{error}</span>
        </div>
      )}

      {/* Loading State */}
      {loading && (
        <div className="flex justify-center my-4">
          <span className="loading loading-spinner loading-lg"></span>
        </div>
      )}

      {/* Recap Table */}
      {recap.length > 0 && (
        <>
          <div className="overflow-x-auto">
            <table className="table table-zebra w-full">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>ID</th>
                  <th>CC</th>
                  <th>TP</th>
                  <th>SN</th>
                  <th>Average</th>
                </tr>
              </thead>
              <tbody>
                {recap.map((student, index) => (
                  <tr key={index}>
                    <td>{`${student.etudiant.nom} ${student.etudiant.prenom}`}</td>
                    <td>{student.etudiant.matricule}</td>
                    <td>{student.moyenneCC.toFixed(2)}</td>
                    <td>{student.moyenneTP.toFixed(2)}</td>
                    <td>{student.moyenneSN.toFixed(2)}</td>
                    <td>{student.moyenneGenerale.toFixed(2)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Download Button */}
          <div className="flex justify-center mt-6">
            <button className="btn btn-primary" onClick={handleDownloadCSV}>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="h-6 w-6 mr-2"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4"
                />
              </svg>
              Download CSV
            </button>
          </div>
        </>
      )}
    </div>
  );
};

export default Recap;
