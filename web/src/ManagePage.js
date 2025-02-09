import { useState, useRef, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { WarningAlert, SuccessAlert } from "./components/Alerts";
import { uploadFile, createCategory, deleteCategory, updateCategory, createMovie, getCategories, updateMovie, deleteMovie } from "./fetchRequests";

function CategoryCreate({mToken}) {
    const [nError, setNError] = useState("")
    const [gError, setGError] = useState("")
    const [success, setSuccess] = useState()

    const [promoted, setPromoted] = useState(true)
    const [name, setName] = useState("")

    const formRef = useRef(null)
    // func that detect clicks outside the form and reset alerts
    useEffect(() => {
      const handleClickOutside = (event) => {
        if (formRef.current && !formRef.current.contains(event.target)) {
          // clear all alerts
          setGError("")
          setNError("")
          setSuccess("")
        }
      };
  
      document.addEventListener("click", handleClickOutside);
      return () => document.removeEventListener("click", handleClickOutside);
    }, [])

    // func that's called by form in submit
    const handleSubmit = async (e) => {
        e.preventDefault()

        let error = false
        // if one of the field is empty, raise alert about it        
        if (name === "") {
            setNError("Name box is empty")
            error = true
        }
        else {setNError("")}

        if (error) {
            setSuccess("")
            setGError("")
            console.log("name is empty")
            return
        }

        // calling post api/categories
        console.log("all fields are fine: ", promoted)
        let res = await createCategory(mToken, name, promoted, setGError)
        if (res){
          setGError("")
          setSuccess("The category created")
        }
        else {setSuccess("")}
        setName("")
    }

    return (
        <div class="col-md-10 mx-auto col-lg-5">
        <h3>Create</h3>
        <form ref={formRef} class="p-4 p-md-5 border rounded-3 bg-body-tertiary" onSubmit={handleSubmit}>
        {gError && <WarningAlert message={gError} />}
        {success && <SuccessAlert message={success} />}
          <div class="form-floating mb-3">
            <input type="name" class="form-control" id="ccn" placeholder="name@example.com" fdprocessedid="t81uq" value={name} onChange={(e) => setName(e.target.value)} />
            <label for="ccn">Name</label>
            {nError && <WarningAlert message={nError} />}
          </div>
          <label htmlFor="dropdown">Promoted</label>
          <select
        id="dropdown"
        className="form-select"
        value={promoted}
        onChange={(e) => setPromoted(e.target.value)}
        style={{marginBottom: "5%"}}
      >
        <option value={true}>Yes</option>
        <option value={false}>No</option>
        Promoted
      </select>
          <button class="w-100 btn btn-lg btn-primary" type="submit" fdprocessedid="lg6y7">Create Category</button>
        </form>
      </div>
    )
}

function CategoryUpdate({mToken}) {
    const [gError, setGError] = useState("")
    const [iError, setIError] = useState("")
    const [success, setSuccess] = useState()

    const [promoted, setPromoted] = useState("")
    const [name, setName] = useState("")
    const [id, setId] = useState("")

    const formRef = useRef(null)
    // func that detect clicks outside the form and reset alerts
    useEffect(() => {
      const handleClickOutside = (event) => {
        if (formRef.current && !formRef.current.contains(event.target)) {
          // clear all alerts
          setGError("")
          setIError("")
          setSuccess("")
        }
      };
  
      document.addEventListener("click", handleClickOutside);
      return () => document.removeEventListener("click", handleClickOutside);
    }, [])

    // func that's called by form in submit
    const handleSubmit = async (e) => {
        e.preventDefault()

        let error = false
        // if name and promoted are empty, or id is empty raise alert about it  
        if (id === "") {
            setIError("Id box is empty")
            error = true
        }
        else {setIError("")}      
        if (name === "" && promoted === "") {
            setGError("must specified at least one of name/promoted")
            error = true
        }
        else {setGError("")}

        if (error) {
            setSuccess("")
            console.log("fields error")
            return
        }

        // calling patch api/categories/:id
        let res = await updateCategory(mToken, id, name, promoted, setGError)
        if (res) {
          console.log("success: ", promoted)
          setSuccess("The category updated")
          setGError("")
        } else {setSuccess("")}
        setId("")
        setName("")
        setPromoted("")
    }
    return (
        <div class="col-md-10 mx-auto col-lg-5">
        <h3>Update</h3>
        <form ref={formRef} class="p-4 p-md-5 border rounded-3 bg-body-tertiary" onSubmit={handleSubmit}>
        {gError && <WarningAlert message={gError} />}
        {success && <SuccessAlert message={success} />}
        <div class="form-floating mb-3">
            <input type="id" class="form-control" id="cui" placeholder="name@example.com" fdprocessedid="t81uq" value={id} onChange={(e) => setId(e.target.value)}/>
            <label for="cui">Id</label>
            {iError && <WarningAlert message={iError} />}
          </div>
          <div class="form-floating mb-3">
            <input type="name" class="form-control" id="ccn" placeholder="name@example.com" fdprocessedid="t81uq" value={name} onChange={(e) => setName(e.target.value)}/>
            <label for="ccn">Name</label>
          </div>
          <label htmlFor="dropdown">Promoted</label>
          <select
        id="dropdown"
        className="form-select"
        value={promoted}
        onChange={(e) => setPromoted(e.target.value)}
        style={{marginBottom: "5%"}}
      >
        <option value="">Select an option</option>
        <option value={true}>Yes</option>
        <option value={false}>No</option>
        Promoted
      </select>
          <button class="w-100 btn btn-lg btn-primary" type="submit" fdprocessedid="lg6y7">Update Category</button>
        </form>
      </div>
    )
}

function CateroryDelete({mToken}) {
    const [gError, setGError] = useState("")
    const [success, setSuccess] = useState()
    const [id, setId] = useState("")

    const formRef = useRef(null)
    // func that detect clicks outside the form and reset alerts
    useEffect(() => {
      const handleClickOutside = (event) => {
        if (formRef.current && !formRef.current.contains(event.target)) {
          // clear all alerts
          setGError("")
          setSuccess("")
        }
      };
  
      document.addEventListener("click", handleClickOutside);
      return () => document.removeEventListener("click", handleClickOutside);
    }, [])

    // func that's called by form in submit
    const handleSubmit = async (e) => {
        e.preventDefault()

        let error = false
        // if id is empty raise alert about it  
        if (id === "") {
            setGError("Id box is empty")
            error = true
        }
        else {setGError("")}

        if (error) {
            setSuccess("")
            console.log("fields error")
            return
        }

        // calling delete api/categories/:id

        let res = await deleteCategory(mToken, id, setGError)
        if (res) {
          console.log("success")
          setSuccess("The category was deleted")
          setGError("")
        }
        else {setSuccess("")}
        setId("")
    }
    return (
        <div class="col-md-10 mx-auto col-lg-5">
        <h3>Delete</h3>
        <form ref={formRef} class="p-4 p-md-5 border rounded-3 bg-body-tertiary" onSubmit={handleSubmit}>
        {gError && <WarningAlert message={gError} />}
        {success && <SuccessAlert message={success} />}
        <div class="form-floating mb-3">
            <input type="id" class="form-control" id="cdi" placeholder="name@example.com" fdprocessedid="t81uq" value={id} onChange={(e) => setId(e.target.value)} />
            <label for="cdi">Id</label>
          </div>
          <button class="w-100 btn btn-lg btn-primary" type="submit" fdprocessedid="lg6y7">Delete Category</button>
        </form>
      </div>
    )
}



function MovieCreate({mToken, allCategories}) {
  const [nError, setNError] = useState("")
  const [gError, setGError] = useState("")
  const [dError, setDError] = useState("")
  const [pError, setPError] = useState("")
  const [vError, setVError] = useState("")
  const [cError, setCError] = useState("")
  const [success, setSuccess] = useState("")

  const [name, setName] = useState("")
  const [description, setDescription] = useState("")
  const [categories, setCategories] = useState([]);

  const [selectedImg, setSelectedImg] = useState("")
  var imgName
  const [selectedVideo, setSelectedVideo] = useState("")
  var videoName
  
  const formRef = useRef(null)
    // func that detect clicks outside the form and reset alerts
    useEffect(() => {
      const handleClickOutside = (event) => {
        if (formRef.current && !formRef.current.contains(event.target)) {
          // clear all alerts
          setGError("")
          setNError("")
          setSuccess("")
          setDError("")
          setCError("")
          setPError("")
          setVError("")
        }
      };
  
      document.addEventListener("click", handleClickOutside);
      return () => document.removeEventListener("click", handleClickOutside);
    }, [])

  var error = false

  // updates the categories
  const handleCategoriesChange = (e) => {
    const value = e.target.value

    setCategories((prev) =>
      prev.includes(value) ? prev.filter((v) => v !== value) : [...prev, value]
    )
  }

  // funcs that the file input calls. 
  // changes the setSelected file according to the input
  const handleImgChange = (e) => {
    const file = e.target.files[0];

    if (file) {
      setSelectedImg(file);
    }
  }
  const handleVideoChange = (e) => {
    const file = e.target.files[0];

    if (file) {
      setSelectedVideo(file);
    }
  }

  // func that uploades the file specified in the input into the media directory in 
  // the server
  const handleUpload = async () => {
    // if no img specified, raising alert
    if (!selectedImg) {
      setPError("No image was chosen")
      error = true
    }
    else {setPError("")}
    // if no video was specified, raising alert
    if (!selectedVideo) {
      setVError("No video was chosen")
      error = true
    }
    else {setVError("")}
    if (error) {
      return
    }

    // uploading the img
    imgName = await uploadFile(selectedImg, setPError)
    if (!imgName) {
      error = true
      return
    }
    // uploading the video
    videoName = await uploadFile(selectedVideo, setVError)
    if (!videoName) {
      error = true
      return
    }
  }
  
  const handleSubmit = async (e) => {
    e.preventDefault()
    console.log("ctegories: ", categories)

    // if one of the fields is empty, raise alert about it
    if (name === "") {
      setNError("Name box is empty")
      error = true
    }
    else {setNError("")}
    
    if (description === "") {
      setDError("Description box is empty")
      error = true
    }
    else {setDError("")}

    if (categories.length === 0) {
      setCError("You must choose at least one category")
      error = true
    }
    else {setCError("")}
    // if no img specified, raising alert
    if (!selectedImg) {
      setPError("No image was chosen")
      error = true
    }
    else {setPError("")}
    // if no video was specified, raising alert
    if (!selectedVideo) {
      setVError("No video was chosen")
      error = true
    }
    else {setVError("")}

    // if one of the fields raised an alert, the func will stop at this point
    if (error) {
      console.log("error in one of the fileds")
      setSuccess("")
      return
    }
    // else, it will try to upload the file specified in the file input
    console.log("all fields are fine. try to upload the image")
    // await handleUpload()
    // if handleUpload raised an alert, the func will stop at this point
    // if (error) {
    //   console.log("error in handleUpload")
    //   setSuccess("")
    //   return
    // }
    // create a new movie by calling post req to api/movies

    const res = await createMovie(mToken, name, categories, description, selectedImg, selectedVideo, setGError)
    console.log("res: ", res)
    if (res == true) {
      setSuccess("The movie created")
      setGError("")
    }
    console.log("img: ", imgName)
    console.log("video: ", videoName)
    
    setName("")
    setDescription("")
    setCategories([])
  }

    return (
        <div class="col-md-10 mx-auto col-lg-5">
        <h3>Create</h3>
        <form ref={formRef} class="p-4 p-md-5 border rounded-3 bg-body-tertiary" onSubmit={handleSubmit}>
        {gError && <WarningAlert message={gError} />}
        {success && <SuccessAlert message={success} />}
        <div class="form-floating mb-3">
            <input type="name" class="form-control" id="mcn" placeholder="name@example.com" fdprocessedid="t81uq" value={name} onChange={(e) => setName(e.target.value)}/>
            <label for="mcn">Name</label>
            {nError && <WarningAlert message={nError} />}
        </div>

        <div class="mb-3">
        {cError && <WarningAlert message={cError} />}
        <div class="dropdown">
  <button class="btn btn-outline-primary dropdown-toggle" type="button" data-bs-toggle="dropdown">
    Select categories
  </button>
  <ul class="dropdown-menu">
  {allCategories.map((category) => (
          <li key={category}>
            <label className="dropdown-item">
              <input
                type="checkbox"
                value={category}
                checked={categories.includes(category)}
                onChange={handleCategoriesChange}
                className="form-check-input"
                style={{marginRight: "5%"}}
              />
              {category}
            </label>
          </li>
        ))}
  </ul>
</div>

        </div>

        <div class="mb-3">
            <label for="exampleFormControlTextarea1" class="form-label">Description</label>
            <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" value={description} onChange={(e) => setDescription(e.target.value)}></textarea>
            {dError && <WarningAlert message={dError} />}
        </div>

        <div class="mb-3">
          <label for="formFile" class="form-label">Choose movie's picture</label>
          <input class="form-control" type="file" id="formFile" accept="image/*" onChange={handleImgChange} />
          {pError && <WarningAlert message={pError} />}
        </div>

        <div class="mb-3">
          <label for="formFile" class="form-label">Choose movie's video</label>
          <input class="form-control" type="file" id="formFile" accept="video/*" onChange={handleVideoChange} />
          {vError && <WarningAlert message={vError} />}
        </div>

          <button class="w-100 btn btn-lg btn-primary" type="submit" fdprocessedid="lg6y7">Create movie</button>
        </form>
      </div>
    )
}

function MovieUpdate({mToken, allCategories}) {
  const [nError, setNError] = useState("")
  const [gError, setGError] = useState("")
  const [iError, setIError] = useState("")
  const [dError, setDError] = useState("")
  const [pError, setPError] = useState("")
  const [vError, setVError] = useState("")
  const [cError, setCError] = useState("")
  const [success, setSuccess] = useState("")

  const [id, setId] = useState("")
  const [name, setName] = useState("")
  const [description, setDescription] = useState("")
  const [categories, setCategories] = useState([]);

  const [selectedImg, setSelectedImg] = useState(null)
  var imgName
  const [selectedVideo, setSelectedVideo] = useState(null)
  var videoName
  
  const formRef = useRef(null)
    // func that detect clicks outside the form and reset alerts
    useEffect(() => {
      const handleClickOutside = (event) => {
        if (formRef.current && !formRef.current.contains(event.target)) {
          // clear all alerts
          setGError("")
          setNError("")
          setSuccess("")
          setIError("")
          setDError("")
          setCError("")
          setPError("")
          setVError("")
        }
      };
  
      document.addEventListener("click", handleClickOutside);
      return () => document.removeEventListener("click", handleClickOutside);
    }, [])

  var error = false

  // updates the categories
  const handleCategoriesChange = (e) => {
    const value = e.target.value

    setCategories((prev) =>
      prev.includes(value) ? prev.filter((v) => v !== value) : [...prev, value]
    )
  }

  // funcs that the file input calls. 
  // changes the setSelected file according to the input
  const handleImgChange = (e) => {
    const file = e.target.files[0];

    if (file) {
      setSelectedImg(file);
    }
  }
  const handleVideoChange = (e) => {
    const file = e.target.files[0];

    if (file) {
      setSelectedVideo(file);
    }
  }

  // func that uploades the file specified in the input into the media directory in 
  // the server
  const handleUpload = async () => {
    // if no img specified, raising alert
    if (!selectedImg) {
      setPError("No image was chosen")
      error = true
    }
    else {setPError("")}
    // if no video was specified, raising alert
    if (!selectedVideo) {
      setVError("No video was chosen")
      error = true
    }
    else {setVError("")}
    if (error) {
      return
    }

    // uploading the img
    imgName = await uploadFile(selectedImg, setPError)
    if (!imgName) {
      error = true
      return
    }
    // uploading the video
    videoName = await uploadFile(selectedVideo, setVError)
    if (!videoName) {
      error = true
      return
    }
  }
  
  const handleSubmit = async (e) => {
    e.preventDefault()
    console.log("ctegories: ", categories)

    // if one of the fields is empty, raise alert about it
    if (id === "") {
      setIError("Id box is empty")
      error = true
    }
    else {setIError("")}
    if (name === "") {
      setNError("Name box is empty")
      error = true
    }
    else {setNError("")}
    
    if (description === "") {
      setDError("Description box is empty")
      error = true
    }
    else {setDError("")}

    if (categories.length === 0) {
      setCError("You must choose at least one category")
      error = true
    }
    else {setCError("")}
    // if no img specified, raising alert
    if (!selectedImg) {
      setPError("No image was chosen")
      error = true
    }
    else {setPError("")}
    // if no video was specified, raising alert
    if (!selectedVideo) {
      setVError("No video was chosen")
      error = true
    }
    else {setVError("")}

    // if one of the fields raised an alert, the func will stop at this point
    if (error) {
      console.log("error in one of the fileds")
      setSuccess("")
      return
    }
    // else, it will try to upload the file specified in the file input
    console.log("all fields are fine. try to upload the image")
    // await handleUpload()
    // if handleUpload raised an alert, the func will stop at this point
    // if (error) {
    //   console.log("error in handleUpload")
    //   setSuccess("")
    //   return
    // }

    // update he movie by calling in put req to api/movies/:id

    const res = await updateMovie(mToken, id, name, categories, description, selectedImg, selectedVideo, setGError)
    if (res == true) {
      setSuccess("The movie updated")
      setGError("")
    }
    else {setSuccess("")}

    console.log("img: ", imgName)
    console.log("video: ", videoName)
    
    setName("")
    setId("")
    setDescription("")
    setCategories([])
  }

    return (
        <div class="col-md-10 mx-auto col-lg-5">
        <h3>Update</h3>
        <form ref={formRef} class="p-4 p-md-5 border rounded-3 bg-body-tertiary" onSubmit={handleSubmit}>
        {gError && <WarningAlert message={gError} />}
        {success && <SuccessAlert message={success} />}
        <div class="form-floating mb-3">
            <input type="id" class="form-control" id="mui" placeholder="name@example.com" fdprocessedid="t81uq" value={id} onChange={(e) => setId(e.target.value)}/>
            <label for="mui">Id</label>
            {iError && <WarningAlert message={iError} />}
        </div>
        <div class="form-floating mb-3">
            <input type="name" class="form-control" id="mun" placeholder="name@example.com" fdprocessedid="t81uq" value={name} onChange={(e) => setName(e.target.value)}/>
            <label for="mun">Name</label>
            {nError && <WarningAlert message={nError} />}
        </div>

        <div class="mb-3">
        {cError && <WarningAlert message={cError} />}
        <div class="dropdown">
  <button class="btn btn-outline-primary dropdown-toggle" type="button" data-bs-toggle="dropdown">
    Select categories
  </button>
  <ul class="dropdown-menu">
  {allCategories.map((category) => (
          <li key={category}>
            <label className="dropdown-item">
              <input
                type="checkbox"
                value={category}
                checked={categories.includes(category)}
                onChange={handleCategoriesChange}
                className="form-check-input"
                style={{marginRight: "5%"}}
              />
              {category}
            </label>
          </li>
        ))}
  </ul>
</div>

        </div>

        <div class="mb-3">
            <label for="exampleFormControlTextarea1" class="form-label">Description</label>
            <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" value={description} onChange={(e) => setDescription(e.target.value)}></textarea>
            {dError && <WarningAlert message={dError} />}
        </div>

        <div class="mb-3">
          <label for="formFile" class="form-label">Choose movie's picture</label>
          <input class="form-control" type="file" id="formFile" accept="image/*" onChange={handleImgChange} />
          {pError && <WarningAlert message={pError} />}
        </div>

        <div class="mb-3">
          <label for="formFile" class="form-label">Choose movie's video</label>
          <input class="form-control" type="file" id="formFile" accept="video/*" onChange={handleVideoChange} />
          {vError && <WarningAlert message={vError} />}
        </div>

          <button class="w-100 btn btn-lg btn-primary" type="submit" fdprocessedid="lg6y7">Create movie</button>
        </form>
      </div>
    )
}

function MovieDelete({mToken}) {
  const [gError, setGError] = useState("")
  const [success, setSuccess] = useState()
  const [id, setId] = useState("")

  const formRef = useRef(null)
    // func that detect clicks outside the form and reset alerts
    useEffect(() => {
      const handleClickOutside = (event) => {
        if (formRef.current && !formRef.current.contains(event.target)) {
          // clear all alerts
          setGError("")
          setSuccess("")
        }
      };
  
      document.addEventListener("click", handleClickOutside);
      return () => document.removeEventListener("click", handleClickOutside);
    }, [])

  // func that's called by form in submit
  const handleSubmit = async (e) => {
      e.preventDefault()

      let error = false
      // if id is empty raise alert about it  
      if (id === "") {
          setGError("Id box is empty")
          error = true
      }
      else {setGError("")}

      if (error) {
          setSuccess("")
          console.log("fields error")
          return
      }

      // calling delete api/movies/:id
      const res = await deleteMovie(mToken, id)
      if (res == true) {
        setSuccess("The movie was deleted")
        setGError("")
        console.log("success")
      }
      else {
        setSuccess("")
      }
      setId("")
  }
  return (
      <div class="col-md-10 mx-auto col-lg-5">
      <h3>Delete</h3>
      <form ref={formRef} class="p-4 p-md-5 border rounded-3 bg-body-tertiary" onSubmit={handleSubmit}>
      {gError && <WarningAlert message={gError} />}
      {success && <SuccessAlert message={success} />}
      <div class="form-floating mb-3">
          <input type="id" class="form-control" id="mdi" placeholder="name@example.com" fdprocessedid="t81uq" value={id} onChange={(e) => setId(e.target.value)} />
          <label for="mdi">Id</label>
        </div>
        <button class="w-100 btn btn-lg btn-primary" type="submit" fdprocessedid="lg6y7">Delete Movie</button>
      </form>
    </div>
  )
}
function ManagePage({mToken}) {
    // get all the categories
    // let allCategories = ["Action", "Comedy", "Drama", "Horror", "Advantures", "Kids"]
    const [allCategories, setCategories] = useState([])
  useEffect(() => {
    const fetchCategories = async () => {
        const data = await getCategories(mToken);
        setCategories(data.map(cat => cat.name))
    };

    fetchCategories();
}, [mToken]);



    return (<div>
<div class="container col-xl-10 col-xxl-8 px-4 py-5">
    <h1>Categories</h1>
    <div class="row align-items-center g-lg-5 py-5">

    <CategoryCreate mToken={mToken} />
    <CategoryUpdate mToken={mToken} />
    <CateroryDelete mToken={mToken} />
    </div>
  </div>
  <div class="container col-xl-10 col-xxl-8 px-4 py-5">
  <h1>Movies</h1>
  <div class="row align-items-center g-lg-5 py-5">

  <MovieCreate mToken={mToken} allCategories={allCategories}/>
  <MovieUpdate mToken={mToken} allCategories={allCategories}/>
  <MovieDelete mToken={mToken} />
  </div>
</div>
</div>
    )
}

export default ManagePage