import React, { useState } from 'react';
import './Search.css'
import Button from '../../components/Button/Button';
import Input from '../../components/Input/Input';

const Search = () => {
    const [query, setQuery] = useState('');
    const [results, setResults] = useState([]);
  
    const handleSearch = () => {
      // Simulate a search operation
      const sampleResults = [
        'Result 1',
        'Result 2',
        'Result 3',
        'Result 4',
      ];
  
      // Filter results based on the query
      const filteredResults = sampleResults.filter(result =>
        result.toLowerCase().includes(query.toLowerCase())
      );
  
      setResults(filteredResults);
    };
  
    return (
      <div className="SearchPage">
        <h1>Search Page</h1>
        <div className="search-form">
          <input
            type="text"
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            placeholder="Search..."
          />
          <Button onClick={handleSearch}>Search</Button>
          <ul>
            {results.map((result, index) => (
              <li key={index}>{result}</li>
            ))}
          </ul>
        </div>
      </div>
    );
  };

export default Search;