import queryServices from "../services/query.js";
import {authantication} from "../services/authantication.js"

// return all movies that query is a sub-string of their name field or of one of categories elemnts, requires autentication
const getMovieByQuery = async (req, res) => {
    // checks the authantication
    try {
        await authantication(req)
    }
    // if the authantication failed, throws exeption
    catch(e) {
        return res.status(400).json({"error": e})
    }
    try{
        return res.status(200).json(await queryServices.getMovieByQuery(req.params.query))
    }
    catch(e) {
        return res.status(404).json({"error": e})
    }
}

export {getMovieByQuery}