import { useContext, useState } from "react";
import Button from "../../components/Button/Button";
import Profile from "../../components/Update/Profile";
import { getData, putData } from "../../api/api";
import { AuthContext } from "../../contexts/AuthContext";
import { useNavigate } from "react-router-dom";
import './Profile.css';

const ProfileContainer = () => {
    const navigate = useNavigate();
    const { currentUserFirstName, setCurrentUserFirstName } = useContext(AuthContext);
    const [errorMessage, setErrorMessage] = useState(null);
    const [profileFormData, setProfileFormData] = useState({
        firstName: "",
        lastName: "",
        dateOfBirth: "",
        email: "",
        password: ""
    });

    const handleUpdateProfile = async () => {
        const response = await putData("users/{id}", profileFormData);
        console.log(response)
        if (response.hasError) {
            setErrorMessage(response.error);
        } else {
            setCurrentUserFirstName(response.data.firstName);
            setErrorMessage(null);
            navigate("/home");
        }
    };

    return (
        <section className="profile-container">
            <div>
                {errorMessage && <h4 className="error">{errorMessage}</h4>}
            </div>
            <Profile
                profileFormData={profileFormData}
                setProfileFormData={setProfileFormData}
                handleUpdateProfile={handleUpdateProfile}
            />
        </section>
    );
};

export default ProfileContainer;