import { useState, useEffect } from "react";

const UE = () => {
  const [modules, setModules] = useState([]);
  const [nom, setNom] = useState("");
  const [description, setDescription] = useState("");
  const [credit, setCredit] = useState("");
  const [showForm, setShowForm] = useState(false);

  useEffect(() => {
    fetch("http://127.0.0.1:8000/modules")
      .then((response) => response.json())
      .then((data) => setModules(data))
      .catch((error) => console.error("Error fetching modules:", error));
  }, []);

  const handleAddModule = (e) => {
    e.preventDefault();
    const newModule = { nom, description, credit: Number(credit) };

    fetch("http://127.0.0.1:8000/modules", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(newModule),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.rowsAffected > 0) {
          setModules([...modules, newModule]);
          setNom("");
          setDescription("");
          setCredit("");
          setShowForm(false);
        }
      })
      .catch((error) => console.error("Error adding module:", error));
  };

  const handleDeleteModule = (moduleId) => {
    if (window.confirm("Êtes-vous sûr de vouloir supprimer ce module ?")) {
      fetch("http://127.0.0.1:8000/modules", {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ id: moduleId }),
      })
        .then((response) => response.json())
        .then((data) => {
          if (data.rowsAffected > 0) {
            setModules(modules.filter((module) => module.id !== moduleId));
          }
        })
        .catch((error) => console.error("Error deleting module:", error));
    }
  };

  return (
    <div className="p-8">
      <div className="card bg-base-100 shadow-xl mb-8">
        <div className="card-body">
          <h1 className="card-title text-2xl font-bold">Ajouter un Module</h1>
          <button
            onClick={() => setShowForm(!showForm)}
            className="btn btn-secondary mb-4"
          >
            {showForm ? "Masquer le formulaire" : "Afficher le formulaire"}
          </button>
          {showForm && (
            <form onSubmit={handleAddModule} className="space-y-4">
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
                <label className="block text-sm font-medium">Description</label>
                <textarea
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                  className="textarea textarea-bordered w-full"
                  required
                />
              </div>
              <div>
                <label className="block text-sm font-medium">Crédit</label>
                <input
                  type="number"
                  value={credit}
                  onChange={(e) => setCredit(e.target.value)}
                  className="input input-bordered w-full"
                  required
                />
              </div>
              <button type="submit" className="btn btn-primary">
                Ajouter
              </button>
            </form>
          )}
        </div>
      </div>
      <div className="card bg-base-100 shadow-xl">
        <div className="card-body">
          <h1 className="card-title text-2xl font-bold">Liste des Modules</h1>
          <div className="overflow-x-auto">
            <table className="table w-full">
              <thead>
                <tr>
                  <th>Nom</th>
                  <th>Description</th>
                  <th>Crédit</th>
                  <th>Étudiants</th>
                  <th>Évaluations</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {modules.map((module) => (
                  <tr key={module.id}>
                    <td className="font-semibold">{module.nom}</td>
                    <td>{module.description}</td>
                    <td className="text-center">{module.credit}</td>
                    <td className="text-center">
                      {module.etudiantSet?.length || 0}
                    </td>
                    <td className="text-center">
                      {module.evaluationSet?.length || 0}
                    </td>
                    <td>
                      <button
                        onClick={() => handleDeleteModule(module.id)}
                        className="btn btn-error btn-sm"
                      >
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          fill="none"
                          viewBox="0 0 24 24"
                          strokeWidth={1.5}
                          stroke="currentColor"
                          className="w-5 h-5"
                        >
                          <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0"
                          />
                        </svg>
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UE;
