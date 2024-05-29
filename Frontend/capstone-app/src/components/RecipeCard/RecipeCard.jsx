import "./RecipeCard.css"

const RecipeCard = ({image, alt, recipeName, recipeAuthor }) => {
  return (
    <div className="recipe-card">
      <img src={image} alt={alt} />
      <h3>{recipeName}</h3>
      <p>{recipeAuthor}</p>
      <Button>Recipe Details</Button>
    </div>
  );
};

export default RecipeCard;
