// src/pages/Home.jsx

import React from "react";
import { Link } from "react-router-dom"; // Assurez-vous d'avoir react-router-dom installé

function Home() {
  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Bienvenue sur Recap Notes !</h1>

      <nav>
        <ul className="list-none pl-0">
          <li className="mb-2">
            <Link to="/students" className="text-blue-500 hover:underline">
              Gestion des étudiants
            </Link>
          </li>
          <li className="mb-2">
            <Link to="/notes" className="text-blue-500 hover:underline">
              Saisie des notes
            </Link>
          </li>
          <li className="mb-2">
            <Link to="/recap" className="text-blue-500 hover:underline">
              Récapitulatif des notes
            </Link>
          </li>
        </ul>
      </nav>

      <p className="mt-4">
        Utilisez les liens ci-dessus pour naviguer vers les différentes sections
        de l'application.
      </p>
    </div>
  );
}

export default Home;
