import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../contexts/AuthContext";

const CreateRecipe = () => {

    const navigate = useNavigate();
    const {currentUserFirstName, setCurrentUserFirstName, currentBook, setCurrentBook} = useContext(AuthContext)
    const [isIngredient, setIngredient] = useState(true);
    const [recipeFormData, setRecipeFormData] = useState({
        name: "",
        recipeAuthor: "",
        recipeDescription: ""
    })
    const [ingredientFormData, setIngredientFormData] = useState({
        name: "",
        ingredientFoodGroup: "",
        commonAllergen: "",
        dietaryRestriction: ""
    })

    return (
        <>
        <h2>Create a New Recipe</h2>
        <form className="recipe-form">
            <Input label="Name: " type="text" name="name" required onChange={handleChange} />
            <Input label="Author: " type="text" name="author" required onChange={handleChange} />
            <div>
                <h3>how do I put in the add ingredient form here?! inception form?</h3>
                <Button handleClick={onAddIngredientButtonClick} text = "Add Ingredient" style="button-light" />
            </div>
            <Input label="Directions: " type="text" name="directions" required onChange={handleChange} />
        </form>



        <Button handleClick={submitRecipe} text = "Submit your Recipe" style="button-light" />
        </>
    )
}

export default CreateRecipe;