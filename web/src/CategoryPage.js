import { useParams } from "react-router-dom";
import moviess from "./movies.json"
import MovieGrid from "./components/MovieGrid";

// render category page
function CategoryPage({token}) {
    const {categoryId} = useParams()
    // getting movies _id list and category's name by calling to api/categories/:categoryId

    // returning 404 in case category wasn't found

    // getting itiratively the movies objects from theirs _id by calling api/movies/:_id



    let movies = moviess["Drama"]
    let categoryName = "Drama"
    return (
    <div style={{margin: "5%"}}>
        <h1 style={{textAlign: "center"}}>{categoryName}</h1>
        <MovieGrid movies={movies} />
    </div>
)
}

export default CategoryPage

