import express from 'express';
import multer from 'multer';
import path from 'path';

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

export {UploadRouter};