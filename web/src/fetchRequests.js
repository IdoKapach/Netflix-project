
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
    // trying to get user
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


async function createCategory(token, name, promoted, setFerror) {
  // trying to create category
  try {
    const res = await fetch(`http://localhost:3000/api/categories`, {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${token}`,  // Include the Bearer token
        "Content-Type": "application/json"
    },
      body: JSON.stringify({
        name: name,
        promoted: promoted === true ? true : false
    })
    });
    // if the request succeeded
    if (res.ok) {
      return true
    }
    // if the request failed, raising alert
    else {
      const data = await res.json()
      const error = await data.error
      const errors = await data.errors
      if (typeof errors === "undefined"){
        console.error("fail cause: ", error)
        setFerror(error)
      }
      else {
        console.error("fail cause: ", errors[0])
        setFerror(errors[0])
      }
      return false
    }
  } catch (e) {
    console.error("create category error:", e)
    setFerror(e)
    return false
  }
}

async function getCategories(token) {
  // trying to get all the categories
  try {
    const res = await fetch(`http://localhost:3000/api/categories`, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`,  // Include the Bearer token
        "Content-Type": "application/json"
    }
    });
    // if the request succeeded
    if (res.ok) {
      const data = await res.json()
      // return all the ctegories
      console.log("categories: ", data)
      return data
    }
    // if the request failed, raising alert
    else {
      const error = await res.text()
      console.error("fail cause: ", error)
      return null
    }
  } catch (e) {
    console.error("get categories:", e)
    return null
  }
}

async function deleteCategory(token, id, setFerror) {
  // trying to delete category
  try {
    const res = await fetch(`http://localhost:3000/api/categories/${id}`, {
      method: "DELETE",
      headers: {
        "Authorization": `Bearer ${token}`,  // Include the Bearer token
        "Content-Type": "application/json"
    }
    });
    // if the request succeeded
    if (res.ok) {
      return true
    }
    // if the request failed, raising alert
    else {
      const data = await res.json()
      const error = await data.error
      console.error("fail cause: ", error)
      setFerror(error)
      return false
    }
  } catch (e) {
    console.error("delete category error:", e)
    setFerror(e)
    return false
  }
}

async function updateCategory(token, id, name, promoted, setFerror) {
  // trying to update category
  console.log("name: ", name, "promoted: ",typeof promoted)
  let res
  try {
    if (promoted === ""){
      res = await fetch(`http://localhost:3000/api/categories/${id}`, {
        method: "PATCH",
        headers: {
          "Authorization": `Bearer ${token}`,  // Include the Bearer token
          "Content-Type": "application/json"
      },
        body: JSON.stringify({
          name: name
      })
      });
    }
    else if (name === "") {
      res = await fetch(`http://localhost:3000/api/categories/${id}`, {
        method: "PATCH",
        headers: {
          "Authorization": `Bearer ${token}`,  // Include the Bearer token
          "Content-Type": "application/json"
      },
        body: JSON.stringify({
          promoted: promoted === "true" ? true : false
      })
      });
    }
    else {
    console.log("both fields filled")
    res = await fetch(`http://localhost:3000/api/categories/${id}`, {
      method: "PATCH",
      headers: {
        "Authorization": `Bearer ${token}`,  // Include the Bearer token
        "Content-Type": "application/json"
    },
      body: JSON.stringify({
        name: name,
        promoted: promoted === "true" ? true : false
    })
    });
  }
    // if the request succeeded
    if (res.ok) {
      return true
    }
    // if the request failed, raising alert
    else {
      const data = await res.json()
      const error = await data.error
      console.error("fail cause: ", error)
      setFerror(error)
      return false
    }
  } catch (e) {
    console.error("update category error:", e)
    setFerror(e)
    return false
  }
}


