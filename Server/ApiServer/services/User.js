import {User} from "../models/User.js"
import {IdCounter} from "../models/IdCounter.js"

const getNextId = async () => {
    try {
        const nextId = await IdCounter.findById("nextId")

        // if the counter doesnt exist, create it and return 0 (first _id)
        if (!nextId) {
            const newCounter = new IdCounter({ _id: 'nextId', seq: "0"})
            await newCounter.save()
            return "0"
        }
        // convert id string (stored in mongo) to BigInt (cant be stored in mongo) + increment
        const nextSeq = BigInt(nextId.seq) + BigInt(1)
        // convert id BigInt back to string
        const nextSeqStr = nextSeq.toString()
    
        // update nextId in mongo DB
        await IdCounter.findByIdAndUpdate(
            'nextId',
            { seq: nextSeqStr},
            { new: true }
        )
    
        return nextSeqStr
    } catch (e) {
        console.error("Error in getNextId: ", e)
        throw e
    }
}

const createUserService = async (username, password, picture) => {
    try {
        const nextId = await getNextId() // generate the next ID
        console.log('nextId: ', nextId)
        // setting that only the first registered user will be admin
        let admin = nextId === "0" ? true : false
        const user = new User({
            _id: nextId,
            username: username,
            password: password,
            admin: admin
        });
        console.log('user: ', user)
        // set the picture if one was provided
        if (picture) user.picture = picture
        // save the user to mongoDB
        return await user.save()
    } catch (e) {
        console.error("Error in createUserService: ", e)
        throw e
    }    
}

const getUserService = async (id) => {
    try {
        return await User.findOne({ _id: id })
    } catch (e) {
        console.error("Error in getUserService: ", e)
        throw e
    }
}

const updateUserService = async (id, data) => {
    try {
        const options = { new: true, runValidators: true}
        return await User.findByIdAndUpdate(id, data, options)
    } catch (e) {
        console.error("Error in updateUserService: ", e.message)
        console.error('Stack trace: ', e.stack)
        throw e
    }
}

export {createUserService, getUserService, updateUserService}