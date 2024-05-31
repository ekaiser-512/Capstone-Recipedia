import{ useState } from 'react';
import Button from "../Button/Button";
import Input from '../../components/Input/Input';
import CategoryCard from '../../components/CategoryCard/CategoryCard';

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
        <>
        <Input label="Title: " type="text" name="title" required onChange={handleCategoryForm}/>
        <Button handleClick={onCreateCategory} text="Create category" style="button-light"/>
        { categoryArray.map((category, index) => {
                return (
                        <CategoryCard 
                            key={index}
                            categoryTitle={category.title}
                            categoryId={category.id}
                        />
                    )
                })
        }
        </>
    )
}

export default DisplayCategories;