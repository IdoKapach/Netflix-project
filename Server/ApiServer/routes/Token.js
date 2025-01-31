import { Router } from 'express'
import { getToken } from "../controllers/Token.js"

const tokenRouter = Router()

// no authantication needed as this is the first call to get the token
tokenRouter.route('/tokens').post(getToken)

export { tokenRouter }