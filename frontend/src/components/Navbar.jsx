import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { searchItems } from './axios';
import ListPage from './ListPage';
import '../styles/Navbar.css';

const Navbar = () => {
  const [searchResults, setSearchResults] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');

  const handleSearchChange = async (event) => {         /* declares new function and takes 'event' as its parameter */
    setSearchQuery(event.target.value);

    /* calls the API to get search results based on the search query || try-catch handles potential errors that may occur during API call */
    try {
      const results = await searchItems(event.target.value);
      console.log('Response data:', results);
      setSearchResults(results);
    } catch (error) {
      console.error('Error fetching items:', error);
      setSearchResults([]);
    }
  };

  return (
    <div className="topnav">
      <ul className="topnav-list">
        <li><Link to="/">New In</Link></li>
        <li><Link to="/premiere">Hot Now</Link></li>
        <li><Link to="/">Sale</Link></li>
        <li><Link to="/">Dresses</Link></li>
        <li><Link to="/swimwear">Swimsuits</Link></li>
        <li><Link to="/">Loungewear</Link></li>
        <li><Link to="/ken">Mens</Link></li>

      <div className="search-bar">
        <input
          type="text"
          id="search-input"
          placeholder="Search Store..."
          value={searchQuery}
          onChange={handleSearchChange}
        />
        <button type="button" onClick={() => setSearchQuery('')}>
          Search
        </button>
      </div>
      </ul>
      <div className="search-results"><ListPage searchResults={searchResults}/></div>
    </div>
  );
};

export default Navbar;