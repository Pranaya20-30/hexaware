import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../services/api";
import Navbar from "../components/Navbar";

const EditBook = () => {
  const { isbn } = useParams();
  const navigate = useNavigate();
  const [formData, setFormData] = useState({ title: "", author: "", publicationYear: "", isbn: "" });

  useEffect(() => {
    const fetchBook = async () => {
      try {
        const res = await api.get(`/api/books/${isbn}`);
        setFormData(res.data);
      } catch (err) {
        console.error("Failed to load book", err);
      }
    };
    fetchBook();
  }, [isbn]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.put(`/api/books/${isbn}`, formData);
      navigate("/home");
    } catch (error) {
      console.error("Update failed", error);
    }
  };

  return (

    <>
      <Navbar />
    <div className="container mt-4">
      <div className="card shadow p-4">
        <h3 className="mb-3">Edit Book</h3>
        <form onSubmit={handleSubmit}>
          <input name="title" value={formData.title} className="form-control mb-3" onChange={handleChange} required />
          <input name="author" value={formData.author} className="form-control mb-3" onChange={handleChange} required />
          <input name="publicationYear" value={formData.publicationYear} type="number" className="form-control mb-3" onChange={handleChange} required />
          <input name="isbn" value={formData.isbn} className="form-control mb-3" disabled />
          <button type="submit" className="btn btn-primary">Update Book</button>
        </form>
      </div>
    </div>
  );
    </>
    );
};

export default EditBook;