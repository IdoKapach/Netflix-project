import { model, Schema } from 'mongoose'

const UserSchema = new Schema ({
    _id : {
        type: String,
        required: true
    },
    username : {
        type: String,
        required: true,
        unique: true
    },
    password : {
        type: String,
        required: true,
    },
    picture : {
        type: String, // URL or path to the image
    },
    movies : [{
        id: {
            type: String,
            required: true
        },
        watchDate: {
            type: Date,
            default: Date.now()
        }
    }]
})

const User = model('User', UserSchema)

export {User}