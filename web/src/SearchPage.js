import MovieGrid from "./components/MovieGrid"
import moviess from "./movies.json"
import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import { getQuery } from "./fetchRequests";
// render the search page
function SearchPage({token}) {
    const {query} = useParams()
    
    const [movies, setMovies] = useState([])
    useEffect(() => {
        
        const fetchCategories = async () => {
            // getting query results by calling api/movies/search/:query
            const data = await getQuery(token, query)
            setMovies(data)
        };

        fetchCategories();
    }, [token, query]);

    // in case there aren't results, rendering this page
    if (!movies || movies.length === 0) {
        return (
        <div>
            <h1 class="h1 mb-1 fw-normal my-h1" style={{textAlign: "center", margin: "2% 5%", paddingBottom: "20px"}}>There are no results for "{query}"</h1>
            <img class="img-center" src="/no-results.png" alt="No results"/>
        </div>
        )
    }
    // in case there are results, rendering this page which orginazies the results in a MovieGrid
    return (
    <div style={{margin: "5%"}}>
        <h1 style={{textAlign: "center"}}>Results for "{query}"</h1>
        <MovieGrid movies={movies} token={token} />
    </div>
    )
}

export default SearchPage