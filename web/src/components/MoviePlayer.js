import {watchMovie, getMovie} from "../fetchRequests"
import { useParams, useLocation } from "react-router-dom";
import {useState, useEffect} from 'react'
import NoFoundPage from "../NoFoundPage";

// rendr the movie player for the home page
function PlayerHomePage({video, token}) {
    console.log("HOME: video:", video)
    var width="640"
    var height="300"

    if (!video) {
        console.log("error in playing the video")
        return (
            null
        )
    }

    return (
        <div id="video-div">
        <video width={width} height={height} controls autoPlay={true} muted={true}>
            <source src={`http://localhost:3000/${video}?token=${token}`} type="video/mp4" />
        </video>
        </div>
    )
}

// render the movie player for the movie-player screen
function PlayerScreen() {
    const {movieId} = useParams()
    const [videoPath, setVideo] = useState("")

    console.log("DESTROY")

    // extract token from query parameters
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const token = searchParams.get("token")

    useEffect(() => {
        console.log("DESTROY")
        const fetchCategories = async () => {
            // get the movie element that correspond to the movieId
            const data = await getMovie(token, movieId)
            console.log("movie:", data)
            if (data){
                setVideo(data.video)
            }
        };

        fetchCategories();
    }, [token, movieId]);

    if (videoPath === "") {
        return <NoFoundPage />
    }
    else {
        watchMovie(token, movieId)
    }

    var width="1280"
    var height="600"
    
    return (
        <div id="video-div">
        <video width={width} height={height} controls autoPlay={false} muted={false}>
            <source src={`http://localhost:3000/${videoPath}?token=${token}`} type="video/mp4" />
        </video>
        </div>
    )

}

// render the movie player
function MoviePlayer({video = "", isHomePage = false, token}) {
    console.log("GENERAL: video:", video)
    if (isHomePage === true) {
        return <PlayerHomePage video={video} token={token} />
    }
    else {
        return <PlayerScreen />
    }
}

export default MoviePlayer