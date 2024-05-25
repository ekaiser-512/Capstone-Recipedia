import React from 'react';
import Input from '../Input/Input';
import Button from '../Button/Button';
import "./Auth.css"

const Login = () => {
    return (
        <div className="login-signup-container">
            <h2>Login</h2>
            <form className="common-form">
                <Input label="Email: " type="text" name="email" id="loginEmail" />
                <Input label="Password: " type="password" name="password" id="loginPassword" />
                <Button text="Login" style="button-dark"/>
            </form>
        </div>
    );
};

export default Login;