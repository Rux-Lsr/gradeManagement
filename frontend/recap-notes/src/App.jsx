import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Sidebar from "./components/SideBar";
import UE from "./pages/UE";
import Students from "./pages/Students";
import Notes from "./pages/Notes";
import Recap from "./pages/Recap";

function App() {
  return (
    <div className="flex">
      <Sidebar />
      <div className="ml-64 flex-1 p-4">
        <Routes>
          <Route path="/" element={<UE />} />
          <Route path="/students" element={<Students />} />
          <Route path="/notes" element={<Notes />} />
          <Route path="/recap" element={<Recap />} />
        </Routes>
      </div>
    </div>
  );
}

export default App;
