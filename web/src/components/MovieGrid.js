import MovieCard from "./MovieCard"

// render grid of movies
function MovieGrid({movies}) {
    return (
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-4 g-4" style={{margin:"20px"}}>
        {
        movies.map((movie) => <div class="col"><MovieCard {...movie}/></div>)
    }
    </div>
    )
}

export default MovieGrid