import "./Footer.css";

const Footer = () => {
    const listObjects = [
        { href: "https://www.linkedin.com/in/emily-kaiser512/", name: "LinkedIn", desc: "LinkedIn link" }
    ];

    return (
        <footer id="contact">
            <div className="footer-content">
                <div className="footer-section contact-socials">
                    <div className="contact-info">
                        <h4>Contact</h4>
                        <p>Email: ekaiser@indeed.com</p>
                    </div>
                    <div className="social-links">
                        {listObjects.map((item, index) => (
                            <a key={index} href={item.href} aria-label={item.desc}>{item.name}</a>
                        ))}
                    </div>
                </div>
            </div>
            <div className="footer-bottom">
                <p>&copy; 2024 Recipedia. All rights reserved unless you want to catch these hands</p>
            </div>
        </footer>
    );
};

export default Footer;