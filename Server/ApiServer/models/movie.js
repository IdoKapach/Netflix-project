import mongoose from 'mongoose';
const Schema = mongoose.Schema;
const Movie = new Schema({
    _id : {
        type: String,
        required: true
    },
    name: {
        type: String,
        required: true,
        unique: true
    },
    video: {
        type: String,
        required: true
    },
    image: {
        type: String,
        required: true
    },
    categories: [{
        type: String,
        required: true
    }]
});

export default mongoose.model('Movie', Movie);