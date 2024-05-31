
import { Route, Routes } from "react-router-dom";
import Auth from "./containers/Auth/Auth";
import Header from "./containers/Header/Header";
import Home from "./containers/Home/Home";
import CreateRecipe from "./containers/CreateRecipe/CreateRecipe";
import Category from "./components/Category/Category";
import ProfileContainer from "./containers/Profile/ProfileContainer";
import Search from "./containers/Search/Search";
import { useState, useEffect } from "react";
import {ThemeContext} from './contexts/Theme'
import Footer from "./containers/Footer/Footer";
import { AuthContext } from "./contexts/AuthContext";
import "./App.css";

const App = () => {
  const[currentUser, setCurrentUser] = useState(null) // State for managing the current user
  const[theme, setTheme] = useState("light") // State for managing the theme (light or dark)

  useEffect(() => {
    const savedTheme = localStorage.getItem('theme') || 'light'; // Retrieving the saved theme from localStorage or using 'light' as default
    setTheme(savedTheme); // Setting the initial theme state
  }, []);

  useEffect(() => {
    document.body.className = theme; // Updating the body class based on the current theme
  }, [theme]);

  const toggleTheme = () => {
    const newTheme = theme === "light" ? "dark" : "light"; // Toggling the theme between 'light' and 'dark'
    setTheme(newTheme); // Updating the theme state
    localStorage.setItem('theme', newTheme); // Saving the new theme in localStorage
  };

  const updateUser = (updatedData) => {
    // Logic to update user profile
    setCurrentUser(updatedData); // Updating the currentUser state with the new data
    // Update other user data as needed
  };

  return (
    <AuthContext.Provider value={{currentUser, setCurrentUser}}> {/* Providing the AuthContext with the current user and a function to update it */}
    <ThemeContext.Provider value ={{theme, setTheme: toggleTheme}}> {/* Providing the ThemeContext with the current theme and a function to toggle it */}
    <main className={theme}>
      <Header/>
      <div className="content">
      <Routes>
        <Route path="/home" element={<Home />} />
        <Route path="/" element={<Auth />} />
        <Route path="/createrecipe" element={<CreateRecipe />} />
        <Route path="/search" element={<Search />} />
        <Route path="/profile" element={<ProfileContainer />} />
        <Route path="/categories/:categoryId" element={<Category />} />
      </Routes>
      </div>
      <Footer />
    </main>
    </ThemeContext.Provider>
    </AuthContext.Provider>

  );
};

export default App;
