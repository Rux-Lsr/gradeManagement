import { Routes, Route, Link } from "react-router-dom";
import Home from "./pages/home/Home.jsx";
import Note from "./pages/note/Note.jsx";
import Students from "./pages/etudiant/Students.jsx";
import Recap from "./pages/recap/Recap.jsx";
function App() {
  return (
    <div className="p-4">
      {/* <nav className="mb-4">
        <Link to="/" className="mr-4">
          Accueil
        </Link>
        <Link to="/students" className="mr-4">
          Étudiants
        </Link>
        <Link to="/notes" className="mr-4">
          Notes
        </Link>
        <Link to="/recap">Récapitulatif</Link>
      </nav> */}

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/students" element={<Students />} />
        <Route path="/notes" element={<Note />} />
        <Route path="/recap" element={<Recap />} />
      </Routes>
    </div>
  );
}

export default App;
