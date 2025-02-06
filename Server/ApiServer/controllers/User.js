import { getUserService, createUserService } from '../services/User.js';

const createUser = async (req, res) => {
    try {
        const username = req.body.username
        const password = req.body.password
        let picture = req.body.picture

        console.log('username: got here', username)

        if (picture === undefined) {
            console.log('picture: was undefined (before)', picture)
            picture = "media/default.png"
            console.log('picture: was undefined', picture)
        }

        // call the creation service
        const user = await createUserService(username, password, picture)

        console.log('user: got here', user)
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
        return res.status(200).json({
            username: user.username,
            picture: user.picture
        }
        )
    } catch (e) {
        return res.status(500).json({error: e.message})
    }
}


export {createUser, getUser}