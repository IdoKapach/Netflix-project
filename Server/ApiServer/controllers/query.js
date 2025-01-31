import queryServices from "../services/query.js";

// return all movies that query is a sub-string of their name field or of one of categories elemnts, requires autentication
const getMovieByQuery = async (req, res) => {
    try{
        return res.status(200).json(await queryServices.getMovieByQuery(req.params.query))
    }
    catch(e) {
        return res.status(404).json({"error": e})
    }
}

export {getMovieByQuery}