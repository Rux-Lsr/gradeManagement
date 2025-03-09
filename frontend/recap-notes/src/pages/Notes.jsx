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
  const [moduleValidated, setModuleValidated] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const [currentBatch, setCurrentBatch] = useState([]);
  const [notification, setNotification] = useState(null);

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

        // Récupérer toutes les notes et filtrer côté client
        fetch("http://127.0.0.1:8000/notes")
          .then((response) => response.json())
          .then((data) => {
            // Filtrer les notes pour ce module
            const moduleEvaluationIds = module.evaluationSet.map(
              (eva) => eva.id
            );
            const moduleNotes = data.filter((note) =>
              moduleEvaluationIds.includes(note.evaluationId)
            );

            setNotes(moduleNotes);
            const initialGrades = {};
            moduleNotes.forEach((note) => {
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

    // Convert the date string to Unix timestamp (milliseconds since epoch)
    const isoDate = new Date(evaluationDate).toISOString();

    const newEvaluation = {
      moduleId: selectedModule,
      date: isoDate, // Send as integer timestamp
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
          setEvaluationType(""); // Correction ici
          setEvaluationDate("");
          setEvaluationCoef("");
          setEvaluationMax("");
          setNotification({
            type: "success",
            message: "Évaluation ajoutée avec succès !",
          });
          setTimeout(() => setNotification(null), 3000);
        } else {
          setNotification({
            type: "error",
            message: "Erreur lors de l'ajout de l'évaluation.",
          });
          setTimeout(() => setNotification(null), 3000);
        }
      })
      .catch((error) => {
        console.error("Error adding evaluation:", error);
        setNotification({
          type: "error",
          message: "Erreur lors de l'ajout de l'évaluation.",
        });
        setTimeout(() => setNotification(null), 3000);
      });
  };

  const handleGradeChange = (studentId, evaluationId, value) => {
    setGrades({
      ...grades,
      [`${studentId}-${evaluationId}`]: value,
    });

    // Mettre à jour le lot courant
    setCurrentBatch((prev) => [
      ...prev.filter(
        (item) =>
          !(item.etudiantId === studentId && item.evaluationId === evaluationId)
      ),
      {
        etudiantId: parseInt(studentId),
        evaluationId: parseInt(evaluationId),
        note: value === "" ? 0 : parseFloat(value),
      },
    ]);
  };

  const handleBatchSave = async () => {
    if (currentBatch.length === 0) return;

    try {
      setNotification({
        type: "info",
        message: "Enregistrement en cours...",
      });

      await Promise.all(
        currentBatch.map(async (update) => {
          // D'abord, vérifier si la note existe
          const existingNotes = await fetch("http://127.0.0.1:8000/notes").then(
            (res) => res.json()
          );

          const noteExists = existingNotes.some(
            (note) =>
              note.etudiantId === update.etudiantId &&
              note.evaluationId === update.evaluationId
          );

          // Utiliser POST pour créer, PUT pour mettre à jour
          return fetch("http://127.0.0.1:8000/notes", {
            method: noteExists ? "PUT" : "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(update),
          });
        })
      );

      setCurrentBatch([]);
      setNotification({ type: "success", message: "Notes enregistrées" });
      setTimeout(() => setNotification(null), 3000);
    } catch (error) {
      console.error("Error saving grades:", error);
      setNotification({
        type: "error",
        message: "Erreur lors de l'enregistrement",
      });
      setTimeout(() => setNotification(null), 3000);
    }
  };

  // Ajouter cette nouvelle fonction après handleBatchSave
  const handleDeleteEvaluation = async (evaluationId) => {
    if (
      !window.confirm("Êtes-vous sûr de vouloir supprimer cette évaluation ?")
    ) {
      return;
    }

    try {
      const response = await fetch("http://127.0.0.1:8000/evaluations", {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ id: evaluationId }),
      });
      const data = await response.json();

      if (data.rowsAffected > 0) {
        setEvaluations(evaluations.filter((e) => e.id !== evaluationId));
        // Supprimer également les notes associées à cette évaluation
        const updatedGrades = { ...grades };
        Object.keys(updatedGrades).forEach((key) => {
          if (key.endsWith(`-${evaluationId}`)) {
            delete updatedGrades[key];
          }
        });
        setGrades(updatedGrades);

        setNotification({
          type: "success",
          message: "Évaluation supprimée avec succès",
        });
      } else {
        setNotification({
          type: "error",
          message: "Erreur lors de la suppression de l'évaluation",
        });
      }
      setTimeout(() => setNotification(null), 3000);
    } catch (error) {
      console.error("Error deleting evaluation:", error);
      setNotification({
        type: "error",
        message: "Erreur lors de la suppression de l'évaluation",
      });
      setTimeout(() => setNotification(null), 3000);
    }
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
              <div className="flex justify-between items-center mb-4">
                <h1 className="card-title text-2xl font-bold">
                  Liste des Étudiants
                </h1>
                <div className="flex gap-2">
                  <button
                    onClick={() => setEditMode(!editMode)}
                    className={`btn ${
                      editMode ? "btn-primary" : "btn-outline"
                    }`}
                  >
                    {editMode ? "Mode Lecture" : "Mode Édition"}
                  </button>
                  <button
                    onClick={handleBatchSave}
                    className={`btn ${
                      currentBatch.length > 0
                        ? "btn-success animate-pulse"
                        : "btn-disabled"
                    }`}
                  >
                    Sauvegarder ({currentBatch.length})
                  </button>
                </div>
              </div>

              {/* Notification flottante */}
              {notification && (
                <div
                  className={`fixed top-4 right-4 p-4 rounded-lg shadow-lg ${
                    notification.type === "success"
                      ? "bg-green-500 text-white"
                      : "bg-red-500 text-white"
                  } transition-all duration-500 ease-in-out transform`}
                >
                  <div className="flex items-center">
                    {notification.type === "success" ? (
                      <svg
                        className="w-6 h-6 mr-2"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth="2"
                          d="M5 13l4 4L19 7"
                        />
                      </svg>
                    ) : (
                      <svg
                        className="w-6 h-6 mr-2"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth="2"
                          d="M6 18L18 6M6 6l12 12"
                        />
                      </svg>
                    )}
                    <span>{notification.message}</span>
                  </div>
                </div>
              )}

              <div className="overflow-x-auto">
                <table className="table table-zebra w-full">
                  <thead>
                    <tr>
                      <th>Nom</th>
                      <th>Prénom</th>
                      {evaluations.map((evaluation) => (
                        <th key={evaluation.id} className="text-center">
                          <div className="flex flex-col items-center">
                            <div className="flex justify-between items-center w-full">
                              <div>{evaluation.typeEvaluation}</div>
                              {editMode && (
                                <button
                                  onClick={() =>
                                    handleDeleteEvaluation(evaluation.id)
                                  }
                                  className="btn btn-error btn-xs"
                                >
                                  <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    fill="none"
                                    viewBox="0 0 24 24"
                                    strokeWidth={1.5}
                                    stroke="currentColor"
                                    className="w-4 h-4"
                                  >
                                    <path
                                      strokeLinecap="round"
                                      strokeLinejoin="round"
                                      d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0"
                                    />
                                  </svg>
                                </button>
                              )}
                            </div>
                            <div className="text-sm opacity-70">
                              {new Date(evaluation.date).toLocaleDateString()}
                            </div>
                            <div className="text-xs opacity-50">
                              Max: {evaluation.max} - Coef: {evaluation.coef}
                            </div>
                          </div>
                        </th>
                      ))}
                      <th className="text-center">Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                    {students.map((student) => (
                      <tr key={student.id}>
                        <td>{student.nom}</td>
                        <td>{student.prenom}</td>
                        {evaluations.map((evaluation) => {
                          const gradeKey = `${student.id}-${evaluation.id}`;
                          const currentGrade = grades[gradeKey] || "";
                          return (
                            <td key={evaluation.id} className="text-center">
                              {editMode ? (
                                <input
                                  type="number"
                                  step="0.01"
                                  min="0"
                                  max={evaluation.max}
                                  value={currentGrade}
                                  onChange={(e) => {
                                    const value = e.target.value;
                                    if (
                                      value === "" ||
                                      (parseFloat(value) >= 0 &&
                                        parseFloat(value) <= evaluation.max)
                                    ) {
                                      handleGradeChange(
                                        student.id,
                                        evaluation.id,
                                        value
                                      );
                                    }
                                  }}
                                  className="input input-bordered input-sm w-20 text-center"
                                />
                              ) : (
                                <span
                                  className={
                                    currentGrade >= evaluation.max * 0.7
                                      ? "text-success"
                                      : currentGrade < evaluation.max * 0.5
                                      ? "text-error"
                                      : "text-warning"
                                  }
                                >
                                  {currentGrade}
                                </span>
                              )}
                            </td>
                          );
                        })}
                        <td className="text-center">
                          {editMode && (
                            <button
                              className="btn btn-ghost btn-xs"
                              onClick={() => {
                                // Réinitialiser les notes pour cet étudiant
                                const studentGrades = evaluations.reduce(
                                  (acc, ev) => {
                                    acc[`${student.id}-${ev.id}`] = "";
                                    return acc;
                                  },
                                  {}
                                );
                                setGrades((prev) => ({
                                  ...prev,
                                  ...studentGrades,
                                }));
                              }}
                            >
                              Réinitialiser
                            </button>
                          )}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default Notes;
