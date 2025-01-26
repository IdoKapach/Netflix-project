import { Router } from 'express'
import { getToken } from "../controllers/Token.js"

const tokenRouter = Router()

tokenRouter.route('/tokens').post(getToken)

export { tokenRouter }