import { useContext, useState } from "react";
import Login from "../../components/Auth/Login";
import Signup from "../../components/Auth/Signup";
import Button from "../../components/Button/Button";
import { postData } from "../../api/api"
import { AuthContext } from "../../contexts/AuthContext";
import { useNavigate } from "react-router-dom";
import './Auth.css'

const Auth = () => {

    const navigate = useNavigate();
    const {currentUser, setCurrentUser} = useContext(AuthContext)
    const [isLogin, setIsLogin] = useState(true)
    const [isSetup, setIsSetup] = useState(true)
    const [errorMessage, setErrorMessage] = useState(null)
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
        console.log("login: response", response);
        if(response.hasError) {
            setErrorMessage(response.message)
        } else {
            setCurrentUser(response.data)
            console.log("response.data.book", response.data.book)
            navigate("/home")
        }
    
    }

    const handleSignup = async () => {
        const response = await postData("users/signup", signupFormData)
        console.log(response);
        if(response.hasError) {
            setErrorMessage(response.message)
        } else {
            setCurrentUser(response.data)
            setErrorMessage(null)
            navigate("/home")
        }
    }

    const toggleAuthMode = () => {
        setIsLogin(!isLogin)
        setErrorMessage(null)
    }
    return (
        <div className="Auth">
        <section className="auth-container">
            <div>
                {
                errorMessage && <h4 className="error">{errorMessage}</h4>
                }   
            </div>
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
    </div>
    )
}
export default Auth;