
import { Route, Routes } from "react-router-dom";
import Auth from "./containers/Auth/Auth";
import Header from "./containers/Header/Header";
import Home from "./containers/Home/Home";
import CreateRecipe from "./containers/CreateRecipe/CreateRecipe";
import Profile from "./containers/Profile/Profile";
import Search from "./containers/Search/Search";
// import Footer from "./containers/Footer/Footer";
import "./App.css";

const App = () => {

  return (
    <main>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/auth" element={<Auth />} />
        <Route path="/createrecipe" element={<CreateRecipe />} />
        <Route path="/search" element={<Search />} />
        <Route path="/profile" element={<Profile />} />
      </Routes>
      {/* <Footer /> */}
    </main>

  );
};

export default App;
