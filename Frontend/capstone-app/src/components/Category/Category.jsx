import{ useState, useContext, useEffect } from 'react';
import { useParams, useNavigate } from "react-router-dom";
import { AuthContext } from '../../contexts/AuthContext';
import { getData } from '../../api/api';
import CategoryCard from "../CategoryCard/CategoryCard";
import Button from "../Button/Button";
import RecipeCard from '../RecipeCard/RecipeCard';

import "./Category.css";

// TODO show a list of recipes in a category
const Category = () => {
    const navigate = useNavigate();
    const { currentUser, setCurrentUser } = useContext(AuthContext)
    const { categoryId } = useParams();
    const [categoryTitle, setCategoryTitle] = useState("Category")
    const [recipes, setRecipes] = useState([])

    // TODO get category information using categoryId
    useEffect(() => {
        checkLogin(currentUser, navigate)
        if (currentUser && categoryId != null) {
            getCategoryById(categoryId)
        }
    }, []);

    const getCategoryById = async (categoryId) => {
        const response = await getData(`categories/${categoryId}`)
        console.log("category response: ", response)
        if(response.hasError) {
            setErrorMessage(response.message)
        } else {
            //setCategories(response.data)
            setCategoryTitle(response.title)
            setRecipes(response.recipes)
        }
    }

    const checkLogin = (currentUser, navigate) => {
        if(!currentUser) {
            navigate("/") 
        }
    }

    return (
        <section className = "categories" id = "categories">
            <h2>{categoryTitle}</h2>
            <div className = "category-grid">
                {recipes.map((recipe, index) => {
                    return (
                        <RecipeCard 
                            recipeName={recipe.name}
                            recipeAuthor={recipe.recipeAuthor}
                        />
                    )
                })}
            </div>
        </section>
    );
}
export default Category;