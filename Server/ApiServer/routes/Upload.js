import express from 'express';
import multer from 'multer';
import path from 'path';
import fs from 'fs'
// import movieUpload from '../controllers/movieUpload';
const rootFolder = path.join(import.meta.dirname, '../media/movies');

// set the file location
const storage1 = multer.diskStorage({
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

const upload1 = multer({ storage: storage1 });

const movieUpload = (req, res, next) => {
    console.log('fileUpload middleware started');


    upload1.fields([
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

var UploadRouter = express.Router();

// Multer setup: Save files in the "media/" folder
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
      cb(null, "media/"); // Save files in "media/"
    },
    filename: (req, file, cb) => {
      cb(null, Date.now() + path.extname(file.originalname)); // Unique filename
    },
});

const upload = multer({ storage });

// get and post calls for url /upload
UploadRouter.route('/')
    .post(upload.single("file"), (req, res) => {
        if (!req.file) {
            console.log("error in multer")
          return res.status(400).send("No file uploaded.");
        }
        console.log("succsess in multer")
        res.json({ filePath: req.file.filename }); // Send file path as response
      })

// Route to handle movie uploads
UploadRouter.post('/movies', movieUpload, (req, res) => {
  console.log("Uploaded files:", req.files)
  console.log("Request video path:", req.videoPath);
  console.log("Request image path:", req.imagePath);

  if (!req.videoPath || !req.imagePath) {
    return res.status(400).json({ error: "File upload failed" });
}

  // Respond with file paths (or save them to the database)
  res.status(200).json({
      status: 200,
      videoPath: req.videoPath,
      imagePath: req.imagePath
  });
});

export {UploadRouter};