
import { Route, Routes } from "react-router-dom";
import Auth from "./containers/Auth/Auth";
import Header from "./containers/Header/Header";
import Home from "./containers/Home/Home";
import CreateRecipe from "./containers/CreateRecipe/CreateRecipe";
import ProfileContainer from "./containers/Profile/ProfileContainer";
import Search from "./containers/Search/Search";
import { useState, useEffect } from "react";
import {ThemeContext} from './contexts/Theme'
import Footer from "./containers/Footer/Footer";
import { AuthContext } from "./contexts/AuthContext";
import "./App.css";

const App = () => {
  const[currentUserFirstName, setCurrentUserFirstName] = useState(null)
  const[currentBook, setCurrentBook] = useState(null)
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

  const updateUser = (updatedData) => {
    // Logic to update user profile
    setCurrentUserFirstName(updatedData.firstName);
    // Update other user data as needed bg-[F4F1DE]
  };

  return (
    <AuthContext.Provider value={{currentUserFirstName, setCurrentUserFirstName, currentBook, setCurrentBook}}>
    <ThemeContext.Provider value ={{theme, setTheme: toggleTheme}}>
    <main className={theme}>
      <Header/>
      <Routes>
        <Route path="/home" element={<Home />} />
        <Route path="/" element={<Auth />} />
        <Route path="/createrecipe" element={<CreateRecipe />} />
        <Route path="/search" element={<Search />} />
        <Route path="/profile" element={<ProfileContainer />} />
        {/* currentUser={{ firstName: currentUserFirstName }} updateUser={updateUser}  */}
      </Routes>
      <Footer />
    </main>
    </ThemeContext.Provider>
    </AuthContext.Provider>

  );
};

export default App;
