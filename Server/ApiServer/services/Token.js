import {User} from "../models/User.js"

const getTokenService = async (username, password) => {
    try {
        // find a user with the provided username and password (if there is one)
        const user = await User.findOne({ username: username, password: password})

        // if a user with creds is found - returns its id
        if (user) {
            console.log("got user: ", user)
            return user._id
        }

        // if no user is found - return null
        return null
    } catch (e) {
        console.error("Error in get token service: ", e)
        throw e
    }
}

export { getTokenService }