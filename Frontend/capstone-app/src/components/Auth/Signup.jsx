import Button from "../Button/Button";
import Input from "../Input/Input";


const Signup = ({ signupFormData, setSignupFormData, handleSignup }) => {

    const handleChange = (event) => {
        const{ name, value } = event.target;
        setSignupFormData((prevFormData)=> ({
            ...prevFormData, 
            [name]: value,
        }))
    }

    const onSignupButtonClick = (event) => {
        event.preventDefault()  
        console.log(signupFormData)
        handleSignup()
    }

    return (
        <div className="login-signup-container">
            <h2>Sign Up</h2>
            <form className="common-form">
                <Input label="First Name: " type="text" name="firstName" onChange={handleChange}/>
                <Input label="Last Name: " type="text" name="lastName" onChange={handleChange}/>
                <Input label="Date of Birth: " type="text" name="dateOfBirth" onChange={handleChange}/>
                <Input label="Email: " type="text" name="email" onChange={handleChange}/>
                <Input label="Password: " type="password" name="password" onChange={handleChange}/>
                <Button handleClick={onSignupButtonClick} text="SignUp" style="button-dark"/>
            </form>
        </div>
    )
}

export default Signup;