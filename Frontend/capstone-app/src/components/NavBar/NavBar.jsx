import { useContext } from "react"
import { AuthContext } from "../../contexts/AuthContext"
import { ThemeContext } from "../../contexts/Theme"
import { useNavigate } from "react-router-dom";
import Button from "../Button/Button"
import Dark from '../../components/Dark/Dark'
import "./NavBar.css"


const NavBar = () => {
    const navigate = useNavigate();
    const { theme, setTheme } = useContext(ThemeContext)
    const {currentUserFirstName, setCurrentUserFirstName} = useContext(AuthContext)

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
        setCurrentUserFirstName(null)
        navigate("/")
    }

    const onProfileClick = (event) => {
        event.preventDefault();
        navigate("/profile");
    };
    

    return (
        <nav className="navbar">
            <div className="navbar-appname">Recipedia</div>
            {
                currentUserFirstName && <>
                <ul className="nav-link-container">
                <Button handleClick={onHomeButtonClick} text="Home" style="button-light"/>
                <Button handleClick={onCreateRecipeButtonClick} text="Create Recipe" style="button-light"/>
                <Button handleClick={onSearchButtonClick} text="Search" style="button-light"/>
            </ul>
            <div className="navbar-profile-signout">
                <strong onClick={onProfileClick} style={{ cursor: 'pointer' }}>
                    {currentUserFirstName[0].toUpperCase()}
                </strong>
                <Button handleClick={onSignOutButtonClick} text="Sign Out" style="button-light"/>
            </div>
            <div className="toggle-container">
                <Dark className={'theme'} onChange={setTheme}/>
                <label>Dark Mode</label>
            </div></>
            }

        </nav>
    )
}

export default NavBar;