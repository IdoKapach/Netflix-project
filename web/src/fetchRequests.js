
// sending post request to localhost:3000/upload in order to upload a file
async function uploadFile(selectedFile, setFError) {
    const formData = new FormData();
    formData.append("file", selectedFile);
    // trying to upload the file
    try {
      const res = await fetch("http://localhost:3000/upload", {
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

// getting token attempt by sending post request to localhost:3000/tokens
async function getToken(username, password, setFError) {
  console.log("recived usename and password: ", username, password)
  const formData = new FormData();
    formData.append("username", username);
    formData.append("password", password);
    // trying to get token
    try {
      const res = await fetch("http://localhost:3000/api/tokens", {
        method: "POST",
        headers: { 
          "Content-Type": "application/json"  // 👈 Ensure the backend recognizes the request as JSON
        },
        body: JSON.stringify({ username, password })
      });
      // if the request succeeded
      if (res.ok) {
        const data = await res.json()
        // return the token
        return await data.token 
      }
      // if the request failed, raising alert
      else {
        const data = await res.json()
        const error = await data.error
        setFError(error)
        return null
      }
    } catch (e) {
      console.error("Upload error:", e)
      setFError("Error occured while trying to login")
      return null
    }
}

// sending post req to api/users in order to create new child
async function signUp(username, password, picture, setFError) {
  const formData = new FormData();
    formData.append("username", username);
    formData.append("password", password);
    formData.append("picture", picture)
    // trying to create user
    try {
      const res = await fetch("http://localhost:3000/api/users", {
        method: "POST",
        body: formData,
      });
      // if the request succeeded
      if (res.ok) {
        const data = await res.json()
        // return user's details
        return data
      }
      // if the request failed, raising alert
      else {
        const data = await res.json()
        const error = await data.error
        setFError(error)
        return null
      }
    } catch (e) {
      console.error("Upload error:", e)
      setFError("Error occured while trying to sign-up")
      return null
    }
}

// sending get req to api/users/:userId in order to get user's picture and set ProfileImg
// accordingly
async function getUserImg(userId, token, setProfileImg) {
    // trying to create user
    try {
      const res = await fetch(`http://localhost:3000/api/users/${userId}`, {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token}`,  // Include the Bearer token
          "Content-Type": "application/json"
      }
      });
      // if the request succeeded
      if (res.ok) {
        const data = await res.json()
        // return user's details
        const userImg = await data.picture
        console.log("profile: ", userImg)
        setProfileImg(userImg)
        localStorage.setItem("profileImg", userImg)
        return true
      }
      // if the request failed, raising alert
      else {
        const data = await res.json()
        const error = await data.error
        console.error("fail cause: ", error)
        return false
      }
    } catch (e) {
      console.error("get profile error:", e)
      return false
    }
}


export {uploadFile, getToken, signUp, getUserImg}