async function createMovie(token, name, categories, description, imgPath, videoPath, setFError) {
  const uploadMovie = async (videoFile, imageFile) => {
    const formData = new FormData();
    formData.append('videoFile', videoFile);
    formData.append('imageFile', imageFile);

    try {
        const res = await fetch('http://localhost:3000/upload/movies', {
            method: 'POST',
            headers: {
                "Authorization": `Bearer ${token}` // Add token for authentication
            },
            body: formData
        });

        if (!res.ok) {
            throw new Error(`Upload failed: ${res.statusText}`);
        }

        const data = await res.json();
        console.log('Upload response:', data);
        return data;
    } catch (error) {
        console.error('Upload failed:', error);
        return { error: error.message };
    }
  };

  let res1 = await uploadMovie(videoPath, imgPath);
  if (res1.error) {
    setFError(res1.error);
    return false;
  }

  videoPath = res1.videoPath
  imgPath = res1.imagePath
  console.log("AFTER: video:", videoPath, "img: ", imgPath)

  // trying to create movie
  try {
    console.log("categories: ", JSON.stringify(categories))
    const formData = new FormData();
    formData.append('videoPath', videoPath);
    formData.append('imagePath', imgPath);
    formData.append('title', name);
    formData.append('categories', categories);
    formData.append('description', description);
    const res = await fetch(`http://localhost:3000/api/movies`, {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${token}`,  // Include the Bearer token
        "Content-Type": "application/json"
    },
      body: JSON.stringify({
        title: name,
        categories: JSON.stringify(categories),
        description: description,
        videoPath: videoPath,
        imagePath: imgPath
    })
    });
    // if the request succeeded
    if (res.ok) {
      return true
    }
    // if the request failed, raising alert
    else {
      const data = await res.json()
      const errors = await data.errors
      console.error("fail cause: ", errors)
      setFError(errors[0])
      return false
    }
  } catch (e) {
    console.error("create movie error:", e)
    setFError(e)
    return false
  }
}

async function updateMovie(token,id, name, categories, description, imgPath, videoPath, setFError) {
  const uploadMovie = async (videoFile, imageFile) => {
    const formData = new FormData();
    formData.append('videoFile', videoFile);
    formData.append('imageFile', imageFile);

    try {
        const res = await fetch('http://localhost:3000/upload/movies', {
            method: 'POST',
            headers: {
                "Authorization": `Bearer ${token}` // Add token for authentication
            },
            body: formData
        });

        if (!res.ok) {
            throw new Error(`Upload failed: ${res.statusText}`);
        }

        const data = await res.json();
        console.log('Upload response:', data);
        return data;
    } catch (error) {
        console.error('Upload failed:', error);
        return { error: error.message };
    }
  };

  let res1 = await uploadMovie(videoPath, imgPath);
  if (res1.error) {
    setFError(res1.error);
    return false;
  }

  videoPath = res1.videoPath
  imgPath = res1.imagePath
  console.log("AFTER: video:", videoPath, "img: ", imgPath)

  // trying to create movie
  try {
    console.log("categories: ", JSON.stringify(categories))
    const formData = new FormData();
    formData.append('video', videoPath);
    formData.append('image', imgPath);
    formData.append('name', name);
    formData.append('categories', categories);
    formData.append('description', description);
    const res = await fetch(`http://localhost:3000/api/movies/${id}`, {
      method: "PUT",
      headers: {
        "Authorization": `Bearer ${token}`,  // Include the Bearer token
        "Content-Type": "application/json"
    },
      body: JSON.stringify({
        name: name,
        categories: categories,
        description: description,
        video: videoPath,
        image: imgPath
    })
    });
    // if the request succeeded
    if (res.ok) {
      return true
    }
    // if the request failed, raising alert
    else {
      const data = await res.json()
      const errors = await data.errors
      console.error("fail cause: ", errors)
      setFError(errors[0])
      return false
    }
  } catch (e) {
    console.error("create movie error:", e)
    setFError(e)
    return false
  }
}

