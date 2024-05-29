
import Button from "../Button/Button";
import Input from "../Input/Input";

const Profile = ({ profileFormData, setProfileFormData, handleUpdateProfile }) => {
    const handleChange = (event) => {
        const { name, value } = event.target;
        setProfileFormData((prevFormData) => ({
            ...prevFormData,
            [name]: value,
        }));
    };

    const onUpdateButtonClick = (event) => {
        event.preventDefault();
        handleUpdateProfile();
    };

    return (
        <div className="profile-container">
            <h2>Update Profile</h2>
            <form className="common-form">
                <Input label="First Name: " type="text" name="firstName" value={profileFormData.firstName} onChange={handleChange} />
                <Input label="Last Name: " type="text" name="lastName" value={profileFormData.lastName} onChange={handleChange} />
                <Input label="Date of Birth: " type="text" name="dateOfBirth" value={profileFormData.dateOfBirth} onChange={handleChange} />
                <Input label="Email: " type="text" name="email" value={profileFormData.email} onChange={handleChange} />
                <Input label="Password: " type="password" name="password" value={profileFormData.password} onChange={handleChange} />
                <Button handleClick={onUpdateButtonClick} text="Update" style="button-dark" />
            </form>
        </div>
    );
};

export default Profile;

