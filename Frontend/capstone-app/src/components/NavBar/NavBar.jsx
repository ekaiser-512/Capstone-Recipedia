import { useContext } from "react"
import { AuthContext } from "../../contexts/AuthContext"
import { ThemeContext } from "../../contexts/Theme"
import Button from "../Button/Button"
import Dark from '../../components/Dark/Dark'
import "./NavBar.css"


const NavBar = () => {
    const { theme, setTheme } = useContext(ThemeContext)
    const {currentUsername, setCurrentUsername} = useContext(AuthContext)

    return (
        <nav className="navbar">
            <div className="navbar-appname">Recipedia</div>
            {
                currentUsername && <>
                <ul className="nav-link-container">
                <li>Home</li>
                <li>Create Recipe</li>
                <li>Search</li>
            </ul>
            <div className="navbar-profile-signout">
                <strong>
                    {currentUsername[0].toUpperCase()}
                </strong>
                <Button text="Sign Out" style="button-light"/>
            </div>
            <div className="toggle-container">
                <Dark className={'theme'} onChange={setTheme}/>
                <label>Dark Mode</label>
            </div></>
            }

        </nav>
    )
}

export default NavBar