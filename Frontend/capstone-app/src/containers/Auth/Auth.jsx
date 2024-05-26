import { useState } from "react";
import Login from "../../components/Auth/Login";
import Signup from "../../components/Auth/Signup";
import Button from "../../components/Button/Button";
import './Auth.css'

import {postData} from "../../api/api"

const App = () => {
const [isLogin, setIsLogin] = useState(true)
const [isSetup, setIsSetup] = useState(true)
const [loginFormData, setLoginFormData] = useState({
    email: "",
    password: "", 

})

const [signupFormData, setSignupFormData] = useState({
    firstName: "",
    lastName: "",
    dateOfBirth: "",
    email: "",
    password: "" 
})

const handleLogin = async () => {
    const response = await postData("users/login", loginFormData)
    console.log(response);
}

const handleSignup = async () => {
    const response = await postData("users/signup", signupFormData)
    console.log(response);
}

const toggleAuthMode = () => {
    setIsLogin(!isLogin)
}
    return (
        <section className="auth-container">
        {isLogin ? (
        <Login 
            loginFormData={loginFormData} 
            setLoginFormData={setLoginFormData} 
            handleLogin={handleLogin}
        /> 
        ) : (
        <Signup 
            signupFormData={signupFormData}
            setSignupFormData={setSignupFormData}
            handleSignup={handleSignup}
        />
        )    
        }
        <Button handleClick={toggleAuthMode} 
        text={isLogin ? "Go to Sign up" : "Go to Login"} 
        style="button-dark" />
        </section>
    )
}
export default App;