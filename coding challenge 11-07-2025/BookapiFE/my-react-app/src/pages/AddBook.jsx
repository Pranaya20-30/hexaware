import React, { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";

const AddBook = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    title: "",
    author: "",
    publicationYear: "",
    isbn: "",
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post("/api/books", formData);
      navigate("/home");
    } catch (err) {
      console.error("Failed to add book", err);
    }
  };

  return (
    <>
      <Navbar />
    <div className="container mt-5">
      <h2 className="mb-4">Add New Book</h2>
      <form onSubmit={handleSubmit} className="card p-4 shadow">
        <div className="mb-3">
          <label className="form-label">Title</label>
          <input type="text" name="title" className="form-control" value={formData.title} onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label className="form-label">Author</label>
          <input type="text" name="author" className="form-control" value={formData.author} onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label className="form-label">Publication Year</label>
          <input type="number" name="publicationYear" className="form-control" value={formData.publicationYear} onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label className="form-label">ISBN</label>
          <input type="text" name="isbn" className="form-control" value={formData.isbn} onChange={handleChange} required />
        </div>
        <button type="submit" className="btn btn-success">Add Book</button>
      </form>
    </div>
  );
  </>
  );
};

export default AddBook;