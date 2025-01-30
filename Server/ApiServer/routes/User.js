import { Router } from 'express'
import { createUser, getUser } from '../controllers/User.js'

const userRouter = Router()

userRouter.route('/users').post(createUser)

userRouter.route('/users/:id').get(getUser)

export {userRouter}