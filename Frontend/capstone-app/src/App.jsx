
import { Route, Routes } from "react-router-dom";
import Auth from "./containers/Auth/Auth";
import Header from "./containers/Header/Header";
import Home from "./containers/Home/Home";
import CreateRecipe from "./containers/CreateRecipe/CreateRecipe";
import Profile from "./containers/Profile/Profile";
import Search from "./containers/Search/Search";
import { useState, useEffect } from "react";
import {ThemeContext} from './contexts/Theme'
// import { AuthProvider } from "./components/Auth/AuthContext";
import Footer from "./containers/Footer/Footer";
import "./App.css";
const App = () => {
  const[theme, setTheme] = useState("light")

  useEffect(() => {
    const savedTheme = localStorage.getItem('theme') || 'light';
    setTheme(savedTheme);
  }, []);

  useEffect(() => {
    document.body.className = theme; // Update the body class based on the theme
  }, [theme]);

  const toggleTheme = () => {
    const newTheme = theme === "light" ? "dark" : "light";
    setTheme(newTheme);
    localStorage.setItem('theme', newTheme);
  };

  return (
        // <AuthProvider>
    <ThemeContext.Provider value ={{theme, setTheme: toggleTheme}}>
    <main className={theme}>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/entry" element={<Auth />} />
        <Route path="/createrecipe" element={<CreateRecipe />} />
        <Route path="/search" element={<Search />} />
        <Route path="/profile" element={<Profile />} />
      </Routes>
      <Footer />
    </main>
    </ThemeContext.Provider>


  );
};

export default App;
