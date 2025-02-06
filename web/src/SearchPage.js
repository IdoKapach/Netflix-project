import MovieGrid from "./components/MovieGrid"
import moviess from "./movies.json"
import { useParams } from "react-router-dom";

// render the search page
function SearchPage({token}) {
    const {query} = useParams()
    // getting query results by calling api/query

    // getting itiratively the movies objects from theirs _id by calling api/movies/:_id



    let movies = moviess["Horror"]
    // in case there aren't results, rendering this page
    if (movies.length === 0) {
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
        <MovieGrid movies={movies} />
    </div>
    )
}

export default SearchPage