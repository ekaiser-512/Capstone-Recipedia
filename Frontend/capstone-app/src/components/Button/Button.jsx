import './Button.css'

const Button = ({text, handleClick, style}) => {

    return (
        <button onClick={handleClick} className={`button ${style}`}>{text}</button>
    )
}

export default Button;