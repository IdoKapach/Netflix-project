import { Router } from 'express'
import { getRecommend, postRecommend } from "../controllers/recommend.js"

const recommendRouter = Router()

recommendRouter.route('/movies/:id/recommend').get(getRecommend)
recommendRouter.route('/movies/:id/recommend').post(postRecommend)

export { recommendRouter }