
import { useNavigate } from 'react-router-dom';

import "./CategoryCard.css"

const CategoryCard = ({ image, alt, categoryTitle, categoryId }) => {
  const navigate = useNavigate();
  
  const onClick = (event) => {
    navigate(`/categories/${categoryId}`)
  }

  return (
    <div className="category-card" onClick={onClick}>
      <img src={image} alt={alt} />
      <h3>{categoryTitle}</h3>
    </div>
  );
};
  
export default CategoryCard;