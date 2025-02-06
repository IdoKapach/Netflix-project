import multer from 'multer';
import fs from 'fs';
import path from 'path';

const rootFolder = path.join(import.meta.dirname, '../media');

const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    let destFolder;
    if (file.fieldname === 'avatar') {
      // Define relative path for later use (e.g. storing in the database)
      req.body.picture = path.join(rootFolder, Date.now() + path.extname(file.originalname));
      destFolder = rootFolder;
    } else {
      // Reject other file fields
      return cb(new Error('invalid file field'), null);
    }
    // Create destination folder if it doesn't exist
    if (!fs.existsSync(rootFolder)) {
      fs.mkdirSync(rootFolder, { recursive: true });
    }
    cb(null, destFolder);
  },
  filename: (req, file, cb) => {
    // Use original filename for storage; adjust if needed
    cb(null, file.originalname);
  }
});

const upload = multer({ storage });

const userUpload = (req, res, next) => {
  console.log('userUpload middleware started');

  upload.fields([
    { name: 'avatar', maxCount: 1 }
  ])(req, res, (err) => {
    if (err) {
      console.error('Multer encountered an error:', err);
      return res.status(400).json({ error: 'Multer error', details: err.message });
    }
    console.log('userUpload middleware finished');
    next();
  });
};

export default userUpload;