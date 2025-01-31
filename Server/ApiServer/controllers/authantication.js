import jwt from 'jsonwebtoken'

// a higher order function that returns authantication middleware for a specific role
// Given a request, checks if the request has a valid token, and continues to the next middleware if it does
// usage: authantication('admin') - will require the user to have the role 'admin' in order to continue
// usage: authantication() - will require the user to have any role in order to continue
const authantication = (requiredRole) => {
    return async (req, res, next) => {
    // read auth header
    const authHeader = req.header('authorization')
    // check if the header is empty and reads the second part of the header
    // example: Bearer 123456 (Bearer is usually the first part of the header)
    const token = authHeader && authHeader.split(' ')[1]
    // if the token is empty, return 401
    if (token == null) return res.sendStatus(401)

    try {
        // verify the token
        const payload = jwt.verify(token, process.env.JWT_SECRET)
        // verify the role (if required)
        if (requiredRole && payload.role !== requiredRole) {
            return res.sendStatus(403)
        }
        // set the user to the one extracted from the token
        req.user = payload.userId
        // continue to the next controller
        next()
    } catch (e) {
        // if the token is invalid, throw an exception
        console.error("Error in authantication: ", e)
        res.sendStatus(403)
    }
    }
}

export {authantication}