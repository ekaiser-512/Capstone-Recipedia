import React, { useState } from 'react';
import './Search.css'
import Button from '../../components/Button/Button';
import Input from '../../components/Input/Input';

const Search = () => {
    const [query, setQuery] = useState(''); // State for managing the search query
    const [results, setResults] = useState([]); // State for managing the search results
  
    const handleSearch = () => {
      // Simulate a search operation with sample results
      const sampleResults = [
        'Result 1',
        'Result 2',
        'Result 3',
        'Result 4',
      ];
  
      // Filter results based on the query
      const filteredResults = sampleResults.filter(result =>
        result.toLowerCase().includes(query.toLowerCase()) // Case-insensitive search
      );
  
      setResults(filteredResults); // Update the results state with the filtered results
    };
  
    return (
      <div className="SearchPage"> {/* Container for the search page */}
        <h1>Search Page</h1> {/* Heading for the search page */}
        <div className="search-form"> {/* Container for the search form */}
          <input
            type="text"
            value={query} // Bind the input value to the query state
            onChange={(e) => setQuery(e.target.value)} // Update the query state on input change
            placeholder="Search..." // Placeholder text for the input field
          />
          <Button onClick={handleSearch}>Search</Button> {/* Button to trigger the search operation */}
          <ul>
            {results.map((result, index) => (
              <li key={index}>{result}</li> // Render each search result as a list item
            ))}
          </ul>
        </div>
      </div>
    );
  };

export default Search;