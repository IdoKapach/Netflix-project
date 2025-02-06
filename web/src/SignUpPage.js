import { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { WarningAlert } from "./components/Alerts";

// render the sign-up page
function SignUpPage({handleLogin}) {
  const userR = useRef()
  const passwordR = useRef()
  const confirmR = useRef()
  const nicknameR = useRef()
  const [uError, setUError] = useState("")
  const [pError, setPError] = useState("")
  const [cError, setCError] = useState("")
  const [nError, setNError] = useState("")
  const [gError, setGError] = useState("")
  const [fError, setFError] = useState("")

  const [selectedFile, setSelectedFile] = useState(null)
  var fileName
  
  const navigate = useNavigate()

  var error = false
  // the func that the file input calls. 
  // changes the setSelected file according to the input
  const handleFileChange = (e) => {
    const file = e.target.files[0];

    if (file) {
      setSelectedFile(file);
    }
  }
  // func that uploades the file specified in the input into the media directory in 
  // the server
  const handleUpload = async () => {
    // if no file specified, raising alert
    if (!selectedFile) {
      setFError("No image was chosen")
      error = true
      return;
    }

    const formData = new FormData();
    formData.append("file", selectedFile);
    // trying upload the file
    try {
      const res = await fetch("http://localhost:8777/upload", {
        method: "POST",
        body: formData,
      });
      // if the upload succeeded
      if (res.ok) {
        const data = await res.json()
        // save uploaded file path into fileName
        fileName = data.filePath 
      }
      // if it wasn't upload the file, raising alert
      else {
        setFError("Error occured while trying uploading the image")
        error = true
      }
    } catch (e) {
      console.error("Upload error:", e)
      setFError("Error occured while trying uploading the image")
      error = true
    }
  }

  // the func that being activated in login attempt
  const handleSubmit = async (e) => {
    e.preventDefault()
    // gets the inputed fields
    let userName = userR.current.value.trim()
    let password = passwordR.current.value.trim()
    let confirmP = confirmR.current.value.trim()
    let nickname = nicknameR.current.value.trim()

    // if one of the fields is empty, raise alert about it
    if (userName === "") {
      setUError("Username box is empty")
      error = true
    }
    else {setUError("")}
    
    if (password === "") {
      setPError("Password box is empty")
      error = true
    }
    else {setPError("")}

    if (confirmP === "") {
      setCError("Confirm box is empty")
      error = true
    }
    // raising alert in case the confirm isn't similar to the password
    else if (confirmP !== password) {
      setCError("Confirm isn't similar to the password")
      error = true
    }
    else {setCError("")}

    if (nickname === "") {
      setNError("Nickname box is empty")
      error = true
    }
    else {setNError("")}

    // if one of the fields raised an alert, the func will stop at this point
    if (error) {
      console.log("error in one of the fileds")
      return
    }
    // else, it will try to upload the file specified in the file input
    console.log("all fields are fine. try to upload the image")
    await handleUpload()
    // if handleUpload raised an alert, the func will stop at this point
    if (error) {
      console.log("error in handleUpload")
      return
    }
    // send post request to api/users. in case it worked, calling to handleLogin.
    console.log("succsess in handleUpload: ", fileName)
    




    handleLogin("llkkj")
    navigate('/')
  }

  return (
      <div class="d-flex align-items-center py-4 bg-body-tertiary" id="login-cart">
  
<main class="form-signin w-100 m-auto">
<form onSubmit={handleSubmit}>
  <h1 class="h3 mb-3 fw-normal">Sign up</h1>

  {gError && <WarningAlert message={gError} />}

  <div class="form-floating">
    <input type="username" ref={userR} class="form-control" id="floatingInput" placeholder="name@example.com" fdprocessedid="k2b4s"/>
    <label for="floatingInput">Username</label>
    {uError && <WarningAlert message={uError} />}
  </div>
  <div class="form-floating">
    <input type="password" ref={passwordR} class="form-control" id="floatingPassword" placeholder="Password" fdprocessedid="6hoglo"/>
    <label for="floatingPassword">Password</label>
    {pError && <WarningAlert message={pError} />}
  </div>
  <div class="form-floating">
    <input type="password" ref={confirmR} class="form-control" id="floatingPasswordConfirm" placeholder="Password" fdprocessedid="6hoglo"/>
    <label for="floatingPasswordConfirm">Confirm password</label>
    {cError && <WarningAlert message={cError} />}
  </div>
  <div class="form-floating">
    <input type="username" ref={nicknameR} class="form-control" id="floatingInputNickname" placeholder="name@example.com" fdprocessedid="k2b4s"/>
    <label for="floatingInputNickname">Nickname</label>
    {nError && <WarningAlert message={nError} />}
  </div>

  <div class="mb-3">
    <label for="formFile" class="form-label">Choose profile picture</label>
    <input class="form-control" type="file" id="formFile" accept="image/*" onChange={handleFileChange} />
    {fError && <WarningAlert message={fError} />}
  </div>

  <button class="btn btn-outline-primary w-100 py-2" type="submit" fdprocessedid="tv6em" style={{"margin-top": "8%"}}>Sign in</button>
</form>
</main>
  

<span id="PING_IFRAME_FORM_DETECTION" style={{display: null}}></span></div>
  ) 
}

export default SignUpPage