import "./NavBar.css"
import Button from "../Button/Button"
import { useContext } from "react"
import { ThemeContext } from "../../contexts/Theme"
import Dark from '../../components/Dark/Dark'


const NavBar = () => {
    const { theme, setTheme } = useContext(ThemeContext)

    return (
        <nav className="navbar">
            <div className="navbar-appname">Recipedia</div>
            <ul className="nav-link-container">
                <li>Home</li>
                <li>Create Recipe</li>
                <li>Search</li>
            </ul>
            <div className="navbar-profile-signout">
                <strong>
                    E
                </strong>
                <Button text="Sign Out" style="button-light"/>
            </div>
            <div className="toggle-container">
                <Dark className={'theme'} onChange={setTheme}/>
                <label>Dark Mode</label>
            </div>

        </nav>
    )
}

export default NavBar