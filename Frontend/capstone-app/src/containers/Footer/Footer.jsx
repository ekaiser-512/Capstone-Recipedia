import "./Footer.css"

const Footer = () => {
    const listObjects = [
        {href: "#", name: "LinkedIn", desc: "linkedin link"},
        {href: "#", name: "Instagram", desc: "Instagram link"},
        {href: "#", name: "GitHub", desc: "GitHub link"}
    ]

    return (
        <footer id = "contact">
            <div className="footer-content">
                <div className="footer-section">
                    <h4>Call me, beep me</h4>
                    <p>ekaiser@indeed.com</p>
                    <p> phone: not your business, friend</p>
                </div>
                <div className="footer-section">
                    <h4>Socials</h4>
                    <ul>
                    </ul>
                </div>
            </div>
            <div className = "footer-bottom">
                <p>&copy; 2024 Recipedia. All rights reserved unless you want to catch these hands</p>
            </div>
        </footer>
    )
}
export default Footer;