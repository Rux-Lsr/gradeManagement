import { Link } from "react-router-dom";

const Sidebar = () => {
  return (
    <div className="h-screen w-64 bg-base-200 p-4 fixed">
      <div className="card card-side bg-base-100 shadow-xl mb-4">
        <figure>
          <img
            src="https://via.placeholder.com/40"
            alt="Profile"
            className="rounded-full w-16 h-16"
          />
        </figure>
        <div className="card-body">
          <h2 className="card-title">John Doe</h2>
          <p>Admin</p>
        </div>
      </div>
      <h1 className="text-xl font-bold mb-4">Gestion des Notes</h1>
      <ul className="menu bg-base-100 p-2 rounded-box">
        <li>
          <Link to="/" className="text-lg">
            🏠 Modules
          </Link>
        </li>
        <li>
          <Link to="/students" className="text-lg">
            👨‍🎓 Étudiants
          </Link>
        </li>
        <li>
          <Link to="/notes" className="text-lg">
            📝 Notes
          </Link>
        </li>
        <li>
          <Link to="/recap" className="text-lg">
            📊 Récapitulatif
          </Link>
        </li>
      </ul>
    </div>
  );
};

export default Sidebar;
