import CategoryCard from "../CategoryCard/CategoryCard";
import Button from "../Button/Button";
import "./Category.css";

const Category = () => {

    const categoryCardData = [

    ]

    return (
        <section className = "categories" id = "categories">
            <h2>Recipe Categories</h2>
            <div className = "category-grid">
                {categoryCardData.map((category, index) => {
                    return (
                        <CategoryCard />
                    );
                })}
            </div>
        </section>
    );
}
export default Category;