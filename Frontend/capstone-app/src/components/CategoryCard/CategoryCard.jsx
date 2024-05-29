import "./CategoryCard.css"

const CategoryCard = ({image, alt, categoryTitle }) => {
    return (
      <div className="category-card">
        <img src={image} alt={alt} />
        <h3>{categoryTitle}</h3>
        <Button>See Recipes</Button>
      </div>
    );
  };
  
  export default CategoryCard;