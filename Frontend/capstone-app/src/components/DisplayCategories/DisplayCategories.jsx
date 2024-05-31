import{ useState } from 'react';
import Button from "../Button/Button";
import Input from '../../components/Input/Input';
import CategoryCard from '../../components/CategoryCard/CategoryCard';
import "./DisplayCategories.css";

// TODO: show a list of categories as cards
const DisplayCategories = ({categories, createCategory}) => {
    const [categoryFormData, setCategoryFormData] = useState({
        title:""
    })
    const categoryArray = categories || []

    const handleCategoryForm = (event) => {
        const{ name, value } = event.target;
        setCategoryFormData((prevFormData)=> ({
            ...prevFormData, 
            [name]: value //
        }))
    }

    const onCreateCategory = (event) => {
        createCategory(categoryFormData)
    }
    
    return (
        <section className="categories-section">
          <h2>Categories</h2>
          <div className="category-form">
            <Input label="Category Title: " type="text" name="title" required onChange={handleCategoryForm} />
            <Button handleClick={onCreateCategory} text="Create Category" style="button-light" />
          </div>
          <div className="category-grid">
            {categoryArray.map((category, index) => (
              <CategoryCard
                key={index}
                categoryTitle={category.title}
                categoryId={category.id}
              />
            ))}
          </div>
        </section>
      );
}

export default DisplayCategories;