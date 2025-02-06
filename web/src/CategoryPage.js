import { useParams } from "react-router-dom";
import moviess from "./movies.json"
import MovieGrid from "./components/MovieGrid";

// render category page
function CategoryPage({token}) {
    const {categoryId} = useParams()
    // getting movies _id list and category's name by calling to api/categories/:categoryId

    // returning 404 in case category wasn't found

    // getting itiratively the movies objects from theirs _id by calling api/movies/:_id



    let movies = moviess["Comedy"]
    let categoryName = "Drama"
    // in case there aren't movies that belongs to this category, rendering this page
    if (movies.length === 0) {
        return (
            <div>
                <h1 class="h1 mb-1 fw-normal my-h1" style={{textAlign: "center", margin: "2% 5%", paddingBottom: "20px"}}>There's no movie belongs to "{categoryName}"</h1>
                <img class="img-center" src="/no-results.png" alt="No results" />
            </div>
            )
    }
    // in case there are movies, rendering this page which orginazies the movies in a MovieGrid
    return (
    <div style={{margin: "5%"}}>
        <h1 style={{textAlign: "center"}}>{categoryName}</h1>
        <MovieGrid movies={movies} />
    </div>
)
}

export default CategoryPage

