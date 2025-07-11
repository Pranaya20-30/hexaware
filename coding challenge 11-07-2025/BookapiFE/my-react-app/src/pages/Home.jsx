import React, { useEffect, useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";
import { BookOpen, Edit3, Trash2, PlusCircle, User2, CalendarDays, BadgePlus } from "lucide-react";

const Home = () => {
  const navigate = useNavigate();
  const [books, setBooks] = useState([]);
  const username = localStorage.getItem("username");

  const fetchBooks = async () => {
    try {
      const response = await api.get("/api/books");
      setBooks(response.data);
    } catch (err) {
      console.error("Failed to fetch books", err);
    }
  };

  const handleDelete = async (isbn) => {
    try {
      await api.delete(`/api/books/${isbn}`);
      fetchBooks();
    } catch (err) {
      console.error("Failed to delete book", err);
    }
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  return (
    <>
      <Navbar />
      <div className="container mt-4">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <h2><BookOpen className="me-2" size={24} /> Book List</h2>
          <button className="btn btn-success d-flex align-items-center" onClick={() => navigate("/add-book")}> 
            <PlusCircle className="me-1" size={18} /> Add Book
          </button>
        </div>

        {username && (
          <div className="alert alert-info d-flex align-items-center">
            <User2 className="me-2" size={18} />
            <strong>Welcome, {username}!</strong> You're logged in.
          </div>
        )}

        <div className="row">
          {books.map((book) => (
            <div key={book.isbn} className="col-md-4 mb-4">
              <div className="card h-100 shadow">
                <div className="card-body">
                  <h5 className="card-title">{book.title}</h5>
                  <h6 className="card-subtitle mb-2 text-muted">{book.author}</h6>
                  <p className="card-text d-flex align-items-center"><CalendarDays className="me-2" size={16} /> Year: {book.publicationYear}</p>
                  <p className="card-text d-flex align-items-center"><BadgePlus className="me-2" size={16} /> ISBN: {book.isbn}</p>
                  <div className="d-flex justify-content-between">
                    <button
                      className="btn btn-primary btn-sm d-flex align-items-center"
                      onClick={() => navigate(`/edit-book/${book.isbn}`)}
                    >
                      <Edit3 className="me-1" size={16} /> Edit
                    </button>
                    <button
                      className="btn btn-danger btn-sm d-flex align-items-center"
                      onClick={() => handleDelete(book.isbn)}
                    >
                      <Trash2 className="me-1" size={16} /> Delete
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default Home;
