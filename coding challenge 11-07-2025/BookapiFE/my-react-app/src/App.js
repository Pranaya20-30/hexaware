import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import Home from './pages/Home';
import AddBook from './pages/AddBook';
import Navbar from './components/Navbar';
import EditBook from './pages/EditBook';



function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/home" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<Register />} />
        <Route path="/add-book" element={<AddBook />} />
        <Route path="navbar" element={<Navbar />} />
        <Route path="/edit-book/:isbn" element={<EditBook />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
