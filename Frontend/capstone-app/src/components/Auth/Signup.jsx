import Button from "../Button/Button";
import Input from "../Input/Input";
import "./Auth.css"

const Signup = () => {

    return (
        <div className="login-signup-container">
            <h2>Sign Up</h2>
            <form className="common-form">
                <Input label="First Name: " type="text" name="firstname" />
                <Input label="Last Name: " type="text" name="lastname" />
                <Input label="Date of Birth: " type="text" name="dob" />
                <Input label="Email: " type="text" name="email" />
                <Input label="Password: " type="password" name="password" />
                <Button text="SignUp" style="button-dark"/>
            </form>
        </div>
    )
}

export default Signup;