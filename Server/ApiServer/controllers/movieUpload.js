import multer from 'multer';
import fs from 'fs'
import path from 'path'

const rootFolder = path.join(import.meta.dirname, '../media/movies');

// set the file location
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        // save videos under 'videos' and images under 'images'
        let destFolder;
        // check subir and path for video files
        if (file.fieldname === 'videoFile') {
            // define the relative path to pass to the clients (to access static files)
            const relativeFolder = 'media/movies/videos'
            // set destination folder to the videos subdir
            destFolder = path.join(rootFolder, 'videos')
            // add the relative folder to the req for the controller (to add to mongo)
            req.videoPath = path.join(relativeFolder, file.originalname);
            console.log("req.videopath: ", req.videoPath)
        // check subir and path for image files
        } else if (file.fieldname === 'imageFile'){
            const relativeFolder = 'media/movies/images'
            req.imagePath = path.join(relativeFolder, file.originalname)
            destFolder = path.join(rootFolder, 'images')
        } else {
            // another type of file shouldnt be here
            cb(new Error('invalid file field'), null);
        }
        
        // create file if it doesnt exist
        if (!fs.existsSync(destFolder)) {
            fs.mkdirSync(destFolder, { recursive: true });        
        }
        // continue to the next middleware (the actual file upload) and give it the correct subdir
        cb(null, destFolder)
    },
    filename: (req, file, cb) => {
        cb(null, file.originalname)
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