import { getUserService, createUserService } from '../services/User.js';

const createUser = async (req, res) => {
    try {
        const username = req.body.username
        const password = req.body.password
        const picture = req.body.picture

        // call the creation service
        const user = await createUserService(username, password, picture)
        // return the user with success status
        return res.status(201).json(user)
    } catch (e) {
        return res.status(500).json({ error : e.message })
    }
}

const getUser = async (req, res) => {
    try {
        const id = req.params.id
        const user = await getUserService(id)
        if (!user) {
            throw new Error('User not found')
        }
        return res.status(200).json(user)
    } catch (e) {
        return res.status(500).json({error: e.message})
    }
}


export {createUser, getUser}