import "./NavBar.css"
import Button from "../Button/Button"

const NavBar = () => {

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

        </nav>
    )
}

export default NavBar