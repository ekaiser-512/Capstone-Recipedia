import './Button.css';

const Button = ({ text, handleClick, theme }) => {
  const buttonClass = theme === 'dark' ? 'button-dark' : 'button-light';

  return (
    <button onClick={handleClick} className={`button ${buttonClass}`}>
      {text}
    </button>
  );
};

export default Button;