import{ useState, useContext, useEffect } from 'react';
import Hero from '../../components/Hero/Hero';
import Category from '../../components/Category/Category';
import './Home.css'
import Button from '../../components/Button/Button';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../../contexts/AuthContext';
import { getData } from '../../api/api';


const Home = () => {
    const navigate = useNavigate();
    const {currentUserFirstName, setCurrentUserFirstName, currentBook, setCurrentBook} = useContext(AuthContext)
    const [categories, setCategories] = useState(true);
    const [createBook, setCreateBook] = useState(true);
    const [createCategory, setCreateCategory] = useState(true);
    const [bookFormData, setBookFormData] = useState({
        title:""
    })
    const [categoryFormData, setCategoryFormData] = useState({
        title:""
    })

    useEffect(() => {
        checkLogin(currentUserFirstName, navigate)
        getBook(currentBook)

    }, []);

    const checkLogin = (currentUserFirstName, navigate) => {
        if(!currentUserFirstName || currentUserFirstName == "") {
            navigate("/") 
        }
    }

    const onCreateBookButton = (event) => {

    }

    const onCreateCategoryButton = (event) => {
        
    }

    const getBook = async (currentBook) => {
        const response = await getData("books/" + currentBook, bookFormData)
        console.log("get book", response);
        if(response.hasError) {
            setErrorMessage(response.message)
        } else {
            setCurrentBook(response.data)
        }
    }

    const toggleBookMode = (event) => {
        setCreateBook(!createBook)
        setErrorMessage(null)
    }

    const toggleCategoryMode = (event) => {
        setCategories(!categories)
        setErrorMessage(null)
    }


    return (
        <div className='Home'>
        <h2> Welcome to Recipedia</h2>
        <Hero>Book Title
            {/* {currentBook === null && ( */}
            <Button handleClick={onCreateBookButton} text = "Create a Book" style="button-light" />
            </Hero>
        <section className="category-container">
            <div>
                <Button handleClick={onCreateCategoryButton} text="Create a Category" style="button-dark" />
                <Category>Category Cards Go Here</Category>
                
            </div>
        </section>


        </div>
    )
}
export default Home;