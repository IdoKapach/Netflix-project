function MoviePlayer({video, isHomePage = false, token}) {
    console.log("VIDEO:", video)
    if (!video) {
        return null
    }
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
            <source src={`http://localhost:3000/${video}?token=${token}`} type="video/mp4" />
        </video>
        </div>
    )
}

export default MoviePlayer