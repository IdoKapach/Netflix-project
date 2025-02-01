import {User} from "../models/User.js"
import jwt from "jsonwebtoken"

const getTokenService = async (username, password) => {
    try {
        // find a user with the provided username and password (if there is one)
        const user = await User.findOne({ username: username, password: password})

        // if a user with creds is found - returns its id
        if (user) {
            const payload = {
                username: user.username,
                role: user.admin ? "admin" : "user",
            }
            const token = jwt.sign(payload, process.env.JWT_SECRET)
            return token
        }

        // if no user is found - return null
        return null
    } catch (e) {
        console.error("Error in get token service: ", e)
        throw e
    }
}

export { getTokenService }