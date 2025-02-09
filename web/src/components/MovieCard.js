function MovieCard({_id, name, categories, image, video, token}) {
    // console.log("card:", _id, name, categories, image, video)
    // console.log("path:", `http://localhost:3000/${image}`)
    return (
        <div class="card" style={{"width": "18rem"}}>
        <img src={`http://localhost:3000/${image}?token=${token}`} class="card-img-top" alt={`the image of ${name}`} />
        <div class="card-body">
            <h5 class="card-title">{name}</h5>
            <p class="card-text">{categories.map(category => ` ${category} `)}</p>
            <a href={`/movie-info/${_id}`} class="btn btn-primary">Movie's info</a>
        </div>
        </div>
    )
}

export default MovieCard