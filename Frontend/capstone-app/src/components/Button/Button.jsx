import './Button.css';

const Button = ({ text, handleClick, theme }) => {
  const buttonClass = theme === 'dark' ? 'button-dark' : 'button-light';

  return (
    <button onClick={handleClick} className={`button ${buttonClass}`}>
      <li className="nav-item">{text}</li>
    </button>
  );
};

export default Button;