import React from 'react';
import Input from '../Input/Input';
import Button from '../Button/Button';


const Login = ({loginFormData, setLoginFormData, handleLogin}) => {
    
    const handleChange = (event) => {
        const{ name, value } = event.target;
        setLoginFormData((prevFormData)=> ({
            ...prevFormData, 
            [name]: value //
        }))
    }

    const onLoginButtonClick = (event) => {
        event.preventDefault()
        handleLogin()
    }

    return (
        <div className="login-signup-container">
            <h2>Login</h2>
            <form className="common-form">
                <Input label="Email: " type="text" name="email" required onChange={handleChange} />
                <Input label="Password: " type="password" name="password" required onChange={handleChange}/>
                <Button handleClick={onLoginButtonClick} text="Login" style="button-dark"/>
            </form>
        </div>
    );
};

export default Login;