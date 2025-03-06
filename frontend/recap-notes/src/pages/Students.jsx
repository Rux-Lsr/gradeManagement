import React, { useState, useEffect } from "react";
import CSVReader from "react-csv-reader";

const Students = () => {
  const [students, setStudents] = useState([]);
  const [modules, setModules] = useState([]);
  const [nom, setNom] = useState("");
  const [prenom, setPrenom] = useState("");
  const [matricule, setMatricule] = useState("");
  const [moduleId, setModuleId] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [modalMessage, setModalMessage] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);

  useEffect(() => {
    fetch("http://127.0.0.1:8000/etudiants")
      .then((response) => response.json())
      .then((data) => setStudents(data))
      .catch((error) => console.error("Error fetching students:", error));

    fetch("http://127.0.0.1:8000/modules")
      .then((response) => response.json())
      .then((data) => setModules(data))
      .catch((error) => console.error("Error fetching modules:", error));
  }, []);

  const handleAddStudent = (e) => {
    e.preventDefault();
    const newStudent = { nom, prenom, matricule, moduleId };

    fetch("http://127.0.0.1:8000/etudiants", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(newStudent),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.rowsAffected > 0) {
          setStudents([...students, newStudent]);
          setNom("");
          setPrenom("");
          setMatricule("");
          setModuleId("");
          setShowForm(false);
          setIsSuccess(true);
          setModalMessage("Étudiant ajouté avec succès !");
        } else {
          setIsSuccess(false);
          setModalMessage("Erreur lors de l'ajout de l'étudiant.");
        }
        setShowModal(true);
      })
      .catch((error) => {
        console.error("Error adding student:", error);
        setIsSuccess(false);
        setModalMessage("Erreur lors de l'ajout de l'étudiant.");
        setShowModal(true);
      });
  };

  const handleImport = (data) => {
    data.forEach((row) => {
      const [nom, prenom, matricule, moduleNom] = row;
      const module = modules.find((mod) => mod.nom === moduleNom);
      if (module) {
        const newStudent = { nom, prenom, matricule, moduleId: module.id };

        fetch("http://127.0.0.1:8000/etudiants", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(newStudent),
        })
          .then((response) => response.json())
          .then((data) => {
            if (data.rowsAffected > 0) {
              setStudents((prevStudents) => [...prevStudents, newStudent]);
            }
          })
          .catch((error) => console.error("Error adding student:", error));
      }
    });
    setIsSuccess(true);
    setModalMessage("Étudiants importés avec succès !");
    setShowModal(true);
  };

  return (
    <div className="p-8">
      <div className="card bg-base-100 shadow-xl mb-8">
        <div className="card-body">
          <h1 className="card-title text-2xl font-bold">Ajouter un Étudiant</h1>
          <button
            onClick={() => setShowForm(!showForm)}
            className="btn btn-secondary mb-4"
          >
            {showForm ? "Masquer le formulaire" : "Afficher le formulaire"}
          </button>
          {showForm && (
            <form onSubmit={handleAddStudent} className="space-y-4">
              <div>
                <label className="block text-sm font-medium">Nom</label>
                <input
                  type="text"
                  value={nom}
                  onChange={(e) => setNom(e.target.value)}
                  className="input input-bordered w-full"
                  required
                />
              </div>
              <div>
                <label className="block text-sm font-medium">Prénom</label>
                <input
                  type="text"
                  value={prenom}
                  onChange={(e) => setPrenom(e.target.value)}
                  className="input input-bordered w-full"
                  required
                />
              </div>
              <div>
                <label className="block text-sm font-medium">Matricule</label>
                <input
                  type="text"
                  value={matricule}
                  onChange={(e) => setMatricule(e.target.value)}
                  className="input input-bordered w-full"
                  required
                />
              </div>
              <div>
                <label className="block text-sm font-medium">Module</label>
                <select
                  value={moduleId}
                  onChange={(e) => setModuleId(e.target.value)}
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
              </div>
              <button type="submit" className="btn btn-primary">
                Ajouter
              </button>
            </form>
          )}
          <CSVReader
            onFileLoaded={handleImport}
            parserOptions={{ header: false }}
            inputId="csv-reader"
            inputStyle={{ display: "none" }}
          />
          <label htmlFor="csv-reader" className="btn btn-secondary mt-4">
            Importer CSV
          </label>
        </div>
      </div>
      <div className="card bg-base-100 shadow-xl">
        <div className="card-body">
          <h1 className="card-title text-2xl font-bold">Liste des Étudiants</h1>
          <ul className="space-y-4 mt-4">
            {students.map((student, index) => (
              <li key={index} className="border p-4 rounded-lg">
                <h2 className="text-xl font-bold">
                  {student.nom} {student.prenom}
                </h2>
                <p>Matricule: {student.matricule}</p>
                <p>
                  Module:{" "}
                  {
                    modules.find((module) => module.id === student.moduleId)
                      ?.nom
                  }
                </p>
              </li>
            ))}
          </ul>
        </div>
      </div>
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

export default Students;
