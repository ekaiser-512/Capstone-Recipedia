import { useState } from "react";
import Login from "../../components/Auth/Login";
import Signup from "../../components/Auth/Signup";
import Button from "../../components/Button/Button";
import './Auth.css'

const App = () => {
const [isLogin, setIsLogin] = useState(true)

const toggleAuthMode = () => {
    setIsLogin(!isLogin)
}
    return (
        <section className="auth-container">
        {
            isLogin ? <Login /> : <Signup />    
        }
        <Button handleClick={toggleAuthMode} 
        text={isLogin ? "Go to Sign up" : "Go to Login"} 
        style="button-dark" />
        </section>
    )
}
export default App;