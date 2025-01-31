function MovieCard({id, name, categories, img, video}) {
    return (
        <div class="card" style={{"width": "18rem"}}>
        <img src={`/data/${img}`} class="card-img-top" alt={`the image of ${name}`} />
        <div class="card-body">
            <h5 class="card-title">{name}</h5>
            <p class="card-text">{categories.map(category => ` ${category} `)}</p>
            <a href={`/movie-info/${id}`} class="btn btn-primary">Movie's info</a>
        </div>
        </div>
    )
}

export default MovieCard