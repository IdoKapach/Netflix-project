import { Schema, model } from 'mongoose'

const IdCounterSchema = new Schema({
    _id: {
        type: String,
        required: true,
        default: "nextId"
    },
    seq: {
        type: String,
        default: "0",
    },
});

const IdCounter = model('IdCounter', IdCounterSchema);

export { IdCounter };