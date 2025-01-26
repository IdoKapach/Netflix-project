import mongoose from 'mongoose';
const Schema = mongoose.Schema;
const Category = new Schema({
    name : {
        type: String,
        required: true,
        unique: true
    },
    promoted: {
        type: Boolean,
        required: true
    },
    movies: [{
        type: String
    }]
});

export default mongoose.model('Category', Category);