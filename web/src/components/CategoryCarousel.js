import MovieCard from "./MovieCard";
import { getMovie } from "../fetchRequests";
import {useState, useEffect} from 'react'


// render row of x movies: movie[movieInx] until movie[movieInx + x - 1]
function MoviesRow({token, movies, movieInx, x }) {
    // console.log("ARGS movie row:", movieInx, movies, x)
    if (movies.length === 0) {
        return null
    }
    return (
        <div className={`carousel-item${movieInx === 0 ? " active" : ""}`}>
            <div className="row row-cols-1 row-cols-sm-2 row-cols-md-4 g-4">
                {Array.from({ length: x }, (_, i) => (
                    <div className="col" key={i}>
                        <MovieCard {...movies[movieInx + i]} token={token} />
                    </div>
                ))}
            </div>
        </div>
    );
}
// render carousel of movies from the category category 
function CategoryCarousel({token, category, movies, id}) {
    // console.log("ARGS category carusele:", category, movies, id)
    // movies list of _id need to convert it to list of movies object by calling api/movie/_id ex3
    const [moviesObj, setMovies] = useState([])
    useEffect(() => {
        
        const fetchCategories = async () => {
            // Correctly fetch all movies
            let movieArray = await Promise.all(
                movies.map(async (movie) => await getMovie(token, movie))
            );
            setMovies(movieArray)
        };

        fetchCategories();
    }, [token]);

    let movieInx = 0;
    // check if list is empty
    if (movies.length === 0) {
        return (null);
    }


    // appending <moviesRow/> to elements itiratively
    const elements = [];
    while (movieInx < movies.length) {
        elements.push(
            <MoviesRow movies={moviesObj} x={Math.min(4, movies.length - movieInx)} movieInx={movieInx} token={token}/>
        )
        movieInx += 4; 
    }

    return (
        <div>
        <h2 class="h2 mb-2 fw-normal my-h2">{category}</h2>
        <div id={`carouselExample${id}`} className="carousel slide" style={{"width":"92%", "margin-left":"3.5%"}}>
            <div className="carousel-inner" style={{ "padding-left": "7%", "padding-right": "7%" }}>
                {elements}
            </div>
            <button className="carousel-control-prev" type="button" data-bs-target={`#carouselExample${id}`} data-bs-slide="prev">
                {/* <span className="carousel-control-prev-icon" aria-hidden="true"></span> */}
                <img src="data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16' fill='currentColor'%3e
  %3cpath d='M11.707 1.5a1 1 0 0 0-1.414 0L3.293 8l7 7a1 1 0 0 0 1.414-1.414L5.414 8l6.293-6.293a1 1 0 0 0 0-1.414z'/%3e
%3c/svg%3e" class="arrow-icon text-secondary" width="25" height="25" />
                <span className="visually-hidden">Previous</span>
            </button>
            <button className="carousel-control-next" type="button" data-bs-target={`#carouselExample${id}`} data-bs-slide="next">
                {/* <span className="carousel-control-next-icon" aria-hidden="true"></span> */}
                <img src="data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16' fill='currentColor'%3e
  %3cpath d='M4.293 1.5a1 1 0 0 1 1.414 0L12.707 8l-7 7a1 1 0 0 1-1.414-1.414L10.586 8 4.293 2.707a1 1 0 0 1 0-1.414z'/%3e
%3c/svg%3e" class="arrow-icon text-secondary" width="25" height="25" />
                <span className="visually-hidden">Next</span>
            </button>
        </div>
        </div>
    );
}

export default CategoryCarousel