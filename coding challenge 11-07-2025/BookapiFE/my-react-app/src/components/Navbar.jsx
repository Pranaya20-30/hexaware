import React from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { FaSignInAlt, FaUserPlus, FaArrowLeft, FaSignOutAlt, FaBook } from 'react-icons/fa';

const Navbar = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('username'); // Optional cleanup
    navigate('/login');
  };

  const isLoggedIn = !!localStorage.getItem('token');
  const isLoginPage = location.pathname === '/login';
  const isRegisterPage = location.pathname === '/';
  const showBack = location.pathname !== '/home' && location.pathname !== '/home';

  return (
    <nav style={styles.nav}>
      <div style={styles.leftSection}>
        {showBack && (
          <button onClick={() => navigate(-1)} style={styles.backButton}>
            <FaArrowLeft />
          </button>
        )}
        <h2 style={styles.logo}><FaBook style={{ marginRight: '8px' }} /> HEXALIB</h2>
      </div>
      <div style={styles.links}>
        {!isLoggedIn && isLoginPage && (
          <Link to="/" style={styles.link}><FaUserPlus /> Register</Link>
        )}
        {!isLoggedIn && isRegisterPage && (
          <Link to="/login" style={styles.link}><FaSignInAlt /> Login</Link>
        )}
        {isLoggedIn && (
          <button onClick={handleLogout} style={styles.logoutButton}><FaSignOutAlt /> Logout</button>
        )}
      </div>
    </nav>
  );
};

const styles = {
  nav: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    backgroundColor: '#1a1a2e',
    padding: '1rem 2rem',
    color: '#fff',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)'
  },
  leftSection: {
    display: 'flex',
    alignItems: 'center',
    gap: '1rem'
  },
  logo: {
    fontWeight: 'bold',
    fontSize: '1.5rem',
    display: 'flex',
    alignItems: 'center',
    color: '#00adb5'
  },
  links: {
    display: 'flex',
    alignItems: 'center',
    gap: '1rem'
  },
  link: {
    color: '#fff',
    textDecoration: 'none',
    display: 'flex',
    alignItems: 'center',
    gap: '0.3rem',
    fontSize: '1rem'
  },
  logoutButton: {
    backgroundColor: '#ff4b5c',
    border: 'none',
    color: '#fff',
    padding: '0.5rem 1rem',
    cursor: 'pointer',
    borderRadius: '5px',
    display: 'flex',
    alignItems: 'center',
    gap: '0.4rem'
  },
  backButton: {
    background: 'none',
    border: 'none',
    color: '#fff',
    fontSize: '1.2rem',
    cursor: 'pointer'
  }
};

export default Navbar;
