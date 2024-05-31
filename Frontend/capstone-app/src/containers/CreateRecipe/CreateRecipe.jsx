import{ useState, useContext, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../contexts/AuthContext";
import { getData } from '../../api/api';
import { postData } from '../../api/api';
import Input from '../../components/Input/Input';
import Button from '../../components/Button/Button';

const CreateRecipe = ({submitRecipe, }) => {

    const navigate = useNavigate();
    const {currentUser, setCurrentUser} = useContext(AuthContext)
    const [isIngredient, setIngredient] = useState(true);
    const [currentIngredient, setCurrentIngredient] = useState('')
    const [ingredients, setIngredients] = useState([])
    const [categories, setCategories] = useState([]);
    const [recipeFormData, setRecipeFormData] = useState({
        name: "",
        category: "",
        recipeAuthor: "",
        recipeDescription: "",
    })
    const [ingredientFormData, setIngredientFormData] = useState({
        name: "",
        ingredientFoodGroup: "",
        commonAllergen: "",
        dietaryRestriction: ""
    })

    useEffect(() => {
        checkLogin(currentUser, navigate)
        if (currentUser) {
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

    const createRecipe = async (recipe) => {
        const response = await postData(`recipes`, recipe)
        console.log("post recipes: ", response);
        if(response.hasError) {
            setErrorMessage(response.message)
        } else {
            addRecipeToCategory(response.recipeId, recipeFormData.category)
        }
    }

    const addRecipeToCategory = async (recipeId, categoryId) => {
        const response = await postData(`categories/${categoryId}/recipes/${recipeId}`)
        console.log("post recipes: ", response);
        if(response.hasError) {
            setErrorMessage(response.message)
        } else {
            navigate(`/categories/${categoryId}`)
        }
    }

    // const addIngredient = () => {
        
    // }

    const onAddIngredientButtonClick = (event) => {
        setIngredients(prevIngredients => [...prevIngredients, currentIngredient])
        setCurrentIngredient('')
        console.log(ingredients)
    }

    const onSubmitButtonClick = (event) => {
        createRecipe(recipeFormData)
    }

    const handleIngredientChange = (event) => {
        const { name, value } = event.target;
        setCurrentIngredient(value)
    }

    const handleChange = (event) => {
        const{ name, value } = event.target;
        setRecipeFormData((prevFormData)=> ({
            ...prevFormData, 
            [name]: value //
        }))
    }

    return (
        <>
        <h2>Create a New Recipe</h2>
        <div className="recipe-form">
            <Input label="Name: " type="text" name="name" required onChange={handleChange} />
            <Input label="Author: " type="text" name="recipeAuthor" required onChange={handleChange} />
            <label htmlFor="category">Category: </label>
                <select
                id="category"
                name="category"
                value={recipeFormData.category}
                onChange={handleChange}
                required
                >
                <option value="">Select a category</option>
                {categories.map((category) => (
                    <option key={category.id} value={category.id}>
                    {category.title}
                    </option>
                ))}
                </select>
            <div>
                <h4>Ingredients</h4>
                <ul>
                    {ingredients.map((ingredient, index) => {
                        <li key={index}>{ingredient}</li>
                    })}
                </ul>
                <h3>{currentIngredient}</h3>
                <Input label="Ingredient: " type="text" name="ingredient" value={currentIngredient} required onChange={handleIngredientChange}/>
                <Button handleClick={onAddIngredientButtonClick} text="Add Ingredient" style="button-light" />
            </div>
            <Input label="Directions: " type="text" name="recipeDescription" required onChange={handleChange} />
        </div>



        <Button handleClick={onSubmitButtonClick} text = "Submit your Recipe" style="button-light" />
        </>
    )
}

export default CreateRecipe;