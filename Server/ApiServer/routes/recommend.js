import { Router } from 'express'
import { getRecommend, postRecommend } from "../controllers/recommend.js"
import { authantication } from "../controllers/authantication.js"

const recommendRouter = Router()

recommendRouter.route('/movies/:id/recommend').get(authantication(), getRecommend)
recommendRouter.route('/movies/:id/recommend').post(authantication(), postRecommend)

export { recommendRouter }