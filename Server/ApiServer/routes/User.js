import { Router } from 'express'
import { createUser, getUser } from '../controllers/User.js'
import { authantication } from '../controllers/authantication.js'
import  userUpload  from '../controllers/userUpload.js'

const userRouter = Router()

userRouter.route('/users').post(userUpload, createUser)

userRouter.route('/users/:id').get(authantication(), getUser)

export {userRouter}