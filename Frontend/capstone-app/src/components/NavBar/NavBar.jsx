import { useContext } from "react"
import { AuthContext } from "../../contexts/AuthContext"
import { ThemeContext } from "../../contexts/Theme"
import { useNavigate, Link } from "react-router-dom";
import Button from "../Button/Button"
import Dark from '../../components/Dark/Dark'
import "./NavBar.css"


const NavBar = () => {
    const navigate = useNavigate();
    const { theme, setTheme } = useContext(ThemeContext)
    const {currentUser, setCurrentUser} = useContext(AuthContext)

    const onHomeButtonClick = (event) => {
        event.preventDefault()
        navigate("/home")
    }

    const onCreateRecipeButtonClick= (event) => {
        event.preventDefault()
        navigate("/createrecipe")
    }

    const onSearchButtonClick= (event) => {
        event.preventDefault()
        navigate("/search")
    }

    const onSignOutButtonClick= (event) => {
        event.preventDefault()
        setCurrentUser(null)
        navigate("/")
    }

    const onProfileClick = (event) => {
        event.preventDefault();
        navigate("/profile");
    };

    return (
    <nav className="navbar navbar-expand-lg bg-body-none">
        <div className="container-fluid">
            
   
         {
         currentUser && <>
            {currentUser.firstName ? ( 
                    <Link className="navbar-appname navbar-brand" to="/home">Recipedia</Link>
            ) : (
            <span className="navbar-appname navbar-brand">Recipedia</span>
            )}
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span className="navbar-toggler-icon"></span></button>
            <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="nav-link-container navbar-nav me-auto mb-2 mb-lg-0"><Button handleClick={onHomeButtonClick} text="Home" style="button-light"/><Button handleClick={onCreateRecipeButtonClick} text="Create Recipe" style="button-light"/><Button handleClick={onSearchButtonClick} text="Search" style="button-light"/>
                </ul>
            <div className="navbar-profile-signout">
                <strong onClick={onProfileClick} style={{ cursor: 'pointer' }}>
                    {currentUser.firstName[0].toUpperCase()}
                </strong>
                <Button handleClick={onSignOutButtonClick} text="Sign Out" style="button-light"/>
            </div>
            <div className="toggle-container">
                <Dark className={'theme'} onChange={setTheme}/>
                <label>Dark Mode</label>
            </div>
            </div>
            </>
         
            } 
            </div>
        </nav>
    )
}

export default NavBar;