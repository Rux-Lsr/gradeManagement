import React, { useState, useEffect } from "react";

const Notes = () => {
  const [modules, setModules] = useState([]);
  const [students, setStudents] = useState([]);
  const [evaluations, setEvaluations] = useState([]);
  const [notes, setNotes] = useState([]);
  const [selectedModule, setSelectedModule] = useState("");
  const [evaluationType, setEvaluationType] = useState("");
  const [evaluationDate, setEvaluationDate] = useState("");
  const [evaluationCoef, setEvaluationCoef] = useState("");
  const [evaluationMax, setEvaluationMax] = useState("");
  const [grades, setGrades] = useState({});
  const [modalMessage, setModalMessage] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);
  const [moduleValidated, setModuleValidated] = useState(false);

  useEffect(() => {
    fetch("http://127.0.0.1:8000/modules")
      .then((response) => response.json())
      .then((data) => setModules(data))
      .catch((error) => console.error("Error fetching modules:", error));
  }, []);

  useEffect(() => {
    if (moduleValidated && selectedModule) {
      const module = modules.find((mod) => mod.id === parseInt(selectedModule));
      if (module) {
        setStudents(module.etudiantSet);
        setEvaluations(module.evaluationSet);

        fetch(`http://127.0.0.1:8000/notes?moduleId=${selectedModule}`)
          .then((response) => response.json())
          .then((data) => {
            setNotes(data);
            const initialGrades = {};
            data.forEach((note) => {
              initialGrades[`${note.etudiantId}-${note.evaluationId}`] =
                note.note;
            });
            setGrades(initialGrades);
          })
          .catch((error) => console.error("Error fetching notes:", error));
      }
    }
  }, [moduleValidated, selectedModule, modules]);

  const handleAddEvaluation = (e) => {
    e.preventDefault();
    const newEvaluation = {
      moduleId: selectedModule,
      date: evaluationDate,
      typeEvaluation: evaluationType,
      coef: parseFloat(evaluationCoef),
      max: parseFloat(evaluationMax),
    };

    fetch("http://127.0.0.1:8000/evaluations", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(newEvaluation),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.rowsAffected > 0) {
          setEvaluations([...evaluations, newEvaluation]);
          setEvaluationType("");
          setEvaluationDate("");
          setEvaluationCoef("");
          setEvaluationMax("");
          setIsSuccess(true);
          setModalMessage("Évaluation ajoutée avec succès !");
        } else {
          setIsSuccess(false);
          setModalMessage("Erreur lors de l'ajout de l'évaluation.");
        }
        setShowModal(true);
      })
      .catch((error) => {
        console.error("Error adding evaluation:", error);
        setIsSuccess(false);
        setModalMessage("Erreur lors de l'ajout de l'évaluation.");
        setShowModal(true);
      });
  };

  const handleGradeChange = (studentId, evaluationId, value) => {
    setGrades({
      ...grades,
      [`${studentId}-${evaluationId}`]: value,
    });
  };

  const handleSaveGrades = () => {
    const updates = Object.keys(grades).map((key) => {
      const [studentId, evaluationId] = key.split("-");
      return {
        etudiantId: studentId,
        evaluationId: evaluationId,
        note: Number(grades[key]),
      };
    });

    Promise.all(
      updates.map((update) =>
        fetch("http://127.0.0.1:8000/notes", {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(update),
        })
      )
    )
      .then((responses) => Promise.all(responses.map((res) => res.json())))
      .then((results) => {
        const success = results.every((result) => result.rowsAffected > 0);
        if (success) {
          setIsSuccess(true);
          setModalMessage("Notes mises à jour avec succès !");
        } else {
          setIsSuccess(false);
          setModalMessage("Erreur lors de la mise à jour des notes.");
        }
        setShowModal(true);
      })
      .catch((error) => {
        console.error("Error updating grades:", error);
        setIsSuccess(false);
        setModalMessage("Erreur lors de la mise à jour des notes.");
        setShowModal(true);
      });
  };

  const handleValidateModule = () => {
    setModuleValidated(true);
  };

  return (
    <div className="p-8">
      <div className="card bg-base-100 shadow-xl mb-8">
        <div className="card-body">
          <h1 className="card-title text-2xl font-bold">
            Sélectionner un Module
          </h1>
          <select
            value={selectedModule}
            onChange={(e) => setSelectedModule(e.target.value)}
            className="select select-bordered w-full"
            required
          >
            <option value="" disabled>
              Sélectionner un module
            </option>
            {modules.map((module) => (
              <option key={module.id} value={module.id}>
                {module.nom}
              </option>
            ))}
          </select>
          <button
            onClick={handleValidateModule}
            className="btn btn-primary mt-4"
          >
            Valider
          </button>
        </div>
      </div>

      {moduleValidated && selectedModule && (
        <>
          <div className="card bg-base-100 shadow-xl mb-8">
            <div className="card-body">
              <h1 className="card-title text-2xl font-bold">
                Ajouter une Évaluation
              </h1>
              <form onSubmit={handleAddEvaluation} className="space-y-4">
                <div>
                  <label className="block text-sm font-medium">
                    Type d'Évaluation
                  </label>
                  <select
                    value={evaluationType}
                    onChange={(e) => setEvaluationType(e.target.value)}
                    className="select select-bordered w-full"
                    required
                  >
                    <option value="" disabled>
                      Sélectionner un type
                    </option>
                    <option value="TP">TP</option>
                    <option value="CC">CC</option>
                    <option value="SN">SN</option>
                    <option value="Rattrapage">Rattrapage</option>
                  </select>
                </div>
                <div>
                  <label className="block text-sm font-medium">Date</label>
                  <input
                    type="date"
                    value={evaluationDate}
                    onChange={(e) => setEvaluationDate(e.target.value)}
                    className="input input-bordered w-full"
                    required
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium">
                    Coefficient
                  </label>
                  <input
                    type="number"
                    step="0.1"
                    value={evaluationCoef}
                    onChange={(e) => setEvaluationCoef(e.target.value)}
                    className="input input-bordered w-full"
                    required
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium">
                    Note Maximale
                  </label>
                  <input
                    type="number"
                    step="0.1"
                    value={evaluationMax}
                    onChange={(e) => setEvaluationMax(e.target.value)}
                    className="input input-bordered w-full"
                    required
                  />
                </div>
                <button type="submit" className="btn btn-primary">
                  Ajouter
                </button>
              </form>
            </div>
          </div>

          <div className="card bg-base-100 shadow-xl">
            <div className="card-body">
              <h1 className="card-title text-2xl font-bold">
                Liste des Étudiants
              </h1>
              <table className="table-auto w-full">
                <thead>
                  <tr>
                    <th>Nom</th>
                    <th>Prénom</th>
                    {evaluations.map((evaluation) => (
                      <th key={evaluation.id}>
                        {evaluation.typeEvaluation} -{" "}
                        {new Date(evaluation.date).toLocaleDateString()}
                      </th>
                    ))}
                  </tr>
                </thead>
                <tbody>
                  {students.map((student) => (
                    <tr key={student.id}>
                      <td>{student.nom}</td>
                      <td>{student.prenom}</td>
                      {evaluations.map((evaluation) => (
                        <td key={evaluation.id}>
                          <input
                            type="number"
                            value={
                              grades[`${student.id}-${evaluation.id}`] || ""
                            }
                            onChange={(e) =>
                              handleGradeChange(
                                student.id,
                                evaluation.id,
                                e.target.value
                              )
                            }
                            className="input input-bordered w-full"
                          />
                        </td>
                      ))}
                    </tr>
                  ))}
                </tbody>
              </table>
              <button
                onClick={handleSaveGrades}
                className="btn btn-primary mt-4"
              >
                Enregistrer les Notes
              </button>
            </div>
          </div>
        </>
      )}

      {showModal && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white p-8 rounded-lg shadow-lg">
            <h2
              className={`text-2xl font-bold ${
                isSuccess ? "text-green-500" : "text-red-500"
              }`}
            >
              {isSuccess ? "Succès" : "Erreur"}
            </h2>
            <p className="mt-4">{modalMessage}</p>
            <button
              onClick={() => setShowModal(false)}
              className="btn btn-primary mt-4"
            >
              Fermer
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Notes;
