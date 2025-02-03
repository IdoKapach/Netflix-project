// import MovieCard from "./components/MovieCard";
import MoviePlayer from "./components/MoviePlayer";
import CategoryCarousel from "./components/CategoryCarousel";
import categoriess from "./movies.json";

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
