import { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { WarningAlert } from "./components/Alerts";



// rendering the login form
function Form({handleLogin}) {
  const userR = useRef()
  const passwordR = useRef()
  const [uError, setUError] = useState("")
  const [pError, setPError] = useState("")
  const [gError, setGError] = useState("")
  const navigate = useNavigate()

  // the func that being activated in login attempt
  const handleSubmit = (e) => {
    e.preventDefault()
    // gets the inputed username and password
    let userName = userR.current.value.trim()
    let password = passwordR.current.value.trim()

    let error = false
    // if one of the field is empty, raise alert about it
    if (userName === "") {
      setUError("username box is empty")
      error = true
    }
    else {setUError("")}
    
    if (password === "") {
      setPError("password box is empty")
      error = true
    }
    else {setPError("")}

    // send post request to api/users/token. in case it worked, calling to handleLogin.



    if (error) {return}

    handleLogin("llkkj")
    navigate('/')
  }

  return (
    <form onSubmit={handleSubmit}>
  <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

  {gError && <WarningAlert message={gError} />}

  <div class="form-floating">
    <input type="username" class="form-control" id="floatingInput" placeholder="name@example.com" fdprocessedid="k2b4s" ref={userR}/>
    <label for="floatingInput">Username</label>

    {uError && <WarningAlert message={uError} />}

  </div>
  <div class="form-floating">
    <input type="password" class="form-control" id="floatingPassword" placeholder="Password" fdprocessedid="6hoglo" ref={passwordR} />
    <label for="floatingPassword">Password</label>

    {pError && <WarningAlert message={pError} />}

  </div>

  <button class="btn btn-outline-primary w-100 py-2" type="submit" fdprocessedid="tv6em" style={{"margin-top": "8%"}}>Sign in</button>
</form>
  )
}
// render the login page
function LoginPage({handleLogin, handleMlogin}) {
  // handleLogin("jjjj")
  // handleMlogin("nfn")
  return (
      <div class="d-flex align-items-center py-4 bg-body-tertiary" id="login-cart">
  
<main class="form-signin w-100 m-auto">
<Form handleLogin={handleLogin}/>
</main>
  

<span id="PING_IFRAME_FORM_DETECTION" style={{display: null}}></span></div>
  ) 
}

export default LoginPage