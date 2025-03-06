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
          <ul className="space-y-4 mt-4">
            {modules.map((module, index) => (
              <li key={index} className="border p-4 rounded-lg">
                <h2 className="text-xl font-bold">{module.nom}</h2>
                <p>{module.description}</p>
                <p className="font-semibold">Crédit: {module.credit}</p>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default UE;
