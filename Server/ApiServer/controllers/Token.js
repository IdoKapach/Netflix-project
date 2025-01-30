import { getTokenService } from "../services/Token.js";

const getToken = async (req, res) => {
    try {
        // parse the creds from request body
        const username = req.body.username
        const password = req.body.password
        // call the token creation service
        const token = await getTokenService(username, password)
        console.log("Controller token: ", token)

        if (token) {
            // if a user is found
            res.status(200).json({ token: token })
        } else {
            // if a user is not found
            res.status(401).json({ error : "Invalid username or password" })
        }
    } catch (e) {
        console.error("Error in getToken: ", e)
        res.status(500).json({ error: "Internal server error" })
    }
}

export {getToken}