function MoviePlayer({video, isHomePage = false}) {
    if (isHomePage) {
        var width="640"
        var height="300"
    }
    else {
        var width="1280"
        var height="600"
    }
    return (
        <div id="video-div">
        <video width={width} height={height} controls autoPlay={isHomePage} muted={isHomePage}>
            <source src={`http://localhost:8777/media/${video}`} type="video/mp4" />
        </video>
        </div>
    )
}

export default MoviePlayer