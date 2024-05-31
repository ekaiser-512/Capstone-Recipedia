import{ useState, useContext, useEffect } from 'react';
import Category from '../../components/Category/Category';
import './Home.css'
import Button from '../../components/Button/Button';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../../contexts/AuthContext';
import { getData } from '../../api/api';
import { postData } from '../../api/api';
import DisplayCategories from '../../components/DisplayCategories/DisplayCategories';
import Input from '../../components/Input/Input';


const Home = () => {
    const navigate = useNavigate();
    const {currentUser, setCurrentUser} = useContext(AuthContext)
    const [errorMessage, setErrorMessage] = useState(null)
    const [categories, setCategories] = useState([]);
    const [bookFormData, setBookFormData] = useState({
        title:""
    })
    

    useEffect(() => {
        checkLogin(currentUser, navigate)
        if (currentUser && currentUser.book) {
            getCategories(currentUser.book.id)
        } 
    }, []);

    const checkLogin = (currentUser, navigate) => {
        if(!currentUser) {
            navigate("/") 
        }
    }

    const getCategories = async (bookId) => {
        const response = await getData(`books/${bookId}/categories`)
        console.log("getAllCategoriesInBook: response", response);
        if(response.hasError) {
            setErrorMessage(response.message)
        } else {
            setCategories(response.data)
            console.log("response.data", response.data)
        }

    }

    // meant to be used by DisplayCategories component
    const createCategory = async (categoryFormData) => {
        const response = await postData(`books/${currentUser.book.id}/categories`, categoryFormData.title)
        console.log("addCategoryToBook: response", response);
        if(response.hasError) {
            setErrorMessage(response.message)
        } else {
            currentUser.book.categories = response.data
            setCategories([...categories, response.data]);
            navigate("/home")
        }
    }

    const createBook = async (userId) => {
        const response = await postData(`users/${userId}/books`, bookFormData.title)
        console.log("addBookToUser: response", response);
        if(response.hasError) {
            setErrorMessage(response.message)
        } else {
            currentUser.book = response.data
            setCurrentUser(currentUser)
        }

    }

    const onCreateBookButton = (event) => {
        createBook(currentUser.userId)
        navigate("/")
    }

    const handleBookForm = (event) => {
        const{ name, value } = event.target;
        setBookFormData((prevFormData)=> ({
            ...prevFormData, 
            [name]: value //
        }))
    }

    return (
        <>
        {
            errorMessage && <h4 className="error">{errorMessage}</h4>
        }
        {currentUser ?  (
                
            currentUser.book == null ? (
                <>
                    <Input label="Book Title: " type="text" name="title" required onChange={handleBookForm} />
                    <Button handleClick={onCreateBookButton} text = "Create a Book" style="button-light" />
                </>
            ) : (
                <div>
                    <h1>{currentUser.book.title}</h1>
                    <DisplayCategories categories={categories} createCategory={createCategory}/>
                </div>
                
            )
        ) : null }
        </>
    )


}
export default Home;