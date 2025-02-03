import multer from 'multer';
import fs from 'fs'
import path from 'path'

// set the file location
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        console.log('fieldname: ', file.fieldname);
        console.log('originalname: ', file.originalname);
        // save videos under 'videos' and images under 'images'
        let rootFolder = '../media/movies/'
        let folder;
        if (file.fieldname === 'videoFile') {
            folder = rootFolder + 'videos'
            // add the folder to the req for the controller (to add to mongo)
        } else if (file.fieldname === 'imageFile'){
            folder = rootFolder + 'images'
        }
        // create the folder if it doesn't exist
        if (!fs.existsSync(folder)) {
            fs.mkdirSync(folder, { recursive: true });
        }
        cb(null, folder);
    },
    filename: (req, file, cb) => {
        // add the file name to the req for the controller (to add to mongo)
        const fullPath = path.join(req.uploadFolder, file.originalname)
        // create the paths list if needed
        if (!req.filePaths) {
            req.filePaths = [];
        }
        // add the current file path (can be either image or video)
        req.filePaths.push(fullPath);
        cb(null, file.originalname);
    }
})

const upload = multer({ storage });

const fileUpload = (req, res, next) => {
    console.log('fileUpload middleware started');


    upload.fields([
        { name: 'videoFile', maxCount: 1 },
        { name: 'imageFile', maxCount: 1 }
    ])(req, res, (err) => {
        if (err) {
            console.error('Multer encountered an error:', err);
            return res.status(400).json({ error: 'Multer error', details: err.message });
        }
        console.log('fileUpload middleware finished');
        next();
    });
};

export default fileUpload;