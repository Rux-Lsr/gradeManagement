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
    const updates = Object.keys(grades)
      .map((key) => {
        const [studentId, evaluationId] = key.split("-");
        const gradeValue = grades[key];
        return {
          etudiantId: parseInt(studentId),
          evaluationId: parseInt(evaluationId),
          note: gradeValue === "" ? 0 : parseFloat(gradeValue), // Set empty values to 0
        };
      })
      .filter((update) => !isNaN(update.note)); // Only filter NaN values

    // If no valid updates, return early
    if (updates.length === 0) {
      setIsSuccess(false);
      setModalMessage("Aucune note valide à enregistrer.");
      setShowModal(true);
      return;
    }

    Promise.all(
      updates.map((update) =>
        fetch("http://127.0.0.1:8000/notes", {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(update),
        }).then((response) => {
          if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
          }
          return response.json();
        })
      )
    )
      .then((results) => {
        const success = results.every((result) => result.rowsAffected > 0);
        if (success) {
          // Update local grades state with zeros for empty values
          const updatedGrades = { ...grades };
          Object.keys(updatedGrades).forEach((key) => {
            if (updatedGrades[key] === "") {
              updatedGrades[key] = 0;
            }
          });
          setGrades(updatedGrades);
        }
        setIsSuccess(success);
        setModalMessage(
          success
            ? "Notes mises à jour avec succès !"
            : "Erreur lors de la mise à jour des notes."
        );
        setShowModal(true);
      })
      .catch((error) => {
        console.error("Error updating grades:", error);
        setIsSuccess(false);
        setModalMessage(
          "Erreur lors de la mise à jour des notes: " + error.message
        );
        setShowModal(true);
      });
  };

  const handleBatchSave = async () => {
    if (currentBatch.length === 0) return;

    try {
      setIsSuccess(false);
      setModalMessage("Enregistrement en cours...");
      setShowModal(true);

      await Promise.all(
        currentBatch.map((update) =>
          fetch("http://127.0.0.1:8000/notes", {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(update),
          })
        )
      );

      setCurrentBatch([]);
      setNotification({ type: "success", message: "Lot de notes enregistré" });
      setTimeout(() => setNotification(null), 3000);
    } catch (error) {
      setNotification({
        type: "error",
        message: "Erreur lors de l'enregistrement",
      });
    }
  };

  const handleValidateModule = () => {
    setModuleValidated(true);
  };

  // Add an autopersist function
  const autoPersistGrade = async (studentId, evaluationId, value) => {
    try {
      const update = {
        etudiantId: parseInt(studentId),
        evaluationId: parseInt(evaluationId),
        note: value === "" ? 0 : parseFloat(value),
      };

      const response = await fetch("http://127.0.0.1:8000/notes", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(update),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      setNotification({
        type: "success",
        message: "Note sauvegardée",
      });
      setTimeout(() => setNotification(null), 2000);
    } catch (error) {
      setNotification({
        type: "error",
        message: "Erreur de sauvegarde",
      });
    }
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
                    className="btn btn-success"
                    disabled={currentBatch.length === 0}
                  >
                    Sauvegarder ({currentBatch.length})
                  </button>
                </div>
              </div>

              {/* Notification flottante */}
              {notification && (
                <div
                  className={`alert ${
                    notification.type === "success"
                      ? "alert-success"
                      : "alert-error"
                  } mb-4`}
                >
                  <span>{notification.message}</span>
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
                          <div>{evaluation.typeEvaluation}</div>
                          <div className="text-sm opacity-70">
                            {new Date(evaluation.date).toLocaleDateString()}
                          </div>
                          <div className="text-xs opacity-50">
                            Max: {evaluation.max} - Coef: {evaluation.coef}
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
                                      // Debounce the auto-persist
                                      const timeoutId = setTimeout(() => {
                                        autoPersistGrade(
                                          student.id,
                                          evaluation.id,
                                          value
                                        );
                                      }, 1000);
                                      return () => clearTimeout(timeoutId);
                                    }
                                  }}
                                  onBlur={() => {
                                    // Persist on blur for immediate feedback
                                    if (currentGrade !== "") {
                                      autoPersistGrade(
                                        student.id,
                                        evaluation.id,
                                        currentGrade
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
