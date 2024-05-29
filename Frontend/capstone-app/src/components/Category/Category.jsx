import CategoryCard from "../CategoryCard/CategoryCard";
import Button from "../Button/Button";

const Category = () => {

    const categoryCardData = [

    ]

    return (
        <section classname = "categories" id = "categories">
            <h2>Recipe Categories</h2>
            <Button style="button-light">Add Category to your book</Button>
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