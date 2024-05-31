import { useContext, useState } from "react";
import Button from "../../components/Button/Button";
import Profile from "../../components/Update/Profile";
import { getData, putData } from "../../api/api";
import { AuthContext } from "../../contexts/AuthContext";
import { useNavigate } from "react-router-dom";
import './Profile.css';

const ProfileContainer = () => {
    const navigate = useNavigate(); // Hook for navigation
    const { currentUserFirstName, setCurrentUserFirstName } = useContext(AuthContext); // Accessing the AuthContext
    const [errorMessage, setErrorMessage] = useState(null); // State for handling error messages
    const [profileFormData, setProfileFormData] = useState({ // State for managing form data
        firstName: "",
        lastName: "",
        dateOfBirth: "",
        email: "",
        password: ""
    });

    const handleUpdateProfile = async () => {
        const response = await putData("users/{id}", profileFormData); // Making a PUT request to update the user profile
        console.log(response) // Logging the response for debugging purposes
        if (response.hasError) { // Checking if the response has an error
            setErrorMessage(response.error); // Setting the error message state
        } else {
            setCurrentUserFirstName(response.data.firstName); // Updating the currentUserFirstName in the AuthContext
            setErrorMessage(null); // Clearing the error message state
            navigate("/home"); // Navigating to the home page
        }
    };

    return (
        <section className="profile-container">
            <div>
                {errorMessage && <h4 className="error">{errorMessage}</h4>} {/* Rendering the error message if present */}
            </div>
            <Profile
                profileFormData={profileFormData} // Passing the form data as a prop
                setProfileFormData={setProfileFormData} // Passing the function to update the form data as a prop
                handleUpdateProfile={handleUpdateProfile} // Passing the function to handle profile update as a prop
            />
        </section>
    );
};


export default ProfileContainer;