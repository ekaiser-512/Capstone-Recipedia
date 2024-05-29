import{ useState } from 'react';
import Hero from '../../components/Hero/Hero';
import Category from '../../components/Category/Category';
import './Home.css'


const Home = () => {
    const [categories, setCategories] = useState();


    return (
        <>
        <h2> Welcome to Recipedia</h2>
        <Hero>Book Title - create book button here?</Hero>
        <Category>Category Cards Go Here</Category>
        </>
    )
}
export default Home;