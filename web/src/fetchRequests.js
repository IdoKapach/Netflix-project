
// sending post request to localhost:8777/upload in order to upload a file
async function uploadFile(selectedFile, setFError) {
    const formData = new FormData();
    formData.append("file", selectedFile);
    // trying to upload the file
    try {
      const res = await fetch("http://localhost:8777/upload", {
        method: "POST",
        body: formData,
      });
      // if the upload succeeded
      if (res.ok) {
        const data = await res.json()
        // save uploaded file path into fileName
        return await data.filePath 
      }
      // if it wasn't upload the file, raising alert
      else {
        setFError("Error occured while trying uploading the file")
        return null
      }
    } catch (e) {
      console.error("Upload error:", e)
      setFError("Error occured while trying uploading the file")
      return null
    }
}



export {uploadFile}