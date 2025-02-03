import MovieCard from "./components/MovieCard";
import categoriess from "./movies.json";
import MoviePlayer from "./components/MoviePlayer";

// render row of x movies: movie[movieInx] until movie[movieInx + x - 1]
function MoviesRow({ movies, movieInx, x }) {
    return (
        <div className={`carousel-item${movieInx === 0 ? " active" : ""}`}>
            <div className="row row-cols-1 row-cols-sm-2 row-cols-md-4 g-4">
                {Array.from({ length: x }, (_, i) => (
                    <div className="col" key={i}>
                        <MovieCard {...movies[movieInx + i]} />
                    </div>
                ))}
            </div>
        </div>
    );
}
// render carousel of movies from the category category 
function CategoryCarousel({token, category, movies, id}) {
    let movieInx = 0;
    // check if list is empty
    if (movies.length === 0) {
        return (null);
    }
    // movies list of _id need to convert it to list of movies object by calling api/movie/_id ex3




    // appending <moviesRow/> to elements itiratively
    const elements = [];
    while (movieInx < movies.length) {
        elements.push(
            <MoviesRow movies={movies} x={Math.min(4, movies.length - movieInx)} movieInx={movieInx} />
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
                <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                <span className="visually-hidden">Previous</span>
            </button>
            <button className="carousel-control-next" type="button" data-bs-target={`#carouselExample${id}`} data-bs-slide="next">
                <span className="carousel-control-next-icon" aria-hidden="true"></span>
                <span className="visually-hidden">Next</span>
            </button>
        </div>
        </div>
    );
}

// render the carusels of all the categories that return from the call to ex3
function Carousels({token}) {
    // get categoriees json by api/movies
    let categories = categoriess



    let id=0
    return (
        <div>
            {Object.keys(categories).map((category) => <CategoryCarousel token={token} category={category} movies={categories[category]} id={id++}/>)}
        </div>
    )
}

// render tye main page for the logged users
function MainLoggedPage({token}) {
    return (<div>
        <MoviePlayer video={"1.mp4"} isHomePage={true} />
        <Carousels token={token}/>
    </div>);
}

export default MainLoggedPage;