async function getCategory(token, id) {
  // trying to get all the categories
  try {
    const res = await fetch(`http://localhost:3000/api/categories/${id}`, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`,  // Include the Bearer token
        "Content-Type": "application/json"
    }
    });
    // if the request succeeded
    if (res.ok) {
      const data = await res.json()
      // return all the ctegories
      console.log("category: ", data)
      return data
    }
    // if the request failed, raising alert
    else {
      const error = await res.text()
      console.error("fail cause: ", error)
      return null
    }
  } catch (e) {
    console.error("get category:", e)
    return null
  }
}

async function getMovie(token, id) {
  // trying to get all the categories
  try {
    const res = await fetch(`http://localhost:3000/api/movies/${id}`, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`,  // Include the Bearer token
        "Content-Type": "application/json"
    }
    });
    // if the request succeeded
    if (res.ok) {
      const data = await res.json()
      // return all the ctegories
      console.log("category: ", data)
      return data
    }
    // if the request failed, raising alert
    else {
      const error = await res.text()
      console.error("fail cause: ", error)
      return null
    }
  } catch (e) {
    console.error("get movie:", e)
    return null
  }
}

async function getMovies(token) {
  // trying to get all the movies
  try {
    const res = await fetch(`http://localhost:3000/api/movies`, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`,  // Include the Bearer token
        "Content-Type": "application/json"
    }
    });
    // if the request succeeded
    if (res.ok) {
      const data = await res.json()
      // return all the ctegories
      console.log("category: ", data)
      return data
    }
    // if the request failed, raising alert
    else {
      const error = await res.text()
      console.error("fail cause: ", error)
      return null
    }
  } catch (e) {
    console.error("get movies:", e)
    return null
  }
}

async function getQuery(token, query) {
  // trying to get query results
  try {
    const res = await fetch(`http://localhost:3000/api/movies/search/${query}`, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`,  // Include the Bearer token
        "Content-Type": "application/json"
      }
    });
    // if the request succeeded
    if (res.ok) {
      const data = await res.json()
      // return all the results
      console.log("query results: ", data)
      return data
    }
    // if the request failed, raising alert
    else {
      const error = await res.text()
      console.error("fail cause: ", error)
      return null
    }
  } catch (e) {
    console.error("get query:", e)
    return null
  }
}

async function getRecommends(token, movieId) {
  // trying to get all the movies
  try {
    const res = await fetch(`http://localhost:3000/api/movies/${movieId}/recommend`, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`,  // Include the Bearer token
        "Content-Type": "application/json"
      }
    });
    // if the request succeeded
    if (res.ok) {
      const data = await res.json()
      // return all recommends
      console.log("recommends: ", data)
      return data
    }
    // if the request failed, raising alert
    else {
      const error = await res.text()
      console.error("fail cause: ", error)
      return null
    }
  } catch (e) {
    console.error("recommends:", e)
    return null
  }
}

async function watchMovie(token, movieId) {
  // trying to add the movie
  try {
    const res = await fetch(`http://localhost:3000/api/movies/${movieId}/recommend`, {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${token}`,  // Include the Bearer token
        "Content-Type": "application/json"
      }
    });
    // if the request succeeded
    if (res.ok) {
      const data = await res.json()
      // return if the req succeeded
      console.log("recommends: ", data)
      return data
    }
    // if the request failed, raising alert
    else {
      const error = await res.text()
      console.error("fail cause: ", error)
      return null
    }
  } catch (e) {
    console.error("recommends:", e)
    return null
  }
}

async function deleteMovie(token, movieId, setFError) {
  // trying to delete the movie
  try {
    const res = await fetch(`http://localhost:3000/api/movies/${movieId}`, {
      method: "DELETE",
      headers: {
        "Authorization": `Bearer ${token}`,  // Include the Bearer token
        "Content-Type": "application/json"
      }
    });
    // if the request succeeded
    if (res.ok) {
      return true
    }
    // if the request failed, raising alert
    else {
      const error = await res.text()
      console.error("fail cause: ", error)
      setFError(error)
      return false
    }
  } catch (e) {
    console.error("delete:", e)
    setFError(e)
    return false
  }
}
export {uploadFile, getToken, signUp, getUserImg, createCategory, getCategories, deleteCategory, updateCategory
  , createMovie, updateMovie, deleteMovie, getCategory, getMovie, getMovies, getQuery, getRecommends, watchMovie
}