import { useParams } from "react-router-dom";
import moviess from "./movies.json"
import MovieGrid from "./components/MovieGrid";
import { useState, useEffect } from "react";
import { getCategory, getMovie } from "./fetchRequests";

// render category page
function CategoryPage({token}) {
    const {categoryId} = useParams()
    
    const [category, setCategory] = useState()
    const [categoryName, setName] = useState("")
    const [movies, setMovies] = useState([])
    useEffect(() => {
        const fetchCategories = async () => {
            // getting movies _id list and category's name by calling to api/categories/:categoryId
            const data = await getCategory(token, categoryId);
            setCategory(data);
            setName(data.name)
            // fetch all movies
            let movieArray = await Promise.all(
                data.movies.map(async (movie) => await getMovie(token, movie))
            );
            setMovies(movieArray)
        };

        fetchCategories();
    }, [token, categoryId]);
    
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
        <MovieGrid movies={movies} token={token} />
    </div>
)
}

export default CategoryPage

